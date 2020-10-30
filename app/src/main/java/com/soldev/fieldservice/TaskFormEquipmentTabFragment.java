package com.soldev.fieldservice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskDataEquipment;
import com.soldev.fieldservice.dataentity.TaskDataEquipmentList;
import com.soldev.fieldservice.dataentity.TaskDataMachine;
import com.soldev.fieldservice.uiadapter.MachineHolderAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.RecyclerItemClickListener;
import com.soldev.prod.mobileservice.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormEquipmentTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormEquipmentTabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private DatabaseReference databaseReference;
    private String taskID;
    private LinearLayout llEquipmentList;
    private RecyclerView rvMachineList;
    private View fragmentView;
    private ValueEventListener valueEventListener;
    private ImageButton imgNew;
    private MachineHolderAdapter machineHolderAdapter;
    private TaskFormActivity taskFormActivity;

    public TaskFormEquipmentTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskID Parameter 1.
     * @return A new instance of fragment TaskFormEquipmentTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormEquipmentTabFragment newInstance(String taskID) {
        TaskFormEquipmentTabFragment fragment = new TaskFormEquipmentTabFragment();
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
        this.taskFormActivity = (TaskFormActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_task_form_equipment_tab, container, false);
        setBinding();
        setEvent();
        showTask(taskID);
        return fragmentView;
    }

    private void setBinding(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        llEquipmentList = fragmentView.findViewById(R.id.llEquiptmentList);
        imgNew = fragmentView.findViewById(R.id.imgNew);
        taskFormActivity.taskDataMachineList = new ArrayList<>();
        rvMachineList = fragmentView.findViewById(R.id.rvMachineList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvMachineList.setLayoutManager(layoutManager);
        rvMachineList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvMachineList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                machineDialog(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                machineHolderAdapter.removeItem(position);
            }
        }));

        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            imgNew.setVisibility(View.GONE);
        }

    }

    private void setEvent(){
        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machineDialog(-99);
            }
        });
    }


    public void machineDialog(final int rowID){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = View.inflate(getActivity(), R.layout.dialog_machine_edit,null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            dialogView.findViewById(R.id.btnOK).setVisibility(View.GONE);
        }
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskFormActivity.taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING)||
                        taskFormActivity.taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH)) {
                    if (((EditText) dialogView.findViewById(R.id.edtMachinNumber)).getText().toString().trim().length() == 7) {
                        updateMachineData(rowID, dialogView);
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(taskFormActivity, "รหัสเครื่องจักรจะต้องมี 7 หลักเท่านั้น", Toast.LENGTH_LONG).show();
                    }
                } else {
                    updateMachineData(rowID, dialogView);
                    alertDialog.dismiss();
                }
            }
        });
        if(rowID>-99){
            ((EditText)dialogView.findViewById(R.id.edtMachinNumber)).setText(taskFormActivity.taskDataMachineList.get(rowID).getCode());
            ((EditText)dialogView.findViewById(R.id.edtMachinRemark)).setText(taskFormActivity.taskDataMachineList.get(rowID).getRemark());
        }
    }

    public void updateMachineData(int rowID,View dialogView){
        if (rowID == -99) {
            addNewMachine(((EditText) dialogView.findViewById(R.id.edtMachinNumber)).getText().toString(),
                    ((EditText) dialogView.findViewById(R.id.edtMachinRemark)).getText().toString());
        } else {
            editMachine(rowID,
                    ((EditText) dialogView.findViewById(R.id.edtMachinNumber)).getText().toString(),
                    ((EditText) dialogView.findViewById(R.id.edtMachinRemark)).getText().toString());
        }
    }

    public void showTask(String taskID){
        List<TaskDataEquipmentList> taskDataEquipmentLists = taskFormActivity.taskDataEntry.getEquipmentList();
        if(taskDataEquipmentLists!=null){
            llEquipmentList.removeAllViews();
            for (int h = 0; h < taskDataEquipmentLists.size(); h++) {
                TaskDataEquipmentList taskDataEquipmentList = taskDataEquipmentLists.get(h);
                View head = fragmentView.inflate(taskFormActivity, R.layout.row_task_equipment_header, null);
                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataEquipmentList.getDescription());
                llEquipmentList.addView(head);
                final LinearLayout llEquiptmentListDetail = head.findViewById(R.id.llEquiptmentList);
                for (int i = 0; i < taskDataEquipmentList.getTaskDataEquipments().size(); i++) {
                    final TaskDataEquipment taskDataEquipment = taskDataEquipmentList.getTaskDataEquipments().get(i);
                    View view = fragmentView.inflate(taskFormActivity, R.layout.row_task_equipment, null);
                    view.setTag(taskDataEquipment.getDescription());
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            equipmentDialog(view, llEquiptmentListDetail);

                        }
                    });
                    view.findViewById(R.id.llRowEquipment).setId(taskDataEquipment.getRowNo());
                    AppUitility.setText(view, R.id.txtEquipmentRow, Integer.toString(taskDataEquipment.getRowNo() + 1));
                    AppUitility.setText(view, R.id.txtEquipmentDesc, taskDataEquipment.getDescription());
                    AppUitility.setText(view, R.id.txtEquipmentUnits, Integer.toString(taskDataEquipment.getAmount()));
                    String beforeStr = "";
                    if (taskDataEquipment.getCheckBeforeWork() == -1) {
                        beforeStr = "-";
                    } else {
                        beforeStr = Integer.toString(taskDataEquipment.getCheckBeforeWork());
                    }
                    AppUitility.setText(view, R.id.txtEquipmentBefore, beforeStr);
                    String afterStr = "-";
                    if (taskDataEquipment.getCheckAfterWork() == -1) {
                        afterStr = "-";
                    } else {
                        afterStr = Integer.toString(taskDataEquipment.getCheckAfterWork());
                    }
                    AppUitility.setText(view, R.id.txtEquipmentAfter, afterStr);
