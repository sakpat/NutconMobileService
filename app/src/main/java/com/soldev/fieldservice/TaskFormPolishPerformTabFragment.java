package com.soldev.fieldservice;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.prod.mobileservice.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormPolishPerformTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormPolishPerformTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String taskID;
    private View fragmentView;
    private TaskFormActivity taskFormActivity;
    private TextView edtUsedConcrete,edtCheckIn,edtPlanArea,edtActualArea,edtPolishRemark,txtCheckIn;
    private Button btnSignature,btnDrawing;
    private ImageView imgSignature,imgDrawing;


    public TaskFormPolishPerformTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskID Parameter 1.
     * @return A new instance of fragment TaskFormPolishPerformTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormPolishPerformTabFragment newInstance(String taskID) {
        TaskFormPolishPerformTabFragment fragment = new TaskFormPolishPerformTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, taskID);
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
        fragmentView =inflater.inflate(R.layout.fragment_task_form_polish_perform_tab, container, false);
        setBinding();
        showData();
        setEvent();
        return fragmentView;
    }

    private void setBinding(){
        taskFormActivity = (TaskFormActivity) getActivity();
        edtUsedConcrete = fragmentView.findViewById(R.id.edtUsedConcrete);
        edtCheckIn = fragmentView.findViewById(R.id.edtCheckIn);
        edtPlanArea = fragmentView.findViewById(R.id.edtPlanArea);
        edtActualArea = fragmentView.findViewById(R.id.edtActualArea);
        edtPolishRemark = fragmentView.findViewById(R.id.edtPolishRemark);
        txtCheckIn = fragmentView.findViewById(R.id.txtCheckIn);
        btnSignature =  fragmentView.findViewById(R.id.btnSignature);
        btnDrawing =  fragmentView.findViewById(R.id.btnDrawing);
        imgSignature =  fragmentView.findViewById(R.id.imgSignature);
        imgDrawing  =  fragmentView.findViewById(R.id.imgDrawing);
        if(taskFormActivity.displayMode){
            edtUsedConcrete.setEnabled(false);
            edtCheckIn.setEnabled(false);
            txtCheckIn.setEnabled(false);
            btnSignature.setVisibility(View.GONE);
            btnDrawing.setVisibility(View.GONE);
            edtPlanArea.setEnabled(false);
            edtActualArea.setEnabled(false);
            edtPolishRemark.setEnabled(false);
        } else if(taskFormActivity.deliveryModeOnly){
            edtUsedConcrete.setEnabled(false);
            edtCheckIn.setEnabled(false);
            txtCheckIn.setEnabled(false);
            btnSignature.setVisibility(View.GONE);
            btnDrawing.setVisibility(View.GONE);
            edtPlanArea.setEnabled(false);
            edtPolishRemark.setEnabled(false);
        }
        edtCheckIn.setText(taskFormActivity.taskDataEntry.getCheckinLatitude()+","+taskFormActivity.taskDataEntry.getCheckinLongitude());
    }

    private void setEvent(){
        edtUsedConcrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_used_concrete),false);
            }
        });
        edtPlanArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_polished_actual_area),true);
            }
        });
        edtPolishRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
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

        edtActualArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
            }
        });
    }

    private void getCheckInLocaiton(){
//        HashMap<String,Double> hashMap = taskFormActivity.getLocation();
//        edtCheckIn.setText(hashMap.get(AppConstant.DB_FIELD_LATITUDE)+","+hashMap.get(AppConstant.DB_FIELD_LONGITUDE));
        taskFormActivity.getLastLocation();
        taskFormActivity.taskDataEntry.setCheckinLatitude(taskFormActivity.latitude);
        taskFormActivity.taskDataEntry.setCheckinLongitude(taskFormActivity.longitude);
        edtCheckIn.setText(taskFormActivity.latitude+","+taskFormActivity.longitude);
    }

    private void showData(){
        edtUsedConcrete.setText(taskFormActivity.taskDataEntry.getProductType());
        edtPlanArea.setText(taskFormActivity.taskDataEntry.getAreaByOrder());
        edtActualArea.setText(taskFormActivity.taskDataEntry.getAreaPerform());
        edtPolishRemark.setText(taskFormActivity.taskDataEntry.getPolishRemark());
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
        if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE, imgSignature)) {

            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_PERFORM,
                    AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg",
                    imgSignature);
        };
//        if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing)) {
//            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
//                    AppConstant.IMAGE_CAT_PERFORM,
//                    AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg",
//                    imgDrawing);
//        };
        if(taskFormActivity.deliveryModeOnly
                || AppPreference.getUserRole(taskFormActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)
                ||taskFormActivity.displayMode){
            if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE, imgDrawing)) {
                taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                        AppConstant.IMAGE_CAT_PERFORM,
                        AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE + ".jpg",
                        imgDrawing);
            };
        } else {
            if(taskFormActivity.taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)&&
                    taskFormActivity.taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)) {
                if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing)) {
                    taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                            AppConstant.IMAGE_CAT_PERFORM,
                            AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg",
                            imgDrawing);
                };
            } else {
                if(!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE, imgDrawing)){
                    taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING, imgDrawing);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstant.REQUEST_CODE_SIGNATURE: {
//                    loadSignatureImage();
                    taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE,imgSignature);
                    break;
                }
                case AppConstant.REQUEST_CODE_DRAWING:{
//                    loadDrawingImage();
                    taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE,imgDrawing);
                    break;
                }

                default:{
                    break;
                }
            }
        }
    }

    public HashMap<String,String> getData(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("edtUsedConcrete",edtUsedConcrete.getText().toString());
        hashMap.put("edtCheckIn",edtCheckIn.getText().toString());
        hashMap.put("edtPlanArea",edtPlanArea.getText().toString());
        hashMap.put("edtActualArea",edtActualArea.getText().toString());
        hashMap.put("edtPolishRemark",edtPolishRemark.getText().toString());
        return  hashMap;
    }
}
