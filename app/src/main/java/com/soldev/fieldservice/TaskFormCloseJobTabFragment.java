package com.soldev.fieldservice;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormCloseJobTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormCloseJobTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View fragmentView;
    private RadioButton rdHasReveiver,rdHasNoReveiver;
    private LinearLayout llDetail,llSatisfaction;
    private Button btnSignatureDeliver,btnReceiveSignature,btnPrintDeliveryNote,btnLockSurvey;
    private RadioGroup rgBehavior,rgQuality;
    private TaskFormActivity taskFormActivity;
    private ImageView imgDeliverSignature,imgReceiveSignature;
    private CheckBox chbNoWorkPerform;
    public TaskFormCloseJobTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TaskFormCloseJobTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormCloseJobTabFragment newInstance(String param1) {
        TaskFormCloseJobTabFragment fragment = new TaskFormCloseJobTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_task_form_close_job_tab, container, false);
        setBinding();
        setEvent();
        showTaskData();
        return fragmentView;
    }

    private void setBinding(){
        rdHasReveiver = fragmentView.findViewById(R.id.rdHasReveiver);
        rdHasNoReveiver = fragmentView.findViewById(R.id.rdHasNoReveiver);
        llDetail  = fragmentView.findViewById(R.id.llDetail);
        btnSignatureDeliver = fragmentView.findViewById(R.id.btnSignatureDeliver);
        btnReceiveSignature = fragmentView.findViewById(R.id.btnReceiveSignature);
        taskFormActivity = (TaskFormActivity) getActivity();
        imgDeliverSignature = fragmentView.findViewById(R.id.imgDeliverSignature);
        imgReceiveSignature = fragmentView.findViewById(R.id.imgReceiveSignature);
        btnPrintDeliveryNote = fragmentView.findViewById(R.id.btnPrintDeliveryNote);
        btnLockSurvey = fragmentView.findViewById(R.id.btnLockSurvey);
        rgBehavior = fragmentView.findViewById(R.id.rgBehavior);
        rgQuality = fragmentView.findViewById(R.id.rgQuality);
        chbNoWorkPerform = fragmentView.findViewById(R.id.chbNoWorkPerform);
        llSatisfaction = fragmentView.findViewById(R.id.llSatisfaction);
        if(taskFormActivity.displayMode){
            rdHasReveiver.setEnabled(false);
            rdHasNoReveiver.setEnabled(false);
            btnSignatureDeliver.setVisibility(View.GONE);
            btnLockSurvey.setVisibility(View.GONE);
            btnPrintDeliveryNote.setVisibility(View.GONE);
            btnReceiveSignature.setVisibility(View.GONE);
            chbNoWorkPerform.setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorVeryGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorSoSo).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorNeedImprove).setEnabled(false);

            fragmentView.findViewById(R.id.rdQualityVeryGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualitySoSo).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityNeedImprove).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityBad).setEnabled(false);
        }
    }

    private void setEvent(){
        chbNoWorkPerform.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    taskFormActivity.taskDataEntry.setWorkPerformed(false) ;
                    if(rdHasReveiver.isChecked()){
                        llDetail.setVisibility(View.VISIBLE);
//                        llSatisfaction.setVisibility(View.VISIBLE);
                    } else {
                        llDetail.setVisibility(View.GONE);
                    }
                } else {
                    taskFormActivity.taskDataEntry.setWorkPerformed(true) ;
                    if(rdHasReveiver.isChecked()){
                        llDetail.setVisibility(View.VISIBLE);
//                        llSatisfaction.setVisibility(View.VISIBLE);
                    } else {
                        llDetail.setVisibility(View.GONE);
                    }
                }

            }
        });
        rdHasReveiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    llDetail.setVisibility(View.VISIBLE);
//                    if (chbNoWorkPerform.isChecked()) {
//                        llSatisfaction.setVisibility(View.GONE);
//                    } else {
//                        llSatisfaction.setVisibility(View.VISIBLE);
//                    }
                }
            }
        });

        rdHasNoReveiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    llDetail.setVisibility(View.GONE);
                }
