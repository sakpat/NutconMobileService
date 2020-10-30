package com.soldev.fieldservice;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.fieldservice.dataentity.ImageInfo;
import com.soldev.fieldservice.dataentity.TaskDataConcreteTruck;
import com.soldev.fieldservice.uiadapter.CementTruckHolderAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.ImageSaver;
import com.soldev.fieldservice.utilities.RecyclerItemClickListener;
import com.soldev.prod.mobileservice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormPumpPerformTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormPumpPerformTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ImageView imgSignature,imgDrawing,imgPipeDrawing;
    private Button btnSignature,btnDrawing,btnPipeDrawing;
    // TODO: Rename and change types of parameters
    private String taskId;
    private View fragmentView;
    private TaskFormActivity taskFormActivity;
    private ImageButton imgNew;
    private RecyclerView rvCementTruckList;
    private CementTruckHolderAdapter cementTruckHolderAdapter;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private List<ImageInfo> imageInfoList;
    private String mFilenname;
    private List<ImageInfo> mImageInfos;
    private TextView edtPipeLenght,edtPipeRemark,edtUsedConcrete,txtCheckIn,txtMinimum,txtExceed,edtCheckIn,edtTotalVolumn;

    public TaskFormPumpPerformTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskId Parameter 1.
     * @return A new instance of fragment TaskFormPumpPerformTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormPumpPerformTabFragment newInstance(String taskId) {
        TaskFormPumpPerformTabFragment fragment = new TaskFormPumpPerformTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_task_form_pump_perform_tab, container, false);
        setBinding();
        setEvent();
        showTaskData();
        return  fragmentView;
    }

    private void setBinding(){
        edtCheckIn = fragmentView.findViewById(R.id.edtCheckIn);
        txtCheckIn = fragmentView.findViewById(R.id.txtCheckIn);
        imgSignature = fragmentView.findViewById(R.id.imgSignature);
        imgDrawing = fragmentView.findViewById(R.id.imgDrawing);
        imgPipeDrawing = fragmentView.findViewById(R.id.imgPipeDrawing);
        edtUsedConcrete = fragmentView.findViewById(R.id.edtUsedConcrete);
        edtPipeLenght = fragmentView.findViewById(R.id.edtPipeLenght);
        edtTotalVolumn = fragmentView.findViewById(R.id.edtTotalVolumn);
        edtPipeRemark = fragmentView.findViewById(R.id.edtPipeRemark);
        btnSignature = fragmentView.findViewById(R.id.btnSignature);
        btnDrawing = fragmentView.findViewById(R.id.btnDrawing);
        btnPipeDrawing = fragmentView.findViewById(R.id.btnPipeDrawing);
        txtMinimum = fragmentView.findViewById(R.id.txtMinimum);
        txtExceed = fragmentView.findViewById(R.id.txtExceed);
        taskFormActivity = (TaskFormActivity) getActivity();
        taskFormActivity.taskDataConcreteTrucks = new ArrayList<>();
        imgNew = fragmentView.findViewById(R.id.imgNew);
        rvCementTruckList = fragmentView.findViewById(R.id.rvCementTruckList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCementTruckList.setLayoutManager(layoutManager);
        if(taskFormActivity.taskDataEntry.getTaskDataConcreteTrucks()==null) {
            taskFormActivity.taskDataConcreteTrucks = new ArrayList<>();
        } else {
            taskFormActivity.taskDataConcreteTrucks = taskFormActivity.taskDataEntry.getTaskDataConcreteTrucks();
        }
        cementTruckHolderAdapter = new CementTruckHolderAdapter(getActivity(), taskFormActivity.taskDataConcreteTrucks );
        rvCementTruckList.setAdapter(cementTruckHolderAdapter);
        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            edtUsedConcrete.setEnabled(false);
            edtCheckIn.setEnabled(false);
            txtCheckIn.setEnabled(false);
            btnSignature.setVisibility(View.GONE);
            btnDrawing.setVisibility(View.GONE);
            btnPipeDrawing.setVisibility(View.GONE);
            edtPipeLenght.setEnabled(false);
            edtPipeRemark.setEnabled(false);
            edtTotalVolumn.setEnabled(false);
            imgNew.setVisibility(View.INVISIBLE);
            imgNew.setEnabled(false);
        }
        edtUsedConcrete.setText(taskFormActivity.taskDataEntry.getConcreteType());
        edtTotalVolumn.setText(AppUitility.convertDoubleToString(taskFormActivity.taskDataEntry.getTotalCementVolumn(),"##"));
        edtPipeLenght.setText(AppUitility.convertDoubleToString(taskFormActivity.taskDataEntry.getPipeLength(),"##"));
        txtMinimum.setText(AppUitility.convertDoubleToString(taskFormActivity.taskDataEntry.getMinimumCementVolumn(),"##"));
        edtCheckIn.setText(taskFormActivity.taskDataEntry.getCheckinLatitude()+","+taskFormActivity.taskDataEntry.getCheckinLongitude());
        double netDisplay=0;
        if(taskFormActivity.taskDataEntry.getMinimumCementVolumn()>
                taskFormActivity.taskDataEntry.getTotalCementVolumn()){
            netDisplay = 0;
        } else {
            netDisplay = taskFormActivity.taskDataEntry.getTotalCementVolumn() - taskFormActivity.taskDataEntry.getMinimumCementVolumn();
        }
        txtExceed.setText(AppUitility.convertDoubleToString(netDisplay,"##"));
        edtPipeRemark.setText(taskFormActivity.taskDataEntry.getPipeRemark());
    }

    private void setEvent(){
        edtUsedConcrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_used_concrete),false);
            }
        });

        edtPipeLenght.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_pipe),true);
            }
        });

        edtPipeRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
            }
        });
        edtTotalVolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogTotalPump(view,getString(R.string.label_pump_total),true,taskFormActivity.taskDataEntry.getMinimumCementVolumn(),txtExceed,true);
            }
        });
        txtCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheckInLocaiton();
            }
        });
        edtCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheckInLocaiton();
            }
        });
        btnSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                bundle.putString(AppConstant.DB_FIELD_TASK_ID,taskFormActivity.getTaskID());
                bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_SIGNATURE);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstant.REQUEST_CODE_SIGNATURE);
            }
        });
        btnDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                bundle.putString(AppConstant.DB_FIELD_TASK_ID,taskFormActivity.getTaskID());
                bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_DRAWING);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstant.REQUEST_CODE_DRAWING);
            }
        });
        btnPipeDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                bundle.putString(AppConstant.DB_FIELD_TASK_ID,taskFormActivity.getTaskID());
                bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstant.REQUEST_CODE_PIPE_DRAWING);
            }
        });
        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cementTruckDialog(-99);
            }
        });
        rvCementTruckList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvCementTruckList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                cementTruckDialog(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(!taskFormActivity.displayMode&&!taskFormActivity.deliveryModeOnly) {
                    final int recordPosition = position;
                    AlertDialog.Builder builder = new AlertDialog.Builder(taskFormActivity);
                    builder.setCancelable(false)
                            .setTitle(getString(R.string.label_confirm))
                            .setMessage(getString(R.string.label_confirm_delete))
                            .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cementTruckHolderAdapter.removeItem(recordPosition);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }

            }
        }));
    }

    private void getCheckInLocaiton(){
//        HashMap<String,Double> hashMap = taskFormActivity.getLocation();
//        edtCheckIn.setText(hashMap.get(AppConstant.DB_FIELD_LATITUDE)+","+hashMap.get(AppConstant.DB_FIELD_LONGITUDE));
        taskFormActivity.getLastLocation();
        taskFormActivity.taskDataEntry.setCheckinLatitude(taskFormActivity.latitude);
        taskFormActivity.taskDataEntry.setCheckinLongitude(taskFormActivity.longitude);
        Log.e("showlocation",taskFormActivity.latitude+","+taskFormActivity.longitude);
        edtCheckIn.setText(taskFormActivity.latitude+","+taskFormActivity.longitude);
    }

    private void showTaskData() {
        if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE, imgSignature)) {

            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_PERFORM,
                    AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg",
                    imgSignature);
        };
        if(taskFormActivity.deliveryModeOnly
                || AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)
                ||taskFormActivity.displayMode){
            if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE, imgDrawing)) {
                taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                        AppConstant.IMAGE_CAT_PERFORM,
                        AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE + ".jpg",
                        imgDrawing);
            };
        } else {
            if(taskFormActivity.taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)&&
                    taskFormActivity.taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)) {
//                if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing)) {
//                    taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
//                            AppConstant.IMAGE_CAT_PERFORM,
//                            AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg",
//                            imgDrawing);
//                };
                if(!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE, imgDrawing)){
                    if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing)) {
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                                AppConstant.IMAGE_CAT_PERFORM,
                                AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg",
                                imgDrawing);
                    };
                }
            } else {
                if(!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE, imgDrawing)){
                    if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing)) {
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                                AppConstant.IMAGE_CAT_PERFORM,
                                AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg",
                                imgDrawing);
                    };
                }
            }
        }
        if (!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING, imgPipeDrawing)) {
            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_PERFORM,
                    AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING + ".jpg",
                    imgPipeDrawing);
        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK) {

            Log.e("drawing","req code:"+requestCode);
            switch (requestCode) {
                case AppConstant.REQUEST_CODE_SIGNATURE: {
//                    loadSignatureImage();
                    loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE,imgSignature);
                    break;
                }
                case AppConstant.REQUEST_CODE_DRAWING:{
//                    loadDrawingImage();
//                    loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING,imgDrawing);
                    Log.e("drawing","load image");
                    loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE,imgDrawing);
                    break;
                }
                case AppConstant.REQUEST_CODE_PIPE_DRAWING:{
                    loadImageToView(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING,imgPipeDrawing);
                    break;
                }
                case AppConstant.REQUEST_CODE_GET_PHOTO:{
                    int imgRowNo;
                    if(imageInfoList ==null|| imageInfoList.size()==0){
                        imgRowNo =0;
                    } else {
                        imgRowNo = imageInfoList.size();
                    }

                    ImageInfo imageInfo = new ImageInfo(imgRowNo,
                            mFilenname,
                            "truck Image : "+mFilenname);
                    imageInfoList.add(imageInfo);
                    loadImageToView(imageInfoList);
                    break;
                }
                case  AppConstant.REQUEST_CODE_PICK_IMAGE:{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = taskFormActivity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    Bitmap bmp = taskFormActivity.getResizedBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    ImageSaver imageSaver = new ImageSaver(taskFormActivity);
                    Date date = Calendar.getInstance().getTime();
                    String fileName = "img_truck_"+android.text.format.DateFormat.format("yyMMddhhmmss", date).toString();
                    imageSaver.setFileName(fileName+".jpg")
                            .setExternal(false)
                            .setDirectory(taskFormActivity.taskDataEntry.getTaskID());
                    boolean saveSucceed = imageSaver.save(bmp,50);
                    if(saveSucceed) {
                        int imgRowNo;
                        if(imageInfoList ==null|| imageInfoList.size()==0){
                            imgRowNo =0;
                        } else {
                            imgRowNo = imageInfoList.size();
                        }

                        ImageInfo imageInfo = new ImageInfo(imgRowNo,
                                fileName+".jpg",
                                "truck Image : "+mFilenname);
                        imageInfoList.add(imageInfo);
                        loadImageToView(imageInfoList);
                    }
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }


    private boolean loadImageToView(String fileName,ImageView imageView){
        return taskFormActivity.loadImageToView(fileName,imageView);
    }




    public void cementTruckDialog(final int truckRecordID){
        final List<ImageInfo> tempImageList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TaskDataConcreteTruck taskDataConcreteTruck = new TaskDataConcreteTruck();
        final View dialogView = View.inflate(getActivity(), R.layout.dialog_concrete_truck_edit,null);
        String dateCurrent = "";

        imageView1 = dialogView.findViewById(R.id.img01);
        imageView2 = dialogView.findViewById(R.id.img02);
        imageView3 = dialogView.findViewById(R.id.img03);
        imageView4 = dialogView.findViewById(R.id.img04);
        imageInfoList = new ArrayList<>();

        if(truckRecordID==-99){
            dateCurrent = AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime());
            ((EditText)dialogView.findViewById(R.id.edtStartTime) ).setText(dateCurrent);
            ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText(dateCurrent);
        } else {
            mImageInfos = new ArrayList<>();
            taskDataConcreteTruck = taskFormActivity.taskDataConcreteTrucks.get(truckRecordID);
            if(taskDataConcreteTruck.getImageInfoList()!=null) {
                for (int i = 0; i < taskDataConcreteTruck.getImageInfoList().size(); i++) {
                    imageInfoList.add(taskDataConcreteTruck.getImageInfoList().get(i));
                    tempImageList.add(taskDataConcreteTruck.getImageInfoList().get(i));
                }
            }

            ((EditText)dialogView.findViewById(R.id.edtCementTruckLicense) ).setText(taskDataConcreteTruck.getLicensePlate());
            ((EditText)dialogView.findViewById(R.id.edtStartTime) ).setText(taskDataConcreteTruck.getTimeStart());
            ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText(taskDataConcreteTruck.getTimeEnd());
            ((EditText)dialogView.findViewById(R.id.edtCementAmount)).setText(Double.toString(taskDataConcreteTruck.getAmount()));
            ((EditText)dialogView.findViewById(R.id.edtCementTruckRemark) ).setText(taskDataConcreteTruck.getRemark());
            if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
                dialogView.findViewById(R.id.edtCementTruckLicense).setEnabled(false);
                dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
                dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
                dialogView.findViewById(R.id.edtCementAmount).setEnabled(false);
                dialogView.findViewById(R.id.edtCementTruckRemark).setEnabled(false);
                dialogView.findViewById(R.id.btnAddImage).setVisibility(View.GONE);
                dialogView.findViewById(R.id.btnAddImage).setEnabled(false);
            }
            loadImageToView(imageInfoList);
        }
        dialogView.findViewById(R.id.edtStartTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDate = ((EditText) view).getText().toString();
                Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyyhhmmss,txtDate);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date.getTime());
                showDateTimePicker(cal,(EditText) view);
            }
        });
        dialogView.findViewById(R.id.edtEndTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDate = ((EditText) view).getText().toString();
                Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyyhhmmss,txtDate);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date.getTime());
                showDateTimePicker(cal,(EditText) view);
            }
        });
        dialogView.findViewById(R.id.btnSelectImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, AppConstant.REQUEST_CODE_PICK_IMAGE);
            }
        });
        dialogView.findViewById(R.id.btnAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchTakePictureIntent();
                if( taskFormActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    if (imageInfoList.size() < 4) {
                        Date date = Calendar.getInstance().getTime();
                        dispatchTakePictureIntentInternal("img_truck_" + android.text.format.DateFormat.format("yyMMddhhmmss", date).toString());
                    } else {
                        Toast.makeText(taskFormActivity, "Images could not ecxeed 4", Toast.LENGTH_LONG).show();
                    }
                } else {
                    ActivityCompat.requestPermissions(taskFormActivity, new String[]{Manifest.permission.CAMERA}, AppConstant.REQUEST_CODE_CAMERA);
                }
            }
        });
        dialogView.findViewById(R.id.imgDelete1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageInfoList.size()>0) {
                    imageInfoList.remove(0);
                    loadImageToView(imageInfoList);
                }
            }
        });
        dialogView.findViewById(R.id.imgDelete2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageInfoList.size()>1) {
                    imageInfoList.remove(1);
                    loadImageToView(imageInfoList);
                }
            }
        });
        dialogView.findViewById(R.id.imgDelete3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageInfoList.size()>2) {
                    imageInfoList.remove(2);
                    loadImageToView(imageInfoList);
                }
            }
        });
        dialogView.findViewById(R.id.imgDelete4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageInfoList.size()>3) {
                    imageInfoList.remove(3);
                    loadImageToView(imageInfoList);
                }
            }
        });



        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                imageInfoList = new ArrayList<>();
