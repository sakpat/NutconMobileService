package com.soldev.fieldservice;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soldev.fieldservice.dataentity.TaskDataExtraFee;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormExtraFeeTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormExtraFeeTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String taskID;
    private View fragmentView;
    private TaskFormActivity taskFormActivity;
    private LinearLayout llExtraFeeList;

    public TaskFormExtraFeeTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TaskFormExtraFeeTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormExtraFeeTabFragment newInstance(String param1) {
        TaskFormExtraFeeTabFragment fragment = new TaskFormExtraFeeTabFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_task_form_extra_fee_tab, container, false);
        setBinging();
        showTask();
        return  fragmentView;
    }

    private void setBinging(){
        taskFormActivity = (TaskFormActivity) getActivity();
        llExtraFeeList = fragmentView.findViewById(R.id.llExtraFeeList);
    }

    public void showTask(){
        List<TaskDataExtraFee> taskDataExtraFees;
        if(taskFormActivity.taskDataEntry.getExtraFeeList()==null){
            taskDataExtraFees = new ArrayList<>();
        } else {
            taskDataExtraFees = taskFormActivity.taskDataEntry.getExtraFeeList();
        }
        llExtraFeeList.removeAllViews();
        for (int i = 0; i < taskDataExtraFees.size() ; i++) {
            View extraFeeView = fragmentView.inflate(taskFormActivity, R.layout.row_task_extra_fee, null);
            extraFeeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!taskFormActivity.displayMode&&!taskFormActivity.deliveryModeOnly) {
                        extraFeeDialog(view);
                    }
                }
            });
            ((TextView)extraFeeView.findViewById(R.id.txtExtraFeeName)).setText(taskDataExtraFees.get(i).getDesctipion());
            ((TextView)extraFeeView.findViewById(R.id.txtAmount)).setText(AppUitility.convertDoubleToString(taskDataExtraFees.get(i).getAmount(),"##"));
            ((TextView)extraFeeView.findViewById(R.id.txtRemark)).setText(taskDataExtraFees.get(i).getRemark());
            llExtraFeeList.addView(extraFeeView);
        }

    }

    public List<TaskDataExtraFee> getExtraFeeList(){
        List<TaskDataExtraFee> extraFees = new ArrayList<>();
        for (int i = 0; i < llExtraFeeList.getChildCount() ; i++) {
            LinearLayout linearLayout = (LinearLayout) llExtraFeeList.getChildAt(i);
            Log.e("showdata",((TextView)linearLayout.findViewById(R.id.txtExtraFeeName)).getText().toString()+">"+((TextView)linearLayout.findViewById(R.id.txtAmount)).getText().toString()+"<");
//            TaskDataExtraFee taskDataExtraFee = new TaskDataExtraFee(i,
//                    AppUitility.isStringNull(((TextView)linearLayout.findViewById(R.id.txtExtraFeeName)).getText().toString(),""),
//                    AppUitility.convertStringToDouble(AppUitility.isStringNull(((TextView)linearLayout.findViewById(R.id.txtAmount)).getText().toString(),"0")),
//                    "I",
//                    AppUitility.isStringNull(((TextView)linearLayout.findViewById(R.id.txtRemark)).getText().toString(),""));
            TaskDataExtraFee taskDataExtraFee = new TaskDataExtraFee(i,
                    AppUitility.isStringNull(((TextView)linearLayout.findViewById(R.id.txtExtraFeeName)).getText().toString(),""),
                    AppUitility.convertStringToDouble(((TextView)linearLayout.findViewById(R.id.txtAmount)).getText().toString()),
                    "I",
                    AppUitility.isStringNull(((TextView)linearLayout.findViewById(R.id.txtRemark)).getText().toString(),""));
            extraFees.add(taskDataExtraFee);
        }
        for (int i = 0; i < extraFees.size(); i++) {
            Log.e("extrafee",extraFees.get(i).getDesctipion()+":"+extraFees.get(i).getAmount());
        }
        return extraFees;
    }
    private void extraFeeDialog(final View clickedView){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = View.inflate(getActivity(),R.layout.dialog_extra_fee,null);
        ((TextView) dialogView.findViewById(R.id.txtDisplay)).setText(((TextView)clickedView.findViewById(R.id.txtExtraFeeName)).getText().toString());
        String feeAmout = ((TextView)clickedView.findViewById(R.id.txtAmount)).getText().toString();
        if(feeAmout.equalsIgnoreCase("0")){
            feeAmout="";
        }
        ((EditText) dialogView.findViewById(R.id.edtBefore)).setText(feeAmout);
        ((EditText) dialogView.findViewById(R.id.edtAfter)).setText(((TextView)clickedView.findViewById(R.id.txtRemark)).getText().toString());
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
                String returnAmount = ((EditText) dialogView.findViewById(R.id.edtBefore)).getText().toString();
                if(returnAmount.trim().equalsIgnoreCase("")){
                    returnAmount = "0";
                }
                ((TextView)clickedView.findViewById(R.id.txtAmount)).setText(returnAmount);
                ((TextView)clickedView.findViewById(R.id.txtRemark)).setText(((EditText) dialogView.findViewById(R.id.edtAfter)).getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