//                else {
//                    llDetail.setVisibility(View.VISIBLE);
//                }
            }
        });
        btnSignatureDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskFormActivity.taskDataEntry.isAlreadyPrinted()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(taskFormActivity);
                    dialog.setTitle("ยืนยันแก้ไข")
                            .setMessage("เอกสารนี้ได้ทำการพิมพ์แล้ว กรุณายืนยันการเข้าแก้ไขการลงนาม")
                            .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            bundle.putString(AppConstant.DB_FIELD_TASK_ID, taskFormActivity.getTaskID());
                            bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, AppConstant.REQUEST_CODE_SIGNATURE_DELIVER);
                        }
                    }).show();
                } else {
                    if(taskFormActivity.taskDataEntry.isAlreadyPrinted()){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(taskFormActivity);
                        dialog.setTitle("ยืนยันแก้ไข")
                                .setMessage("เอกสารนี้ได้ทำการพิมพ์แล้ว กรุณายืนยันการเข้าแก้ไขการลงนาม")
                                .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                bundle.putString(AppConstant.DB_FIELD_TASK_ID, taskFormActivity.getTaskID());
                                bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, AppConstant.REQUEST_CODE_SIGNATURE_DELIVER);
                            }
                        }).show();
                    } else {
                        Intent intent = new Intent(getActivity(), TaskFormCanvasActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppConstant.PARAM_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        bundle.putString(AppConstant.DB_FIELD_TASK_ID, taskFormActivity.getTaskID());
                        bundle.putString(AppConstant.DB_FIELD_FILE_NAME, AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, AppConstant.REQUEST_CODE_SIGNATURE_DELIVER);
                    }
                }
            }
        });
        btnReceiveSignature.setOnClickListener(new View.OnClickListener() {
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

        btnPrintDeliveryNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                taskFormActivity.saveData(taskFormActivity.taskDataEntry.getTaskGroupCode(),false,false,false);
                if(taskFormActivity.saveLocalData()) {
                    if (taskFormActivity.taskDataEntry.getTaskGroupCode().equalsIgnoreCase("1")) {
                        if (taskFormActivity.checkPumpQuantity()) {
                            Intent intent = new Intent(taskFormActivity, PrintReportActivity.class);
                            Gson gson = new Gson();
                            String dataToPrint = gson.toJson(taskFormActivity.taskDataEntry);
                            Bundle bundle = new Bundle();
                            bundle.putString("DATA", dataToPrint);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(taskFormActivity, "ยอดรวมคอนกรีตไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Intent intent = new Intent(taskFormActivity, PrintReportActivity.class);
                        Gson gson = new Gson();
                        String dataToPrint = gson.toJson(taskFormActivity.taskDataEntry);
                        Bundle bundle = new Bundle();
                        bundle.putString("DATA", dataToPrint);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    taskFormActivity.taskDataEntry.setAlreadyPrinted(true);
                    taskFormActivity.saveData(taskFormActivity.taskDataEntry.getTaskGroupCode(),false,false,false);
//                btnReceiveSignature.setVisibility(View.GONE);
//                btnSignatureDeliver.setVisibility(View.GONE);
                }
            }
        });
        btnLockSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmLock();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstant.REQUEST_CODE_SIGNATURE_DELIVER: {
                    taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER,imgDeliverSignature);
                    break;
                }
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


    private void showTaskData(){
//        taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER,imgDeliverSignature);
        if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER,imgDeliverSignature)) {
            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_DELIVER,
                    AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER + ".jpg",
                    imgDeliverSignature);
        };