//                                if(taskFormActivity.deliveryModeOnly){
//                                    view.findViewById(R.id.txtEquipmentBefore).setEnabled(false);
//                                    view.findViewById(R.id.txtEquipmentAfter).setEnabled(false);
//                                }
                    llEquiptmentListDetail.addView(view);
                }
            }
        }
//        else {
//            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    TaskDataEntry taskDataEntry = dataSnapshot.getValue(TaskDataEntry.class);
//                    llEquipmentList.removeAllViews();
//                    if (taskDataEntry != null) {
//                        if (taskDataEntry.getEquipmentList() != null) {
//                            for (int h = 0; h < taskDataEntry.getEquipmentList().size(); h++) {
//                                TaskDataEquipmentList taskDataEquipmentList = taskDataEntry.getEquipmentList().get(h);
//                                View head = fragmentView.inflate(taskFormActivity, R.layout.row_task_equipment_header, null);
//                                AppUitility.setText(head, R.id.txtEquipmentDesc, taskDataEquipmentList.getDescription());
//                                llEquipmentList.addView(head);
//                                final LinearLayout llEquiptmentListDetail = head.findViewById(R.id.llEquiptmentList);
//                                for (int i = 0; i < taskDataEquipmentList.getTaskDataEquipments().size(); i++) {
//                                    final TaskDataEquipment taskDataEquipment = taskDataEquipmentList.getTaskDataEquipments().get(i);
//                                    View view = fragmentView.inflate(taskFormActivity, R.layout.row_task_equipment, null);
//                                    view.setTag(taskDataEquipment.getDescription());
//                                    view.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            equipmentDialog(view, llEquiptmentListDetail);
//
//                                        }
//                                    });
//                                    view.findViewById(R.id.llRowEquipment).setId(taskDataEquipment.getRowNo());
//                                    AppUitility.setText(view, R.id.txtEquipmentRow, Integer.toString(taskDataEquipment.getRowNo() + 1));
//                                    AppUitility.setText(view, R.id.txtEquipmentDesc, taskDataEquipment.getDescription());
//                                    AppUitility.setText(view, R.id.txtEquipmentUnits, Integer.toString(taskDataEquipment.getAmount()));
//                                    String beforeStr = "";
//                                    if (taskDataEquipment.getCheckBeforeWork() == -1) {
//                                        beforeStr = "-";
//                                    } else {
//                                        beforeStr = Integer.toString(taskDataEquipment.getCheckBeforeWork());
//                                    }
//                                    AppUitility.setText(view, R.id.txtEquipmentBefore, beforeStr);
//                                    String afterStr = "-";
//                                    if (taskDataEquipment.getCheckAfterWork() == -1) {
//                                        afterStr = "-";
//                                    } else {
//                                        afterStr = Integer.toString(taskDataEquipment.getCheckAfterWork());
//                                    }
//                                    AppUitility.setText(view, R.id.txtEquipmentAfter, afterStr);
////                                if(taskFormActivity.deliveryModeOnly){
////                                    view.findViewById(R.id.txtEquipmentBefore).setEnabled(false);
////                                    view.findViewById(R.id.txtEquipmentAfter).setEnabled(false);
////                                }
//                                    llEquiptmentListDetail.addView(view);
//                                }
//                            }
//                        }
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

