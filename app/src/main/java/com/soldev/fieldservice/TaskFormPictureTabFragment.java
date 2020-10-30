package com.soldev.fieldservice;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.fieldservice.dataentity.ImageInfo;
import com.soldev.fieldservice.uiadapter.TaskImageHolderAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.ImageSaver;
import com.soldev.prod.mobileservice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormPictureTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormPictureTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String taskID;
    private TaskFormActivity taskFormActivity;
    private Button btnSelectImage,btnAddImage;
    private String mFilenname;
    private View fragmentView;
    private RecyclerView rvImage;
    private TaskImageHolderAdapter taskImageHolderAdapter;
    public TaskFormPictureTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TaskFormPictureTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormPictureTabFragment newInstance(String param1) {
        TaskFormPictureTabFragment fragment = new TaskFormPictureTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_task_form_picture_tab, container, false);
        setBinding();
        setEvent();
        return fragmentView;
    }

    private void setBinding(){
        taskFormActivity = (TaskFormActivity) getActivity();
        btnAddImage = fragmentView.findViewById(R.id.btnAddImage);
        btnSelectImage = fragmentView.findViewById(R.id.btnSelectImage);
        rvImage = fragmentView.findViewById(R.id.rvImage);
        rvImage.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(taskFormActivity, AppUitility.calculateNoOfColumns(taskFormActivity,120));
        rvImage.setLayoutManager(mGridLayoutManager);

        if(AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)){
            if(taskFormActivity.taskDataEntry.getImageInfos()==null) {
                taskFormActivity.imageInfos = new ArrayList<>();
            } else {
                taskFormActivity.imageInfos = taskFormActivity.taskDataEntry.getImageInfos();
            }
        } else if(AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            if(taskFormActivity.taskDataEntry.getImageInfosQC()==null) {
                taskFormActivity.imageInfos = new ArrayList<>();
            } else {
                taskFormActivity.imageInfos = taskFormActivity.taskDataEntry.getImageInfosQC();
            }

            Log.e("uploadDoc","showqc:"+taskFormActivity.imageInfos.size());
        }
        taskImageHolderAdapter = new TaskImageHolderAdapter(taskFormActivity,taskID,taskFormActivity.imageInfos );
        rvImage.setAdapter(taskImageHolderAdapter);
        if((taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode)&&!AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            btnAddImage.setVisibility(View.GONE);
            btnSelectImage.setVisibility(View.GONE);
        }
    }

    private void setEvent(){
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Date date = Calendar.getInstance().getTime();
                    Log.e("userrole","1:"+AppPreference.getUserRole(taskFormActivity));
                    if(AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                        dispatchTakePictureIntentInternal("img_general_" + taskFormActivity.taskDataEntry.getTaskFlowGroup() + android.text.format.DateFormat.format("yyMMddhhmmss", date).toString());
                    } else {
                        dispatchTakePictureIntentInternal("img_QC_" + taskFormActivity.taskDataEntry.getTaskFlowGroup() + android.text.format.DateFormat.format("yyMMddhhmmss", date).toString());
                    }
                } else {
                    ActivityCompat.requestPermissions(taskFormActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstant.REQUEST_CODE_READ_EXTERNAL_STORAGE);
                }
            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                Intent chooserIntent = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(chooserIntent, AppConstant.REQUEST_CODE_PICK_IMAGE);
            }
        });
    }

    private void dispatchTakePictureIntentInternal(String fileName) {
            if(taskFormActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(taskFormActivity.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = getOutputMediaFile(fileName);
                    } catch (Exception ex) {
                        Log.e("photoerror",ex.toString());
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(taskFormActivity,
                                    AppConstant.AUTHORITY_PROVIDER,
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, AppConstant.REQUEST_CODE_GET_PHOTO);
                        } catch (Exception e){
                            Log.e("photoerror",e.toString());
                        }
                    }
                }
            } else {
                ActivityCompat.requestPermissions(taskFormActivity, new String[]{Manifest.permission.CAMERA}, AppConstant.REQUEST_CODE_CAMERA);
            }
    }

    private File  getOutputMediaFile (String fileName)
    {
        File mediaStorageDir = taskFormActivity.getExternalFilesDir(taskID);


        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        mFilenname = fileName+".jpg";
        File returnFile = new File(mediaStorageDir.getPath() + File.separator +
                mFilenname);
        return returnFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode) {

                case AppConstant.REQUEST_CODE_GET_PHOTO:{
                    if(taskFormActivity.imageInfos==null){
                        taskFormActivity.imageInfos = new ArrayList<>();
                    }
                    int imgRowNo = taskFormActivity.imageInfos.size();
                    ImageInfo imageInfo = new ImageInfo(imgRowNo,
                            mFilenname,
                            "general : "+mFilenname);
                    taskFormActivity.imageInfos.add(imageInfo);
                    taskImageHolderAdapter.notifyDataSetChanged();

                    break;
                }
                case  AppConstant.REQUEST_CODE_PICK_IMAGE:{

//                    final Cursor cursor = taskFormActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            null, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

                    Uri pickedImage = data.getData();
                    // Let's read picked image path using content resolver
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = taskFormActivity.getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));



                    // Now we need to set the GUI ImageView data with data read from the picked file.
//                    Bitmap bmp = BitmapFactory.decodeFile(datapath);

                    Bitmap bmp;
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//                    bmp = BitmapFactory.decodeFile(datapath, options);
                    bmp = taskFormActivity.getResizedBitmap(BitmapFactory.decodeFile(datapath));

                    ImageSaver imageSaver = new ImageSaver(taskFormActivity);
                    Date date = Calendar.getInstance().getTime();
                    String fileName;
                    Log.e("userrole","2:"+AppPreference.getUserRole(taskFormActivity));
                    if(AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                        fileName = "img_general_"+taskFormActivity.taskDataEntry.getTaskFlowGroup()+android.text.format.DateFormat.format("yyMMddhhmmss", date).toString();
                    } else {
                        fileName = "img_QC_"+taskFormActivity.taskDataEntry.getTaskFlowGroup()+android.text.format.DateFormat.format("yyMMddhhmmss", date).toString();

                    }

                    imageSaver.setFileName(fileName+".jpg")
                            .setExternal(false)
                            .setDirectory(taskFormActivity.taskDataEntry.getTaskID());
                    boolean saveSucceed = imageSaver.save(bmp,50);
                    if(saveSucceed) {
                        Log.e("showimage","save success");
                        int imgRowNo = taskFormActivity.imageInfos.size();
                        ImageInfo imageInfo = new ImageInfo(imgRowNo,
                                fileName+".jpg",
                                "truck Image : " + fileName);
                        taskFormActivity.imageInfos.add(imageInfo);
                        taskImageHolderAdapter.notifyDataSetChanged();
                    }
                    // At the end remember to close the cursor or you will end with the RuntimeException!
                    cursor.close();


                    break;
                }
                default:{
                    break;
                }
            }

        }
    }

}