//        taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE,imgReceiveSignature);
        if (!taskFormActivity.loadImageToView(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE,imgReceiveSignature)) {
            taskFormActivity.getImageFromServer(taskFormActivity.taskDataEntry.getTaskNo(),
                    AppConstant.IMAGE_CAT_DELIVER,
                    AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE + ".jpg",
                    imgReceiveSignature);
        };
        if(taskFormActivity.taskDataEntry.isWorkPerformed()){
            chbNoWorkPerform.setChecked(false);
        } else {
            chbNoWorkPerform.setChecked(true);
        }
        if(taskFormActivity.taskDataEntry.isHasReceiver()){
            ((RadioButton)fragmentView.findViewById(R.id.rdHasReveiver)).setChecked(true);
        } else {
            ((RadioButton)fragmentView.findViewById(R.id.rdHasNoReveiver)).setChecked(true);
        }
        ((TextView) fragmentView.findViewById(R.id.edtCheckBy)).setText(taskFormActivity.taskDataEntry.getTaskReceiver());
        ((TextView) fragmentView.findViewById(R.id.edtContactNo)).setText(taskFormActivity.taskDataEntry.getTaskReceiverContact());
        ((TextView) fragmentView.findViewById(R.id.edtTaskRemark)).setText(taskFormActivity.taskDataEntry.getTaskRemark());
        fragmentView.findViewById(R.id.edtCheckBy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_customer_receiver),false);
            }
        });
        fragmentView.findViewById(R.id.edtContactNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_contact),false);
            }
        });
        fragmentView.findViewById(R.id.edtTaskRemark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFormActivity.dialogRemark(view,getString(R.string.label_remark),false);
            }
        });
        if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamBehavior(),"").equalsIgnoreCase("V")){
            ((RadioButton) fragmentView.findViewById(R.id.rdBehaviorVeryGood)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamBehavior(),"").equalsIgnoreCase("G")){
            ((RadioButton) fragmentView.findViewById(R.id.rdBehaviorGood)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamBehavior(),"").equalsIgnoreCase("S")){
            ((RadioButton) fragmentView.findViewById(R.id.rdBehaviorSoSo)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamBehavior(),"").equalsIgnoreCase("B")){
            ((RadioButton) fragmentView.findViewById(R.id.rdBehaviorNeedImprove)).setChecked(true);
        } else {
            ((RadioButton) fragmentView.findViewById(R.id.rdBehaviorVeryGood)).setChecked(true);
        }

        if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamPerformQaulity(),"").equalsIgnoreCase("V")){
            ((RadioButton) fragmentView.findViewById(R.id.rdQualityVeryGood)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamPerformQaulity(),"").equalsIgnoreCase("G")){
            ((RadioButton) fragmentView.findViewById(R.id.rdQualityGood)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamPerformQaulity(),"").equalsIgnoreCase("S")){
            ((RadioButton) fragmentView.findViewById(R.id.rdQualitySoSo)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamPerformQaulity(),"").equalsIgnoreCase("N")){
            ((RadioButton) fragmentView.findViewById(R.id.rdQualityNeedImprove)).setChecked(true);
        } else if(AppUitility.isStringNull(taskFormActivity.taskDataEntry.getTeamPerformQaulity(),"").equalsIgnoreCase("B")){
            ((RadioButton) fragmentView.findViewById(R.id.rdQualityBad)).setChecked(true);
        } else {
            ((RadioButton) fragmentView.findViewById(R.id.rdQualityVeryGood)).setChecked(true);
        }
        if(taskFormActivity.taskDataEntry.isLockSurvey()){
            fragmentView.findViewById(R.id.rdBehaviorVeryGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorSoSo).setEnabled(false);
            fragmentView.findViewById(R.id.rdBehaviorNeedImprove).setEnabled(false);

            fragmentView.findViewById(R.id.rdQualityVeryGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityGood).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualitySoSo).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityNeedImprove).setEnabled(false);
            fragmentView.findViewById(R.id.rdQualityBad).setEnabled(false);
        }
