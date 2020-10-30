package com.soldev.fieldservice;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskDataEnvironment;
import com.soldev.fieldservice.dataentity.TaskDataEnvironmentList;
import com.soldev.fieldservice.dataentity.TaskDataWorking;
import com.soldev.fieldservice.dataentity.TaskDataWorkingList;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormPerformQCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormPerformQCFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String taskID;
    private View fragmentView;
    private DatabaseReference databaseReference;
    private LinearLayout llEnvirontmentCheckingList,llSiteCheckingList;
    private TaskFormActivity taskFormActivity ;
    private CheckBox chQCBefore,chQCBetween,chQCAfter;
    private EditText edtQcBeforeTime,edtQcBetweenTime,edtQcAfterTime;
    private boolean showBefore=false,showBetween=false,showAfter=false;


    public TaskFormPerformQCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskID Parameter 1.
     * @return A new instance of fragment TaskFormPerformQC.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormPerformQCFragment newInstance(String taskID) {
        TaskFormPerformQCFragment fragment = new TaskFormPerformQCFragment();
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

        fragmentView =  inflater.inflate(R.layout.fragment_task_form_perform_qc, container, false);
        setBinding();
        setEvent();
        showTask(taskID);
        return fragmentView;
    }

    private void setBinding(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        taskFormActivity = (TaskFormActivity) getActivity();
        llEnvirontmentCheckingList = fragmentView.findViewById(R.id.llEnvirontmentCheckingList);
        llSiteCheckingList = fragmentView.findViewById(R.id.llSiteCheckingList);
        chQCBefore =  fragmentView.findViewById(R.id.chQCBefore);
        chQCBetween =  fragmentView.findViewById(R.id.chQCBetween);
        chQCAfter =  fragmentView.findViewById(R.id.chQCAfter);
        edtQcBeforeTime =  fragmentView.findViewById(R.id.edtQcBeforeTime);
        edtQcBetweenTime =  fragmentView.findViewById(R.id.edtQcBetweenTime);
        edtQcAfterTime =  fragmentView.findViewById(R.id.edtQcAfterTime);
        edtQcBeforeTime.setText(taskFormActivity.taskDataEntry.getQcBeforeTime());
        edtQcBetweenTime.setText(taskFormActivity.taskDataEntry.getQcBetweenTime());
        edtQcAfterTime.setText(taskFormActivity.taskDataEntry.getQcAfterTime());
        if(edtQcBeforeTime.getText().toString().trim().length()>0){
            chQCBefore.setChecked(true);
            edtQcBeforeTime.setClickable(true);
            edtQcBeforeTime.setEnabled(true);
            showBefore = true;
        } else {
            chQCBefore.setChecked(false);
        }
        if(edtQcBetweenTime.getText().toString().trim().length()>0){
            chQCBetween.setChecked(true);;
            edtQcBetweenTime.setClickable(true);
            edtQcBetweenTime.setEnabled(true);
            showBetween = true;
        } else {
            chQCBetween.setChecked(false);
        }
        if(edtQcAfterTime.getText().toString().trim().length()>0){
            chQCAfter.setChecked(true);;
            edtQcAfterTime.setClickable(true);
            edtQcAfterTime.setEnabled(true);
            showAfter = true;
        } else {
            chQCAfter.setChecked(false);
        }
    }

    public void showTask(String taskIdShow){
//        Log.e("shoetaskdate",taskFormActivity.taskDataEntry.getTaskNo());

//        Log.e("showqccheck","beforeshowlist>"+taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().size());

        showSiteList(taskIdShow);
//        Log.e("showqccheck","aftershowlist>"+taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().size());
    }

    private void showQCDate(TaskDataEntry taskData){
        if(taskData.getQcBeforeTime()!=null&&!taskData.getQcBeforeTime().equalsIgnoreCase("")){
            chQCBefore.setChecked(true);
            edtQcBeforeTime.setText(taskData.getQcBeforeTime());
        }
        if(taskData.getQcBetweenTime()!=null&&!taskData.getQcBetweenTime().equalsIgnoreCase("")){
            chQCBetween.setChecked(true);
            edtQcBetweenTime.setText(taskData.getQcBetweenTime());
        }
        if(taskData.getQcAfterTime()!=null&&!taskData.getQcAfterTime().equalsIgnoreCase("")){
            chQCAfter.setChecked(true);
            edtQcAfterTime.setText(taskData.getQcAfterTime());
        }
    }

    private void showSiteList(String taskID){
        Log.e("shoetaskdate","llllllllllllllll");
        if(taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList()!=null){
            llSiteCheckingList.removeAllViews();
            Log.e("shoetaskdate","fromlist:qc check  local");
            Log.e("shoetaskdate","fromlist:qc check size>"+taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().size());
            showQCDate(taskFormActivity.taskDataEntry);

            for (int h = 0; h < taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().size(); h++) {
                TaskDataWorkingList taskDataWorkingList = taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().get(h);
                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataWorkingList.getDescription());
                llSiteCheckingList.addView(head);
                LinearLayout lldataList = head.findViewById(R.id.llEquiptmentList);
                Log.e("shoetaskdate","fromlist:qc cgetTaskDataWorkings>"+taskDataWorkingList.getTaskDataWorkings().size());
                for (int i = 0; i < taskDataWorkingList.getTaskDataWorkings().size(); i++) {
                    TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(i);
                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                    view.findViewById(R.id.llRowCheckList).setId(taskDataWorking.getRowNo());
                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataWorking.getRowNo() + 1));
                    String display = taskDataWorking.getDescription();
                    AppUitility.setText(view, R.id.txtCheckListName, display);
                    Log.e("shoetaskdate",display);
                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);
                    view.findViewById(R.id.edtReasonBefore).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
                        }
                    });
                    view.findViewById(R.id.edtReasonBetween).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
                        }
                    });
                    view.findViewById(R.id.edtReasonAfter).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
                        }
                    });
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataWorking.getFoundBefore());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataWorking.getFoundBetween());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataWorking.getFoundAfter());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
                            if (view.findViewById(R.id.edtReasonBefore) != null) {
                                if (i == 0 || i == 1) {
                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHint(getString(R.string.label_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHintTextColor(Color.BLACK);
                                } else {
                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHint(getString(R.string.label_please_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHintTextColor(Color.RED);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
                            if (view.findViewById(R.id.edtReasonBetween) != null) {
                                if (i == 0 || i == 1) {
                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHint(getString(R.string.label_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHintTextColor(Color.BLACK);
                                } else {
                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHint(getString(R.string.label_please_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHintTextColor(Color.RED);
                                }
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
                            if (view.findViewById(R.id.edtReasonAfter) != null) {
                                if (i == 0 || i == 1) {
                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHint(getString(R.string.label_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHintTextColor(Color.BLACK);
                                } else {
                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHint(getString(R.string.label_please_remark));
                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHintTextColor(Color.RED);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setText(taskDataWorking.getRemarkBefore());
                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setText(taskDataWorking.getRemarkBetween());
                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setText(taskDataWorking.getRemarkAfter());
                    view.findViewById(R.id.llBefore).setTag(taskDataWorking.isCheckBefore());
                    view.findViewById(R.id.llBetween).setTag(taskDataWorking.isCheckBetween());
                    view.findViewById(R.id.llAfter).setTag(taskDataWorking.isCheckAfter());
                    if (!taskDataWorking.isCheckBefore()) {
                        view.findViewById(R.id.llBefore).setVisibility(View.GONE);
                    }
                    if (!taskDataWorking.isCheckBetween()) {
                        view.findViewById(R.id.llBetween).setVisibility(View.GONE);
                    }
                    if (!taskDataWorking.isCheckAfter()) {
                        view.findViewById(R.id.llAfter).setVisibility(View.GONE);
                    }
                    lldataList.addView(view);
                }
            }
            hidnshowList();
        }
//        else {
//            if(taskID!=null)
//            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    taskFormActivity.taskDataEntry = dataSnapshot.getValue(TaskDataEntry.class);
//                    llSiteCheckingList.removeAllViews();
//                    Log.e("shoetaskdate","fromlist:qc check  fb");
//                    if (taskFormActivity.taskDataEntry != null) {
//                        showQCDate(taskFormActivity.taskDataEntry);
//                        if (taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList() != null) {
//                            for (int h = 0; h < taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().size(); h++) {
//                                TaskDataWorkingList taskDataWorkingList = taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().get(h);
//                                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
//                                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataWorkingList.getDescription());
//                                llSiteCheckingList.addView(head);
//                                LinearLayout lldataList = head.findViewById(R.id.llEquiptmentList);
//                                for (int i = 0; i < taskDataWorkingList.getTaskDataWorkings().size(); i++) {
//                                    TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(i);
//                                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
//                                    view.findViewById(R.id.llRowCheckList).setId(taskDataWorking.getRowNo());
//                                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataWorking.getRowNo() + 1));
//                                    String display = taskDataWorking.getDescription();
//                                    AppUitility.setText(view, R.id.txtCheckListName, display);
//                                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);
//                                    view.findViewById(R.id.edtReasonBefore).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
//                                        }
//                                    });
//                                    view.findViewById(R.id.edtReasonBetween).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
//                                        }
//                                    });
//                                    view.findViewById(R.id.edtReasonAfter).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            taskFormActivity.dialogRemark(view, getString(R.string.label_remark), false);
//                                        }
//                                    });
//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataWorking.getFoundBefore());
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataWorking.getFoundBetween());
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataWorking.getFoundAfter());
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
//                                            if (view.findViewById(R.id.edtReasonBefore) != null) {
//                                                if (i == 0 || i == 1) {
//                                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHint(getString(R.string.label_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHintTextColor(Color.BLACK);
//                                                } else {
//                                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHint(getString(R.string.label_please_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setHintTextColor(Color.RED);
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
//                                            if (view.findViewById(R.id.edtReasonBetween) != null) {
//                                                if (i == 0 || i == 1) {
//                                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHint(getString(R.string.label_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHintTextColor(Color.BLACK);
//                                                } else {
//                                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHint(getString(R.string.label_please_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setHintTextColor(Color.RED);
//                                                }
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
//                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
//                                            if (view.findViewById(R.id.edtReasonAfter) != null) {
//                                                if (i == 0 || i == 1) {
//                                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHint(getString(R.string.label_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHintTextColor(Color.BLACK);
//                                                } else {
//                                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHint(getString(R.string.label_please_remark));
//                                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setHintTextColor(Color.RED);
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
//
//                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setText(taskDataWorking.getRemarkBefore());
//                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setText(taskDataWorking.getRemarkBetween());
//                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setText(taskDataWorking.getRemarkAfter());
////                                    if (!taskDataWorking.isCheckBefore()) {
////                                        view.findViewById(R.id.llBefore).setVisibility(View.GONE);
////                                    }
////                                    if (!taskDataWorking.isCheckBetween()) {
////                                        view.findViewById(R.id.llBetween).setVisibility(View.GONE);
////                                    }
////                                    if (!taskDataWorking.isCheckAfter()) {
////                                        view.findViewById(R.id.llAfter).setVisibility(View.GONE);
////                                    }
//                                    view.findViewById(R.id.llBefore).setTag(taskDataWorking.isCheckBefore());
//                                    view.findViewById(R.id.llBetween).setTag(taskDataWorking.isCheckBetween());
//                                    view.findViewById(R.id.llAfter).setTag(taskDataWorking.isCheckAfter());
//                                    if (!taskDataWorking.isCheckBefore()) {
//                                        view.findViewById(R.id.llBefore).setVisibility(View.GONE);
//                                    }
//                                    if (!taskDataWorking.isCheckBetween()) {
//                                        view.findViewById(R.id.llBetween).setVisibility(View.GONE);
//                                    }
//                                    if (!taskDataWorking.isCheckAfter()) {
//                                        view.findViewById(R.id.llAfter).setVisibility(View.GONE);
//                                    }
//                                    lldataList.addView(view);
//                                }
//                            }
//                        }
//
//                    }
//
////                    hidnshowList();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
    }

    private void hidnshowList(){
        Log.e("shoetaskdate","llSiteCheckingList count:"+llSiteCheckingList.getChildCount());
        for (int i = 0; i < llSiteCheckingList.getChildCount() ; i++) {
//            LinearLayout headGroupList = (LinearLayout) llSiteCheckingList.getChildAt(i);
            LinearLayout lldataList = ((LinearLayout) llSiteCheckingList.getChildAt(i)).findViewById(R.id.llEquiptmentList);
            for (int j = 0; j < lldataList.getChildCount(); j++) {
                String desc = ((TextView) lldataList.getChildAt(j).findViewById(R.id.txtCheckListName)).getText().toString();
                boolean beforeShow=false,betweenShow=false,afterShow=false;

                if(showBefore&&((boolean)lldataList.getChildAt(j).findViewById(R.id.llBefore).getTag())){
                    lldataList.getChildAt(j).findViewById(R.id.llBefore).setVisibility(View.VISIBLE);
                    beforeShow=true;
                } else {
                    lldataList.getChildAt(j).findViewById(R.id.llBefore).setVisibility(View.GONE);
                }
                if(showBetween&&((boolean)lldataList.getChildAt(j).findViewById(R.id.llBetween).getTag())){
                    lldataList.getChildAt(j).findViewById(R.id.llBetween).setVisibility(View.VISIBLE);
                    betweenShow=true;
                } else {
                    lldataList.getChildAt(j).findViewById(R.id.llBetween).setVisibility(View.GONE);
                }
                if(showAfter&&((boolean)lldataList.getChildAt(j).findViewById(R.id.llAfter).getTag())){
                    lldataList.getChildAt(j).findViewById(R.id.llAfter).setVisibility(View.VISIBLE);
                    afterShow=true;
                } else {
                    lldataList.getChildAt(j).findViewById(R.id.llAfter).setVisibility(View.GONE);
                }
//                if(!(((boolean)lldataList.getChildAt(j).findViewById(R.id.llBefore).getTag())||
//                        ((boolean)lldataList.getChildAt(j).findViewById(R.id.llBetween).getTag())||
//                        ((boolean)lldataList.getChildAt(j).findViewById(R.id.llAfter).getTag()))){
//                    lldataList.getChildAt(j).setVisibility(View.GONE);
//                }

                if(!(beforeShow||betweenShow||afterShow)){
                    lldataList.getChildAt(j).setVisibility(View.GONE);
                } else {
                    lldataList.getChildAt(j).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setEvent(){
        tiggerCalendar(edtQcBeforeTime);
        tiggerCalendar(edtQcBetweenTime);
        tiggerCalendar(edtQcAfterTime);
        chQCBefore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtQcBeforeTime.setClickable(true);
                    edtQcBeforeTime.setEnabled(true);
                    edtQcBeforeTime.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime()));
                    showBefore = true;
                } else {
                    edtQcBeforeTime.setClickable(false);
                    edtQcBeforeTime.setEnabled(false);
                    edtQcBeforeTime.setText("");
                    showBefore = false;
                }
                hidnshowList();
            }
        });

        chQCBetween.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtQcBetweenTime.setClickable(true);
                    edtQcBetweenTime.setEnabled(true);
                    showBetween = true;
                    edtQcBetweenTime.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime()));
                } else {
                    edtQcBetweenTime.setClickable(false);
                    edtQcBetweenTime.setEnabled(false);
                    edtQcBetweenTime.setText("");
                    showBetween = false;
                }
                hidnshowList();
            }
        });
        chQCAfter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtQcAfterTime.setClickable(true);
                    edtQcAfterTime.setEnabled(true);
                    showAfter = true;
                    edtQcAfterTime.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime()));
                } else {
                    edtQcAfterTime.setClickable(false);
                    edtQcAfterTime.setEnabled(false);
                    edtQcAfterTime.setText("");
                    showAfter = false;
                }
                hidnshowList();
            }
        });
    }

    private void tiggerCalendar(EditText edittext){
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDate = ((EditText) view).getText().toString();
                Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyyhhmmss,txtDate);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date.getTime());
                showDateTimePicker(cal,(EditText) view);
            }
        });
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

    public HashMap<String,String> getData(){
        HashMap<String,String> returnData = new HashMap<>();
        returnData.put("edtQcBeforeTime",edtQcBeforeTime.getText().toString());
        returnData.put("edtQcBetweenTime",edtQcBetweenTime.getText().toString());
        returnData.put("edtQcAfterTime",edtQcAfterTime.getText().toString());
        return returnData;
    }
    public List<TaskDataWorkingList> getTaskDataWorkingList(){
        LinearLayout llContainer = llSiteCheckingList;
        List<TaskDataWorkingList> taskDataWorkingLists = new ArrayList<>();
        for (int i = 0; i < llContainer.getChildCount() ; i++) {
            LinearLayout linearLayout = llContainer.getChildAt(i).findViewById(R.id.llEquiptmentList);
            List<TaskDataWorking> taskDataWorkings = new ArrayList<>();
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                String eqName = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtCheckListName)).getText().toString();
                boolean checkBefore=false,checkBetween=false,checkAfter=false;
                int foundBefore=0,foundBetween=0,foundAfter=0;
                String remarkBefore="",remarkBetween="",remarkAfter="";
                if(((boolean)linearLayout.getChildAt(j).findViewById(R.id.llBefore).getTag())){
                    checkBefore=taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().get(i).getTaskDataWorkings().get(j).isCheckBefore();
                    foundBefore= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBefore)).getSelectedItemPosition();
                    remarkBefore = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBefore)).getText().toString();
                }
                if(((boolean)linearLayout.getChildAt(j).findViewById(R.id.llBetween).getTag())){
                    checkBetween=taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().get(i).getTaskDataWorkings().get(j).isCheckBetween();
                    foundBetween= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBetween)).getSelectedItemPosition();
                    remarkBetween = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBetween)).getText().toString();
                }
                if(((boolean)linearLayout.getChildAt(j).findViewById(R.id.llAfter).getTag())){
                    checkAfter=taskFormActivity.taskDataEntry.getQcWorkingSiteCheckList().get(i).getTaskDataWorkings().get(j).isCheckAfter();
                    foundAfter= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundAfter)).getSelectedItemPosition();
                    remarkAfter = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonAfter)).getText().toString();
                }

                taskDataWorkings.add(new TaskDataWorking(j,eqName,checkBefore,foundBefore,remarkBefore,checkBetween,foundBetween,remarkBetween,
                        checkAfter,foundAfter,remarkAfter));
            }
            String tempDesc = ((TextView) llContainer.findViewById(R.id.txtEquipmentDesc)).getText().toString();
            taskDataWorkingLists.add(new TaskDataWorkingList(i,
                    tempDesc,
                    taskDataWorkings));
        }
        return taskDataWorkingLists;
    }
}
