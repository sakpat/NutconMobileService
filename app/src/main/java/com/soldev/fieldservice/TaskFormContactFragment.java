package com.soldev.fieldservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.soldev.prod.mobileservice.R;;


public class TaskFormContactFragment extends Fragment {
    private TaskFormActivity taskFormActivity;
    private EditText edtContactName,edtContactNo,edtSalesName,edtSalesNo,edtAdminRemark;
    private View view;


    public TaskFormContactFragment() {
        // Required empty public constructor
    }


    public static TaskFormContactFragment newInstance() {
        TaskFormContactFragment fragment = new TaskFormContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setBinding(){
        taskFormActivity = (TaskFormActivity) getActivity();
        edtContactName = view.findViewById(R.id.edtContactName);
        edtContactNo = view.findViewById(R.id.edtContactNo);
        edtSalesName = view.findViewById(R.id.edtSalesName);
        edtSalesNo = view.findViewById(R.id.edtSalesNo);
        edtContactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", edtContactNo.getText().toString(), null));
                startActivity(intent);
            }
        });
        edtSalesNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", edtSalesNo.getText().toString(), null));
                startActivity(intent);
            }
        });
        edtAdminRemark = view.findViewById(R.id.edtAdminRemark);
        if(taskFormActivity.taskDataEntry.getAdminRemark()!=null){
            edtAdminRemark.setText(taskFormActivity.taskDataEntry.getAdminRemark());
            view.findViewById(R.id.txtAdminRemark).setVisibility(View.VISIBLE);
            edtAdminRemark.setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.txtAdminRemark).setVisibility(View.GONE);
            edtAdminRemark.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_form_contact, container, false);
        setBinding();
        setData();
        return view;
    }


    private void setData(){
        edtContactName.setText(taskFormActivity.taskDataEntry.getContactName());
        edtContactNo.setText(taskFormActivity.taskDataEntry.getContactNo());
        edtSalesName.setText(taskFormActivity.taskDataEntry.getSaleName());
        edtSalesNo.setText(taskFormActivity.taskDataEntry.getSaleNumber());
    }



}