//        if(taskFormActivity.taskDataEntry.isAlreadyPrinted()){
//            btnReceiveSignature.setVisibility(View.GONE);
//            btnSignatureDeliver.setVisibility(View.GONE);
//        } else {
//            btnReceiveSignature.setVisibility(View.VISIBLE);
//            btnSignatureDeliver.setVisibility(View.VISIBLE);
//        }
    }

    public HashMap<String,String> getData(){
        HashMap<String,String> hashMap = new HashMap<>();
        String hasReceiver = "N";
        String receiverName = "";
        String receiverContact = "";
        String teamBehavior = "";
        String teamPerformQuality = "";
        String deliverRemark = "";
        if(((RadioButton)fragmentView.findViewById(R.id.rdHasReveiver)).isChecked()){
            hasReceiver = "Y";
            receiverName = ((TextView) fragmentView.findViewById(R.id.edtCheckBy)).getText().toString();
            receiverContact = ((TextView) fragmentView.findViewById(R.id.edtContactNo)).getText().toString();
            if(((RadioButton) fragmentView.findViewById(R.id.rdBehaviorVeryGood)).isChecked()){
                teamBehavior = "V";
            } else  if(((RadioButton) fragmentView.findViewById(R.id.rdBehaviorGood)).isChecked()){
                teamBehavior = "G";
            } else  if(((RadioButton) fragmentView.findViewById(R.id.rdBehaviorSoSo)).isChecked()){
                teamBehavior = "S";
            } else {
                teamBehavior = "B";
            }

            if(((RadioButton) fragmentView.findViewById(R.id.rdQualityVeryGood)).isChecked()){
                teamPerformQuality = "V";
            } else  if(((RadioButton) fragmentView.findViewById(R.id.rdQualityGood)).isChecked()){
                teamPerformQuality = "G";
            } else  if(((RadioButton) fragmentView.findViewById(R.id.rdQualitySoSo)).isChecked()){
                teamPerformQuality = "S";
            } else  if(((RadioButton) fragmentView.findViewById(R.id.rdQualityNeedImprove)).isChecked()){
                teamPerformQuality = "N";
            } else {
                teamPerformQuality = "B";
            }
        }
        deliverRemark = ((TextView) fragmentView.findViewById(R.id.edtTaskRemark)).getText().toString();
        Log.e("showtaskramark",deliverRemark);

        hashMap.put("hasReceiver",hasReceiver);
        hashMap.put("receiverName",receiverName);
        hashMap.put("receiverContact",receiverContact);
        hashMap.put("teamBehavior",teamBehavior);
        hashMap.put("teamPerformQuality",teamPerformQuality);
        hashMap.put("edtTaskRemark",deliverRemark);
        return hashMap;
    }

    private void dialogConfirmLock(){
        AlertDialog.Builder builder = new AlertDialog.Builder(taskFormActivity);
        builder.setCancelable(false)
                .setTitle("ยืนยันการให้คะแนน")
                .setMessage("เมื่อท่านยืนยันแล้ว ระบบจะไม่อนุญาตให้ทำการแก้ไขคะแนนอีกต่อไป")
                .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rgBehavior.setEnabled(false);
                        rgQuality.setEnabled(false);
                        fragmentView.findViewById(R.id.rdBehaviorVeryGood).setEnabled(false);
                        fragmentView.findViewById(R.id.rdBehaviorGood).setEnabled(false);
                        fragmentView.findViewById(R.id.rdBehaviorSoSo).setEnabled(false);
                        fragmentView.findViewById(R.id.rdBehaviorNeedImprove).setEnabled(false);

                        fragmentView.findViewById(R.id.rdQualityVeryGood).setEnabled(false);
                        fragmentView.findViewById(R.id.rdQualityGood).setEnabled(false);
                        fragmentView.findViewById(R.id.rdQualitySoSo).setEnabled(false);
                        fragmentView.findViewById(R.id.rdQualityNeedImprove).setEnabled(false);
                        fragmentView.findViewById(R.id.rdQualityBad).setEnabled(false);
                        taskFormActivity.taskDataEntry.setLockSurvey(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskFormActivity.taskDataEntry.setLockSurvey(false);
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
