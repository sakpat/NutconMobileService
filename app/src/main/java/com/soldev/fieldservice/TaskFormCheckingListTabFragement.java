package com.soldev.fieldservice;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskDataEnvironment;
import com.soldev.fieldservice.dataentity.TaskDataEnvironmentList;
import com.soldev.fieldservice.dataentity.TaskDataVehicle;
import com.soldev.fieldservice.dataentity.TaskDataWorking;
import com.soldev.fieldservice.dataentity.TaskDataWorkingList;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormCheckingListTabFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormCheckingListTabFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String taskID;
    private DatabaseReference databaseReference;
    private View fragmentView;
    private TextView edtLicensePlate,edtAssetNo,edtStartMileage,edtEndMileage;
    private LinearLayout llEnvirontmentCheckingList, llWorkingSiteCheckingList;
    private TaskFormActivity taskFormActivity;

    public TaskFormCheckingListTabFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskID Parameter 1.
     * @return A new instance of fragment TaskFormCheckingListTabFragement.
     */
    // TODO: Rename and change types and number of parameters

    public static final int assetIDLength = 8;
    public static TaskFormCheckingListTabFragement newInstance(String taskID) {
        TaskFormCheckingListTabFragement fragment = new TaskFormCheckingListTabFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, taskID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.taskID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_task_form_checking_list_tab_fragement, container, false);
        setBinding();
        setEvent();
        showTask(taskID);
        return fragmentView;
    }

    private void setBinding() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        llEnvirontmentCheckingList = fragmentView.findViewById(R.id.llEnvirontmentCheckingList);
        llWorkingSiteCheckingList = fragmentView.findViewById(R.id.llSiteCheckingList);
        edtLicensePlate = fragmentView.findViewById(R.id.edtLicensePlate);
        edtAssetNo = fragmentView.findViewById(R.id.edtAssetNo);
        edtStartMileage = fragmentView.findViewById(R.id.edtStartMileage);
        edtEndMileage = fragmentView.findViewById(R.id.edtEndMileage);
        taskFormActivity = (TaskFormActivity) getActivity();
        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            edtLicensePlate.setEnabled(false);
            edtAssetNo.setEnabled(false);
            edtStartMileage.setEnabled(false);
            edtEndMileage.setEnabled(false);
        }
    }

    private void setEvent(){
        edtLicensePlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_license_plate),false);
            }
        });
        edtAssetNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemarkCheckLength(view,getString(R.string.label_asset_no),assetIDLength);
            }
        });
        edtStartMileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_start_mileage),true);
            }
        });
        edtEndMileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_end_mileage),true);
            }
        });
    }

    public void showTask(String taskID){
        if(taskFormActivity.taskDataEntry.getTaskDataVehicle()!=null) {
            edtLicensePlate.setText(taskFormActivity.taskDataEntry.getTaskDataVehicle().getLicensePlate());
            edtAssetNo.setText(taskFormActivity.taskDataEntry.getTaskDataVehicle().getAssetNo());
            edtStartMileage.setText(Long.toString(taskFormActivity.taskDataEntry.getTaskDataVehicle().getStartMileage()));
            edtEndMileage.setText(Long.toString(taskFormActivity.taskDataEntry.getTaskDataVehicle().getEndMileage()));
        };
        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            showEnvironmentListDeliveryMode();
            showSiteListDeliveryMode();
        } else {
            showEnvironmentList(taskID);
            showSiteList(taskID);
        }
    }

    private void showEnvironmentList(final String taskID){
        if(taskFormActivity.taskDataEntry.getEnvironmentCheckLists()!=null){
            llEnvirontmentCheckingList.removeAllViews();
            for (int h = 0; h < taskFormActivity.taskDataEntry.getEnvironmentCheckLists().size(); h++) {
                TaskDataEnvironmentList taskDataEnvironmentList = taskFormActivity.taskDataEntry.getEnvironmentCheckLists().get(h);
                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataEnvironmentList.getDescription());
                llEnvirontmentCheckingList.addView(head);
                LinearLayout llEnvironemntList = head.findViewById(R.id.llEquiptmentList);

                for (int i = 0; i < taskDataEnvironmentList.getTaskDataEnvironment().size(); i++) {
                    TaskDataEnvironment taskDataEnvironment = taskDataEnvironmentList.getTaskDataEnvironment().get(i);
                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                    view.findViewById(R.id.llRowCheckList).setId(taskDataEnvironment.getRowNo());
                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataEnvironment.getRowNo() + 1));
                    String display = taskDataEnvironment.getDescription();
                    AppUitility.setText(view, R.id.txtCheckListName, display);
                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataEnvironment.getFoundBefore());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataEnvironment.getFoundBetween());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataEnvironment.getFoundAfter());
                    if (taskFormActivity.deliveryModeOnly || taskFormActivity.displayMode) {
                        view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                        view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                        view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                        view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                        view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                        view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                    }
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
                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setText(taskDataEnvironment.getRemarkBefore());
                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setText(taskDataEnvironment.getRemarkBetween());
                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setText(taskDataEnvironment.getRemarkAfter());
                    if (!taskDataEnvironment.isCheckBefore()) {
                        view.findViewById(R.id.llBefore).setVisibility(View.GONE);
                    }
                    if (!taskDataEnvironment.isCheckBetween()) {
                        view.findViewById(R.id.llBetween).setVisibility(View.GONE);
                    }
                    if (!taskDataEnvironment.isCheckAfter()) {
                        view.findViewById(R.id.llAfter).setVisibility(View.GONE);
                    }
                    llEnvironemntList.addView(view);
                }
            }
        } else {
            Query query = databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final TaskDataEntry taskDataEntry = dataSnapshot.getValue(TaskDataEntry.class);

                    if (taskDataEntry != null) {
                        llEnvirontmentCheckingList.removeAllViews();
                        if (taskDataEntry.getEnvironmentCheckLists() != null) {
                            for (int h = 0; h < taskDataEntry.getEnvironmentCheckLists().size(); h++) {
                                TaskDataEnvironmentList taskDataEnvironmentList = taskDataEntry.getEnvironmentCheckLists().get(h);
                                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataEnvironmentList.getDescription());
                                llEnvirontmentCheckingList.addView(head);
                                LinearLayout llEnvironemntList = head.findViewById(R.id.llEquiptmentList);

                                for (int i = 0; i < taskDataEnvironmentList.getTaskDataEnvironment().size(); i++) {
                                    TaskDataEnvironment taskDataEnvironment = taskDataEnvironmentList.getTaskDataEnvironment().get(i);
                                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                                    view.findViewById(R.id.llRowCheckList).setId(taskDataEnvironment.getRowNo());
                                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataEnvironment.getRowNo() + 1));
                                    String display = taskDataEnvironment.getDescription();
                                    AppUitility.setText(view, R.id.txtCheckListName, display);
                                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataEnvironment.getFoundBefore());
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataEnvironment.getFoundBetween());
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataEnvironment.getFoundAfter());
                                    if (taskFormActivity.deliveryModeOnly || taskFormActivity.displayMode) {
                                        view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                                        view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                                        view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                                        view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                                        view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                                        view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                                    }
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
                                    ((TextView) view.findViewById(R.id.edtReasonBefore)).setText(taskDataEnvironment.getRemarkBefore());
                                    ((TextView) view.findViewById(R.id.edtReasonBetween)).setText(taskDataEnvironment.getRemarkBetween());
                                    ((TextView) view.findViewById(R.id.edtReasonAfter)).setText(taskDataEnvironment.getRemarkAfter());
                                    if (!taskDataEnvironment.isCheckBefore()) {
                                        view.findViewById(R.id.llBefore).setVisibility(View.GONE);
                                    }
                                    if (!taskDataEnvironment.isCheckBetween()) {
                                        view.findViewById(R.id.llBetween).setVisibility(View.GONE);
                                    }
                                    if (!taskDataEnvironment.isCheckAfter()) {
                                        view.findViewById(R.id.llAfter).setVisibility(View.GONE);
                                    }
                                    llEnvironemntList.addView(view);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showEnvironmentListDeliveryMode(){
        final TaskDataEntry taskDataEntry = taskFormActivity.taskDataEntry;

        if(taskDataEntry!=null) {
            llEnvirontmentCheckingList.removeAllViews();
            if (taskDataEntry.getEnvironmentCheckLists() != null) {
                for (int h = 0; h < taskDataEntry.getEnvironmentCheckLists().size(); h++) {
                    TaskDataEnvironmentList taskDataEnvironmentList = taskDataEntry.getEnvironmentCheckLists().get(h);
                    View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                    AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataEnvironmentList.getDescription());
                    llEnvirontmentCheckingList.addView(head);
                    LinearLayout llEnvironemntList = head.findViewById(R.id.llEquiptmentList);

                    for (int i = 0; i < taskDataEnvironmentList.getTaskDataEnvironment().size(); i++) {
                        TaskDataEnvironment taskDataEnvironment = taskDataEnvironmentList.getTaskDataEnvironment().get(i);
                        final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                        view.findViewById(R.id.llRowCheckList).setId(taskDataEnvironment.getRowNo());
                        AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataEnvironment.getRowNo() + 1));
                        String display = taskDataEnvironment.getDescription();
                        AppUitility.setText(view, R.id.txtCheckListName, display);
                        String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataEnvironment.getFoundBefore());
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataEnvironment.getFoundBetween());
                        ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataEnvironment.getFoundAfter());
                        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
                            view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                            view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                            view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                            view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                            view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                            view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                        }
                        view.findViewById(R.id.edtReasonBefore).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        view.findViewById(R.id.edtReasonBetween).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        view.findViewById(R.id.edtReasonAfter).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
                                if(view.findViewById(R.id.edtReasonBefore)!=null) {
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
                                if(view.findViewById(R.id.edtReasonBetween)!=null) {
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
                                if(view.findViewById(R.id.edtReasonAfter)!=null) {
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
                        ((TextView) view.findViewById(R.id.edtReasonBefore)).setText(taskDataEnvironment.getRemarkBefore());
                        ((TextView) view.findViewById(R.id.edtReasonBetween)).setText(taskDataEnvironment.getRemarkBetween());
                        ((TextView) view.findViewById(R.id.edtReasonAfter)).setText(taskDataEnvironment.getRemarkAfter());
                        if (!taskDataEnvironment.isCheckBefore()) {
                            view.findViewById(R.id.llBefore).setVisibility(View.GONE);
                        }
                        if (!taskDataEnvironment.isCheckBetween()) {
                            view.findViewById(R.id.llBetween).setVisibility(View.GONE);
                        }
                        if (!taskDataEnvironment.isCheckAfter()) {
                            view.findViewById(R.id.llAfter).setVisibility(View.GONE);
                        }
                        llEnvironemntList.addView(view);
                    }
                }
            }
        }
    }

    private void showSiteList(String taskID){
        if(taskFormActivity.taskDataEntry.getWorkingSiteCheckList()!=null){
            llWorkingSiteCheckingList.removeAllViews();
            for (int h = 0; h < taskFormActivity.taskDataEntry.getWorkingSiteCheckList().size(); h++) {
                TaskDataWorkingList taskDataWorkingList = taskFormActivity.taskDataEntry.getWorkingSiteCheckList().get(h);
                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataWorkingList.getDescription());
                llWorkingSiteCheckingList.addView(head);
                LinearLayout lldataList = head.findViewById(R.id.llEquiptmentList);
                for (int i = 0; i < taskDataWorkingList.getTaskDataWorkings().size(); i++) {
                    TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(i);
                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                    view.findViewById(R.id.llRowCheckList).setId(taskDataWorking.getRowNo());
                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataWorking.getRowNo() + 1));
                    String display = taskDataWorking.getDescription();
                    AppUitility.setText(view, R.id.txtCheckListName, display);
                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataWorking.getFoundBefore());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataWorking.getFoundBetween());
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataWorking.getFoundAfter());
                    if (taskFormActivity.deliveryModeOnly || taskFormActivity.displayMode) {
                        view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                        view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                        view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                        view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                        view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                        view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                    }
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
        } else {
            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TaskDataEntry taskDataEntry = dataSnapshot.getValue(TaskDataEntry.class);
                    llWorkingSiteCheckingList.removeAllViews();
                    if (taskDataEntry != null) {
                        if (taskDataEntry.getWorkingSiteCheckList() != null) {
                            for (int h = 0; h < taskDataEntry.getWorkingSiteCheckList().size(); h++) {
                                TaskDataWorkingList taskDataWorkingList = taskDataEntry.getWorkingSiteCheckList().get(h);
                                View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataWorkingList.getDescription());
                                llWorkingSiteCheckingList.addView(head);
                                LinearLayout lldataList = head.findViewById(R.id.llEquiptmentList);
                                for (int i = 0; i < taskDataWorkingList.getTaskDataWorkings().size(); i++) {
                                    TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(i);
                                    final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                                    view.findViewById(R.id.llRowCheckList).setId(taskDataWorking.getRowNo());
                                    AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataWorking.getRowNo() + 1));
                                    String display = taskDataWorking.getDescription();
                                    AppUitility.setText(view, R.id.txtCheckListName, display);
                                    String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataWorking.getFoundBefore());
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataWorking.getFoundBetween());
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                                    ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataWorking.getFoundAfter());
                                    if (taskFormActivity.deliveryModeOnly || taskFormActivity.displayMode) {
                                        view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                                        view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                                        view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                                        view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                                        view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                                        view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                                    }
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
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showSiteListDeliveryMode(){
        TaskDataEntry taskDataEntry = taskFormActivity.taskDataEntry;
        llWorkingSiteCheckingList.removeAllViews();
        if(taskDataEntry!=null) {
            if (taskDataEntry.getWorkingSiteCheckList() != null) {
                for (int h = 0; h < taskDataEntry.getWorkingSiteCheckList().size(); h++) {
                    TaskDataWorkingList taskDataWorkingList = taskDataEntry.getWorkingSiteCheckList().get(h);
                    View head = fragmentView.inflate(getActivity(), R.layout.row_task_equipment_header, null);
                    AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataWorkingList.getDescription());
                    llWorkingSiteCheckingList.addView(head);
                    LinearLayout lldataList = head.findViewById(R.id.llEquiptmentList);
                    for (int i = 0; i < taskDataWorkingList.getTaskDataWorkings().size(); i++) {
                        TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(i);
                        final View view = fragmentView.inflate(getActivity(), R.layout.row_task_check_list, null);
                        view.findViewById(R.id.llRowCheckList).setId(taskDataWorking.getRowNo());
                        AppUitility.setText(view, R.id.txtCheckListRow, Integer.toString(taskDataWorking.getRowNo() + 1));
                        String display = taskDataWorking.getDescription();
                        AppUitility.setText(view, R.id.txtCheckListName, display);
                        String[] checkListValues = getResources().getStringArray(R.array.check_list_value);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, checkListValues);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setSelection(taskDataWorking.getFoundBefore());
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBetween)).setSelection(taskDataWorking.getFoundBetween());
                        ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setAdapter(adapter);
                        ((Spinner) view.findViewById(R.id.spnCheckFoundAfter)).setSelection(taskDataWorking.getFoundAfter());
                        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
                            view.findViewById(R.id.spnCheckFoundBefore).setEnabled(false);
                            view.findViewById(R.id.spnCheckFoundBetween).setEnabled(false);
                            view.findViewById(R.id.spnCheckFoundAfter).setEnabled(false);
                            view.findViewById(R.id.edtReasonBefore).setEnabled(false);
                            view.findViewById(R.id.edtReasonBetween).setEnabled(false);
                            view.findViewById(R.id.edtReasonAfter).setEnabled(false);
                        }
                        view.findViewById(R.id.edtReasonBefore).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        view.findViewById(R.id.edtReasonBetween).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        view.findViewById(R.id.edtReasonAfter).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
                            }
                        });
                        ((Spinner) view.findViewById(R.id.spnCheckFoundBefore)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View innerView, int i, long l) {
                                if(view.findViewById(R.id.edtReasonBefore)!=null) {
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
                                if(view.findViewById(R.id.edtReasonBetween)!=null) {
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
                                if(view.findViewById(R.id.edtReasonAfter)!=null) {
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
            }
        }
    }

    public List<TaskDataEnvironmentList> getTaskDataEnvironmentList(){
        LinearLayout llContainer = llEnvirontmentCheckingList;
        List<TaskDataEnvironmentList> taskDataEnvironmentLists = new ArrayList<>();
        for (int i = 0; i < llContainer.getChildCount() ; i++) {
            LinearLayout linearLayout = llContainer.getChildAt(i).findViewById(R.id.llEquiptmentList);
            List<TaskDataEnvironment> taskDataEnvironments = new ArrayList<>();
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                String eqName = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtCheckListName)).getText().toString();
                boolean checkBefore=false,checkBetween=false,checkAfter=false;
                int foundBefore=0,foundBetween=0,foundAfter=0;
                String remarkBefore="",remarkBetween="",remarkAfter="";
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBefore).isShown()){
                    checkBefore=true;
                    foundBefore= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBefore)).getSelectedItemPosition();
                    remarkBefore = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBefore)).getText().toString();
                }
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBetween).isShown()){
                    checkBetween=true;
                    foundBetween= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBetween)).getSelectedItemPosition();
                    remarkBetween = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBetween)).getText().toString();
                }
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundAfter).isShown()){
                    checkAfter=true;
                    foundAfter= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundAfter)).getSelectedItemPosition();
                    remarkAfter = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonAfter)).getText().toString();
                }

                taskDataEnvironments.add(new TaskDataEnvironment(j,eqName,checkBefore,foundBefore,remarkBefore,checkBetween,foundBetween,remarkBetween,
                        checkAfter,foundAfter,remarkAfter));
            }
            String tempDesc = ((TextView) llContainer.getChildAt(i).findViewById(R.id.txtEquipmentDesc)).getText().toString();
            taskDataEnvironmentLists.add(new TaskDataEnvironmentList(i,
                    tempDesc,
                    taskDataEnvironments));
        }
        return taskDataEnvironmentLists;
    }


    public List<TaskDataWorkingList> getTaskDataWorkingList(){
        LinearLayout llContainer = llWorkingSiteCheckingList;
        List<TaskDataWorkingList> taskDataWorkingLists = new ArrayList<>();
        for (int i = 0; i < llContainer.getChildCount() ; i++) {
            LinearLayout linearLayout = llContainer.getChildAt(i).findViewById(R.id.llEquiptmentList);
            List<TaskDataWorking> taskDataWorkings = new ArrayList<>();
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                String eqName = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtCheckListName)).getText().toString();
                boolean checkBefore=false,checkBetween=false,checkAfter=false;
                int foundBefore=0,foundBetween=0,foundAfter=0;
                String remarkBefore="",remarkBetween="",remarkAfter="";
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBefore).isShown()){
                    checkBefore=true;
                    foundBefore= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBefore)).getSelectedItemPosition();
                    remarkBefore = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBefore)).getText().toString();
                }
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBetween).isShown()){
                    checkBetween=true;
                    foundBetween= ((Spinner)linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundBetween)).getSelectedItemPosition();
                    remarkBetween = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.edtReasonBetween)).getText().toString();
                }
                if(linearLayout.getChildAt(j).findViewById(R.id.spnCheckFoundAfter).isShown()){
                    checkAfter=true;
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

    public TaskDataVehicle getVehicle(){
        return new TaskDataVehicle(edtLicensePlate.getText().toString(),
                edtAssetNo.getText().toString(),
                AppUitility.convertStringToLong(edtStartMileage.getText().toString()),
                AppUitility.convertStringToLong(edtEndMileage.getText().toString()));
    }
}