//                for (int i = 0; i < mImageInfos.size(); i++) {
//                    imageInfoList.add(mImageInfos.get(i));
//                }
//                taskFormActivity.taskDataConcreteTrucks.get(truckRecordID).setImageInfoList(imageInfoList);
//                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ImageInfo> imageInfos = new ArrayList<>();
                for (int i = 0; i < imageInfoList.size(); i++) {

                    imageInfos.add(imageInfoList.get(i));
                }
                String cementAmountValue = ((EditText) dialogView.findViewById(R.id.edtCementAmount)).getText().toString();
                Double cementAmount;
                if(cementAmountValue.trim().equalsIgnoreCase("")){
                    cementAmount = 0.0;
                } else {
                    cementAmount = Double.parseDouble(cementAmountValue);
                }
                if(truckRecordID==-99) {
                    TaskDataConcreteTruck taskDataConcreteTruck =
                            new TaskDataConcreteTruck(0,
                                    ((EditText) dialogView.findViewById(R.id.edtCementTruckLicense)).getText().toString(),
                                    ((EditText) dialogView.findViewById(R.id.edtStartTime)).getText().toString(),
                                    ((EditText) dialogView.findViewById(R.id.edtEndTime)).getText().toString(),
                                    cementAmount,
                                    ((EditText) dialogView.findViewById(R.id.edtCementTruckRemark)).getText().toString(),
                                    imageInfos
                            );
                    addNewTruck(taskDataConcreteTruck);
                } else {
                    TaskDataConcreteTruck taskDataConcreteTruck =
                            new TaskDataConcreteTruck(truckRecordID,
                                    ((EditText) dialogView.findViewById(R.id.edtCementTruckLicense)).getText().toString(),
                                    ((EditText) dialogView.findViewById(R.id.edtStartTime)).getText().toString(),
                                    ((EditText) dialogView.findViewById(R.id.edtEndTime)).getText().toString(),
                                    cementAmount,
                                    ((EditText) dialogView.findViewById(R.id.edtCementTruckRemark)).getText().toString(),
                                    imageInfos
                            );
                    editTruck(truckRecordID,taskDataConcreteTruck);
                }
                alertDialog.dismiss();

            }
        });
    }

    private void addNewTruck(TaskDataConcreteTruck taskDataConcreteTruck){
        int rowNo = 0;
        if(taskFormActivity.taskDataConcreteTrucks!=null){
            rowNo = taskFormActivity.taskDataConcreteTrucks.size();
        }
        taskDataConcreteTruck.setRowNo(rowNo);
        taskFormActivity.taskDataConcreteTrucks.add(taskDataConcreteTruck);
        cementTruckHolderAdapter.notifyDataSetChanged();
    }
    private void editTruck(int rowID, TaskDataConcreteTruck taskDataConcreteTruck){
        taskFormActivity.taskDataConcreteTrucks.set(rowID,taskDataConcreteTruck);
        cementTruckHolderAdapter.notifyDataSetChanged();
    }

    public void showDateTimePicker(final Calendar calendar, final EditText editText) {
        new DatePickerDialog(taskFormActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(taskFormActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        Date selectedDate = calendar.getTime();
                        editText.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,selectedDate));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
    }

    private void dispatchTakePictureIntentInternal(String fileName) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(taskFormActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = getOutputMediaFile(fileName);
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(taskFormActivity,
                        AppConstant.AUTHORITY_PROVIDER,
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, AppConstant.REQUEST_CODE_GET_PHOTO);
            }
        }
    }

    private File  getOutputMediaFile (String fileName)
    {
        File mediaStorageDir = taskFormActivity.getExternalFilesDir(taskId);


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

    public void loadImageToView(final List<ImageInfo>  imageInfos){
        imageView1.setImageDrawable(taskFormActivity.getDrawable(R.drawable.baseline_photo_size_select_actual_black_48));
        imageView2.setImageDrawable(taskFormActivity.getDrawable(R.drawable.baseline_photo_size_select_actual_black_48));
        imageView3.setImageDrawable(taskFormActivity.getDrawable(R.drawable.baseline_photo_size_select_actual_black_48));
        imageView4.setImageDrawable(taskFormActivity.getDrawable(R.drawable.baseline_photo_size_select_actual_black_48));
        for (int i = 0; i < imageInfos.size(); i++) {
            File mediaStorageDir = taskFormActivity.getExternalFilesDir(taskId);
            String filePath = mediaStorageDir.getPath()+"/"+imageInfos.get(i).getImageName();
            File imgFile = new  File(filePath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                switch (i){
                    case 0 :{
                        imageView1.setImageBitmap(myBitmap);
                        imageView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(taskFormActivity, DisplayImageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("TASKID",taskId);
                                bundle.putString("FILENAME",imageInfos.get(0).getImageName());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        imageView1.invalidate();
                        break;
                    }
                    case 1 :{
                        imageView2.setImageBitmap(myBitmap);
                        imageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(taskFormActivity, DisplayImageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("TASKID",taskId);
                                bundle.putString("FILENAME",imageInfos.get(1).getImageName());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        imageView2.invalidate();
                        break;
                    }
                    case 2 :{
                        imageView3.setImageBitmap(myBitmap);
                        imageView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(taskFormActivity, DisplayImageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("TASKID",taskId);
                                bundle.putString("FILENAME",imageInfos.get(2).getImageName());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        imageView3.invalidate();
                        break;
                    }
                    case 3 :{
                        imageView4.setImageBitmap(myBitmap);
                        imageView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(taskFormActivity, DisplayImageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("TASKID",taskId);
                                bundle.putString("FILENAME",imageInfos.get(3).getImageName());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        imageView4.invalidate();
                        break;
                    }
                    default:{
                        break;
                    }
                }

            } else {
                Log.e("loadimage","not found:"+i);
                switch (i){
                    case 0 :{
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskID(),
                                AppConstant.IMAGE_CAT_TRUCK,imageInfos.get(i).getImageName(),imageView1);
                        imageView1.invalidate();
                        break;
                    }
                    case 1 :{
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskID(),
                                AppConstant.IMAGE_CAT_TRUCK,imageInfos.get(i).getImageName(),imageView2);
                        imageView2.invalidate();
                        break;
                    }
                    case 2 :{
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskID(),
                                AppConstant.IMAGE_CAT_TRUCK,imageInfos.get(i).getImageName(),imageView3);

                        imageView3.invalidate();
                        break;
                    }
                    case 3 :{
                        taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskID(),
                                AppConstant.IMAGE_CAT_TRUCK,imageInfos.get(i).getImageName(),imageView4);
                        imageView4.invalidate();
                        break;
                    }
                    default:{
                        break;
                    }
                }

            }
        }

    }

    public HashMap<String,String> getData(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("edtUsedConcrete",edtUsedConcrete.getText().toString());
        hashMap.put("edtPipeLenght",edtPipeLenght.getText().toString());
        hashMap.put("edtPipeRemark",edtPipeRemark.getText().toString());
        hashMap.put("edtTotalVolumn",edtTotalVolumn.getText().toString());
        hashMap.put("edtCheckIn",edtCheckIn.getText().toString());
        hashMap.put("imgSignature", AppConstant.DB_FIELD_FILE_NAME_SIGNATURE+".jpg");
        hashMap.put("imgDrawing", AppConstant.DB_FIELD_FILE_NAME_DRAWING+".jpg");
        hashMap.put("imgPipeDrawing", AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING+".jpg");
        return  hashMap;
    }

}