//        }
        if (taskFormActivity.taskDataEntry.getMachineList() == null) {
            taskFormActivity.taskDataMachineList = new ArrayList<>();
        } else {
            taskFormActivity.taskDataMachineList = taskFormActivity.taskDataEntry.getMachineList();
        }
        machineHolderAdapter = new MachineHolderAdapter(taskFormActivity.taskDataMachineList);
        rvMachineList.setAdapter(machineHolderAdapter);
    }

//    private void showData(String taskID){
//        valueEventListener = databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskID).child("equipmentList").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                llEquipmentList.removeAllViews();
//                for(DataSnapshot data:dataSnapshot.getChildren()){
//                    TaskDataEquipmentList taskDataEquipmentList = data.getValue(TaskDataEquipmentList.class);
//                    View head = View.inflate(getContext(),R.layout.row_task_equipment_header,null);
//                    AppUitility.setText(head,R.id.txtEquipmentDesc,taskDataEquipmentList.getDescription());
//                    llEquipmentList.addView(head);
//                    LinearLayout llEquiptmentListDetail = head.findViewById(R.id.llEquiptmentList);
//                    Log.e("showdata","data>"+taskDataEquipmentList.getDescription());
//                    for (int i = 0; i < taskDataEquipmentList.getTaskDataEquipments().size(); i++) {
//                        TaskDataEquipment taskDataEquipment =  taskDataEquipmentList.getTaskDataEquipments().get(i);
//                        View view = View.inflate(getContext(),R.layout.row_task_equipment,null);
//                        view.findViewById(R.id.llRowEquipment).setId(taskDataEquipment.getRowNo());
//                        AppUitility.setText(view,R.id.txtEquipmentRow,Integer.toString(taskDataEquipment.getRowNo()));
//                        AppUitility.setText(view,R.id.txtEquipmentDesc,taskDataEquipment.getDescription());
//                        AppUitility.setText(view,R.id.txtEquipmentUnits,Integer.toString(taskDataEquipment.getAmount()));
//                        AppUitility.setText(view,R.id.txtEquipmentBefore,Integer.toString(taskDataEquipment.getCheckBeforeWork()));
//                        AppUitility.setText(view,R.id.txtEquipmentAfter,Integer.toString(taskDataEquipment.getCheckAfterWork()));
//                        llEquiptmentListDetail.addView(view);
//                    }
//
//                }
//                Log.e("showdata","llEquipmentList size>"+llEquipmentList.getChildCount());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        databaseReference.removeEventListener(valueEventListener);
    }

    private void addNewMachine(String machineNumber, String remark){
        int rowNo = 0;
        if(taskFormActivity.taskDataMachineList!=null){
            rowNo = taskFormActivity.taskDataMachineList.size();
        }
        taskFormActivity.taskDataMachineList.add(new TaskDataMachine(rowNo,machineNumber,"",remark));
        machineHolderAdapter.notifyDataSetChanged();
    }

    private void editMachine(int rowID,String machineNumber, String remark){
        taskFormActivity.taskDataMachineList.set(rowID,new TaskDataMachine(rowID,machineNumber,"",remark));
        machineHolderAdapter.notifyDataSetChanged();
    }

    public List<TaskDataEquipmentList> getEquiptmentList(){
        LinearLayout equiptmentListLLL = fragmentView.findViewById(R.id.llEquiptmentList);
        List<TaskDataEquipmentList> taskDataEquipmentLists = new ArrayList<>();
        for (int i = 0; i < equiptmentListLLL.getChildCount() ; i++) {
            LinearLayout linearLayout = equiptmentListLLL.getChildAt(i).findViewById(R.id.llEquiptmentList);
            List<TaskDataEquipment> taskDataEquipments = new ArrayList<>();
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                String eqName = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentDesc)).getText().toString();
                int eqUnit = Integer.parseInt(((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentUnits)).getText().toString());
                String eqBefore = "";
                if( ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentBefore)).getText().toString().equalsIgnoreCase("-")) {
                   eqBefore = "-1";
                } else {
                    eqBefore =  ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentBefore)).getText().toString();
                }

                String eqAfter = "";
                if(((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentAfter)).getText().toString().equalsIgnoreCase("-")) {
                    eqAfter = "-1";
                } else {
                    eqAfter = ((TextView)linearLayout.getChildAt(j).findViewById(R.id.txtEquipmentAfter)).getText().toString();
                }
                taskDataEquipments.add(new TaskDataEquipment(j,
                        eqName,
                        eqUnit,
                        Integer.parseInt(eqBefore),
                        Integer.parseInt(eqAfter)));
            }
            String tempDesc = ((TextView) equiptmentListLLL.getChildAt(i).findViewById(R.id.txtEquipmentDesc)).getText().toString();
            taskDataEquipmentLists.add(new TaskDataEquipmentList(i,
                    tempDesc,
                    taskDataEquipments));
        }
        return taskDataEquipmentLists;
    }

    public boolean isDetailRow(View clickedView){
        try{
            clickedView.findViewById(R.id.txtEquipmentBefore);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void equipmentDialog(final View clickedView, final LinearLayout parent){
        if(isDetailRow(clickedView)) {


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final View dialogView = View.inflate(getActivity(), R.layout.dialog_equipment_edit, null);
            String beforeValue = ((TextView) clickedView.findViewById(R.id.txtEquipmentBefore)).getText().toString();
            if (beforeValue.trim().equalsIgnoreCase("-")) {
                beforeValue = "";
            }
            String afterValue = ((TextView) clickedView.findViewById(R.id.txtEquipmentAfter)).getText().toString();
            if (afterValue.trim().equalsIgnoreCase("-")) {
                afterValue = "";
            }

            ((EditText) dialogView.findViewById(R.id.edtBefore)).setText(beforeValue);
            ((EditText) dialogView.findViewById(R.id.edtAfter)).setText(afterValue);
            ((TextView) dialogView.findViewById(R.id.txtTaskTypeName)).setText(clickedView.getTag().toString());
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String returnBeforeValue = ((EditText) dialogView.findViewById(R.id.edtBefore)).getText().toString();
                    String returnAfterValue = ((EditText) dialogView.findViewById(R.id.edtAfter)).getText().toString();
                    if (returnBeforeValue.trim().equalsIgnoreCase("")) {
                        returnBeforeValue = "-";
                    }
                    if (returnAfterValue.trim().equalsIgnoreCase("")) {
                        returnAfterValue = "-";
                    }
                    ((TextView) clickedView.findViewById(R.id.txtEquipmentBefore)).setText(returnBeforeValue);
                    ((TextView) clickedView.findViewById(R.id.txtEquipmentAfter)).setText(returnAfterValue);
                    alertDialog.dismiss();
                    if(clickedView.getId() + 1<parent.getChildCount()) {
                        equipmentDialog(parent.getChildAt(clickedView.getId() + 1), parent);
                    }

                }
            });
            alertDialog.show();
        }
    }
}
