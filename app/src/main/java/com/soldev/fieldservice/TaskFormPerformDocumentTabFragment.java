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
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormPerformDocumentTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormPerformDocumentTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String taskID;
    private Button btnReceiveSignature;
    private View fragmentView;
    private TaskFormActivity taskFormActivity;
    private ImageView imgReceiveSignature;
    private TextView edtCheckBy,edtContactNo,edtTaskRemark,edtDocNo,edtCheckIn;

    public TaskFormPerformDocumentTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskID Parameter 1.
     * @return A new instance of fragment TaskFormPerformDocumentTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormPerformDocumentTabFragment newInstance(String taskID) {
        TaskFormPerformDocumentTabFragment fragment = new TaskFormPerformDocumentTabFragment();
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
        fragmentView =  inflater.inflate(R.layout.fragment_task_form_perform_document_tab, container, false);
        setBinding();
        setEvent();
        showData();
        return fragmentView;
    }


    private void setBinding(){
        taskFormActivity = (TaskFormActivity) getActivity();
        edtCheckBy = fragmentView.findViewById(R.id.edtCheckBy);
        edtContactNo = fragmentView.findViewById(R.id.edtContactNo);
        edtTaskRemark = fragmentView.findViewById(R.id.edtTaskRemark);
        edtCheckIn = fragmentView.findViewById(R.id.edtCheckIn);
        btnReceiveSignature = fragmentView.findViewById(R.id.btnReceiveSignature);
        imgReceiveSignature = fragmentView.findViewById(R.id.imgReceiveSignature);
        edtDocNo = fragmentView.findViewById(R.id.edtDocNo);
        edtCheckIn.setText(taskFormActivity.taskDataEntry.getCheckinLatitude()+","+taskFormActivity.taskDataEntry.getCheckinLongitude());
        if(taskFormActivity.displayMode){
            btnReceiveSignature.setVisibility(View.GONE);
            edtCheckBy.setEnabled(false);
            edtContactNo.setEnabled(false);
            edtTaskRemark.setEnabled(false);
            edtCheckBy.setEnabled(false);
            edtDocNo.setEnabled(false);
            edtCheckIn.setEnabled(false);
        }
    }

    private void setEvent()
    {btnReceiveSignature.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            bundle.putString(AppConstant.DB_FIELD_TASK_ID,taskFormActivity.getTaskID());
            bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE);
            intent.putExtras(bundle);
            startActivityForResult(intent, AppConstant.REQUEST_CODE_SIGNATURE_RECEIVE);
        }
    });
        edtCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.getLastLocation();
                taskFormActivity.taskDataEntry.setCheckinLatitude(taskFormActivity.latitude);
                taskFormActivity.taskDataEntry.setCheckinLongitude(taskFormActivity.longitude);
                ((TextView) view).setText(taskFormActivity.latitude+","+taskFormActivity.longitude);
            }
        });

        edtCheckBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_customer_receiver),false);
            }
        });
        edtContactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_contact),false);
            }
        });
        edtTaskRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstant.REQUEST_CODE_SIGNATURE_RECEIVE:{
                    taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE,imgReceiveSignature);
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
        hashMap.put("edtCheckBy",edtCheckBy.getText().toString());
        hashMap.put("edtContactNo",edtContactNo.getText().toString());
        hashMap.put("edtTaskRemark",edtTaskRemark.getText().toString());
        hashMap.put("edtCheckIn",edtCheckIn.getText().toString());
        return  hashMap;
    }

    private void showData(){
        edtDocNo.setText(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getDocRef(),""));
        edtCheckBy.setText(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTaskReceiver(),""));
        edtContactNo.setText(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTaskReceiverContact(),""));
        edtTaskRemark.setText(taskFormActivity.taskDataEntry.getTaskDocRemark());
        if(!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE,imgReceiveSignature)){
            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_PERFORM,
                    AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg",
                    imgReceiveSignature);
        }
    }
}
