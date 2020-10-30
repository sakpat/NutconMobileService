package com.soldev.fieldservice;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.fieldservice.dataentity.Employee;
import com.soldev.fieldservice.uiadapter.EmployeeHolderAdapter;
import com.soldev.fieldservice.utilities.ActivityBarCodeScanner;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.RecyclerItemClickListener;
import com.soldev.prod.mobileservice.R;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFormEmployeeTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFormEmployeeTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private View fragmentView;
    private TextView txtWorker;
    private ImageButton imgNew;
    private TaskFormActivity taskFormActivity;
    private RecyclerView rvEmpList;
    private EmployeeHolderAdapter employeeHolderAdapter;

    public TaskFormEmployeeTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TaskFormEmployeeTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFormEmployeeTabFragment newInstance(String param1) {
        TaskFormEmployeeTabFragment fragment = new TaskFormEmployeeTabFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_task_form_employee_tab, container, false);
        setBinding();
        setEvent();
        showData();
        return fragmentView;
    }

    private void setBinding(){
        txtWorker = fragmentView.findViewById(R.id.txtWorker);
        imgNew = fragmentView.findViewById(R.id.imgNew);
        taskFormActivity = (TaskFormActivity) getActivity();
        rvEmpList = fragmentView.findViewById(R.id.rvEmpList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvEmpList.setLayoutManager(layoutManager);
        if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
            imgNew.setVisibility(View.INVISIBLE);
            imgNew.setEnabled(false);
            txtWorker.setEnabled(false);
            txtWorker.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
        }
    }

    private void setEvent(){
        txtWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(taskFormActivity, ActivityBarCodeScanner.class);
                startActivityForResult(intent, AppConstant.REQUEST_CODE_QR_EMP_SCANNER);
            }
        });
        imgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeDialog(-99);
            }
        });
        rvEmpList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvEmpList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                employeeDialog(position);
            }

            @Override
            public void onLongItemClick(View view, final int position) {
                if(!taskFormActivity.displayMode&&!taskFormActivity.deliveryModeOnly) {
                    final int[] positionToDelete = new int[1];
                    positionToDelete[0] = position;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(taskFormActivity);
                    dialog.setTitle("ยืนยัน")
                            .setMessage("กรุณายืนยันการลบชื่อพนักงาน")
                            .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton(getString(R.string.label_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            employeeHolderAdapter.removeItem(positionToDelete[0]);
                            dialog.dismiss();
                        }
                    }).show();
                }

            }
        }));
    }

    private void addEmployeeData(Employee employee){
//        if(taskFormActivity.employees==null){
//            taskFormActivity.employees = new ArrayList<>();
//        }
//        taskFormActivity.employees.add(employee);
        taskFormActivity.taskDataEntry.getEmployees().add(employee);
    }

    private void editEmployee(int rowID, Employee employee){
        taskFormActivity.taskDataEntry.getEmployees().set(rowID,employee);
//        taskFormActivity.employees.set(rowID,employee);
    }

    private void showData(){
//        if(taskFormActivity.taskDataEntry.getEmployees()==null) {
//            taskFormActivity.employees = new ArrayList<>();
//        } else {
//            taskFormActivity.employees = taskFormActivity.taskDataEntry.getEmployees();
//        }
        if(taskFormActivity.taskDataEntry.getEmployees()==null) {
            taskFormActivity.taskDataEntry.instantiateEmployees();
        }
//        employeeHolderAdapter = new EmployeeHolderAdapter(getActivity(),taskFormActivity.employees);
        employeeHolderAdapter = new EmployeeHolderAdapter(getActivity(),taskFormActivity.taskDataEntry.getEmployees());
        rvEmpList.setAdapter(employeeHolderAdapter);
    }

    public void employeeDialog(final int rowID){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = View.inflate(getActivity(), R.layout.dialog_employee_attendance,null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();

        String dateCurrent = "";
        if(rowID==-99){
            dateCurrent = AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime());
//            ((EditText)dialogView.findViewById(R.id.edtStartTime) ).setText(dateCurrent);
//            ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText(dateCurrent);
        } else {
            Employee employee = taskFormActivity.taskDataEntry.getEmployees().get(rowID);
//            Employee employee = taskFormActivity.employees.get(rowID);
            ((EditText)dialogView.findViewById(R.id.edtEmpId) ).setText(employee.getEmpID());
            ((EditText)dialogView.findViewById(R.id.edtEmpName) ).setText(employee.getEmpName());
            ((EditText)dialogView.findViewById(R.id.edtStartTime)).setText(employee.getTimeStart());
            ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText(employee.getTimeEnd());
            ((EditText)dialogView.findViewById(R.id.edtRemark) ).setText(employee.getRemark());
            if(employee.getAbsent()==null||employee.getAbsent().equalsIgnoreCase("N")){
                ((CheckBox) dialogView.findViewById(R.id.chkAbsent)).setChecked(false);
            } else {
                ((CheckBox) dialogView.findViewById(R.id.chkAbsent)).setChecked(true);
            }

            ((CheckBox) dialogView.findViewById(R.id.chkAbsent)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
                        dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
                        ((EditText)dialogView.findViewById(R.id.edtStartTime)).setText("");
                        ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText("");
                    } else {
                        {
                            if(!AppPreference.isLockWorkDate(taskFormActivity)) {
                                dialogView.findViewById(R.id.edtStartTime).setEnabled(true);
                            } else {
                                dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
                            }
                            if(!AppPreference.isLockWorkEndDate(taskFormActivity)) {
                                dialogView.findViewById(R.id.edtEndTime).setEnabled(true);
                            } else {
                                dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
                            }
                        }
                    }
                }
            });

            if(taskFormActivity.deliveryModeOnly||taskFormActivity.displayMode){
                dialogView.findViewById(R.id.edtEmpId).setEnabled(false);
                dialogView.findViewById(R.id.edtEmpName).setEnabled(false);
                dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
                dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
                dialogView.findViewById(R.id.edtRemark).setEnabled(false);
                dialogView.findViewById(R.id.chkAbsent).setEnabled(false);
                dialogView.findViewById(R.id.rdGoodDress).setEnabled(false);
                dialogView.findViewById(R.id.rdNotAppropriateDress).setEnabled(false);


                dialogView.findViewById(R.id.rdGoodBehavior).setEnabled(false);
                dialogView.findViewById(R.id.rdFineBehavior).setEnabled(false);
                dialogView.findViewById(R.id.rdNotAppropriateBehavior).setEnabled(false);

                dialogView.findViewById(R.id.btnOK).setVisibility(View.GONE);
            } else{
                if(!AppPreference.isLockWorkDate(taskFormActivity)) {
                    dialogView.findViewById(R.id.edtStartTime).setEnabled(true);
                } else {
                    dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
                }
                if(!AppPreference.isLockWorkEndDate(taskFormActivity)) {
                    dialogView.findViewById(R.id.edtEndTime).setEnabled(true);
                } else {
                    dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
                }
            }
            if(employee.getWellDress().equalsIgnoreCase("G")){
                ((RadioButton)dialogView.findViewById(R.id.rdGoodDress) ).setChecked(true);
                ((RadioButton)dialogView.findViewById(R.id.rdNotAppropriateDress) ).setChecked(false);
            } else {
                ((RadioButton) dialogView.findViewById(R.id.rdGoodDress)).setChecked(false);
                ((RadioButton) dialogView.findViewById(R.id.rdNotAppropriateDress)).setChecked(true);
            }
            if(employee.getWellBehavior().equalsIgnoreCase("G")){
                ((RadioButton)dialogView.findViewById(R.id.rdGoodBehavior) ).setChecked(true);
                ((RadioButton)dialogView.findViewById(R.id.rdFineBehavior) ).setChecked(false);
                ((RadioButton)dialogView.findViewById(R.id.rdNotAppropriateBehavior) ).setChecked(false);
            } else if(employee.getWellBehavior().equalsIgnoreCase("F")){
                ((RadioButton)dialogView.findViewById(R.id.rdGoodBehavior) ).setChecked(false);
                ((RadioButton)dialogView.findViewById(R.id.rdFineBehavior) ).setChecked(true);
                ((RadioButton)dialogView.findViewById(R.id.rdNotAppropriateBehavior) ).setChecked(false);
            } else {
                ((RadioButton)dialogView.findViewById(R.id.rdGoodBehavior) ).setChecked(false);
                ((RadioButton)dialogView.findViewById(R.id.rdFineBehavior) ).setChecked(false);
                ((RadioButton)dialogView.findViewById(R.id.rdNotAppropriateBehavior) ).setChecked(true);
            }
        }
        if( ((EditText)dialogView.findViewById(R.id.edtStartTime)).getText().toString()==null
                ||((EditText)dialogView.findViewById(R.id.edtStartTime)).getText().toString().trim().equalsIgnoreCase(""))
        {
            ((EditText)dialogView.findViewById(R.id.edtStartTime))
                    .setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime()));
        } else if( ((EditText)dialogView.findViewById(R.id.edtEndTime)).getText().toString()==null
                ||((EditText)dialogView.findViewById(R.id.edtEndTime)).getText().toString().trim().equalsIgnoreCase(""))
        {
            ((EditText)dialogView.findViewById(R.id.edtEndTime))
                    .setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,Calendar.getInstance().getTime()));
        }
        if(((CheckBox) dialogView.findViewById(R.id.chkAbsent)).isChecked()){
            dialogView.findViewById(R.id.edtStartTime).setEnabled(false);
            dialogView.findViewById(R.id.edtEndTime).setEnabled(false);
            ((EditText)dialogView.findViewById(R.id.edtStartTime)).setText("");
            ((EditText)dialogView.findViewById(R.id.edtEndTime) ).setText("");
        }
        dialogView.findViewById(R.id.edtStartTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AppPreference.isLockWorkDate(taskFormActivity)) {
                    String txtDate = ((EditText) view).getText().toString();
                    Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyyhhmmss, txtDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(date.getTime());
                    showDateTimePicker(cal, (EditText) view);
                }
            }
        });
        dialogView.findViewById(R.id.edtEndTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!AppPreference.isLockWorkEndDate(taskFormActivity)) {
                    String txtDate = ((EditText) view).getText().toString();
                    Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyyhhmmss, txtDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(date.getTime());
                    showDateTimePicker(cal, (EditText) view);
                }
            }
        });

//        dialogView.findViewById(R.id.txtStartTime).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent intent = new Intent(taskFormActivity, ActivityBarCodeScanner.class);
//               startActivityForResult(intent,AppConstant.REQUEST_CODE_QR_START_TIME);
//            }
//        });
//
//        dialogView.findViewById(R.id.txtEndTime).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//                startActivityForResult(intent, AppConstant.REQUEST_CODE_QR_END_TIME);
//            }
//        });
        alertDialog.show();
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mId = ((EditText) dialogView.findViewById(R.id.edtEmpId)).getText().toString();
                String mName = ((EditText) dialogView.findViewById(R.id.edtEmpName)).getText().toString();
                String mStartTime = ((EditText) dialogView.findViewById(R.id.edtStartTime)).getText().toString();
                String mEndTime = ((EditText) dialogView.findViewById(R.id.edtEndTime)).getText().toString();
                String mRemark = ((EditText) dialogView.findViewById(R.id.edtRemark)).getText().toString();
                String mAbsent = "";
                if( ((CheckBox) dialogView.findViewById(R.id.chkAbsent)).isChecked()){
                    mAbsent = "Y";
                } else {
                    mAbsent = "N";
                }
                String mDress;
                if(((RadioButton) dialogView.findViewById(R.id.rdGoodDress)).isChecked()){
                    mDress = "G";
                } else {
                    mDress = "N";
                }
                String mBehavior;
                if(((RadioButton) dialogView.findViewById(R.id.rdGoodBehavior)).isChecked()){
                    mBehavior = "G";
                } else if(((RadioButton) dialogView.findViewById(R.id.rdFineBehavior)).isChecked()){
                    mBehavior = "F";
                } else {
                    mBehavior = "N";
                }
                int recordNo=0;
                if(rowID==-99) {
//                    if(taskFormActivity.employees==null){
//                        recordNo = 0;
//                    } else {
//                        recordNo = taskFormActivity.employees.size();
//                    }
                    if(taskFormActivity.taskDataEntry.getEmployees()==null){
                        recordNo = 0;

                    } else {
                        recordNo = taskFormActivity.taskDataEntry.getEmployees().size();
                    }

                    Employee employee = new Employee(recordNo,
                            mId,
                            mName,
                            mStartTime,
                            mEndTime,
                            mDress,
                            mBehavior,
                            mRemark,
                            "M");
                    employee.setAbsent(mAbsent);
                    taskFormActivity.taskDataEntry.addEmployee(employee);
//                    addEmployeeData(employee);
                    Log.e("empname",employee.getEmpName());
                } else {
                    Employee employee = new Employee(rowID,
                            mId,
                            mName,
                            mStartTime,
                            mEndTime,
                            mDress,
                            mBehavior,
                            mRemark,
                            "M");
                    employee.setAbsent(mAbsent);
                    editEmployee(rowID,employee);
                }

                employeeHolderAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== AppConstant.REQUEST_CODE_QR_EMP_SCANNER) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("showqr", data.getExtras().getString("BARCODE"));
                String[] dataString = data.getExtras().getString("BARCODE").split(" ");
                if(dataString.length>0){
                    Log.e("showqr", "split:"+dataString[0]);
//                    for (int i = 0; i < taskFormActivity.employees.size(); i++) {
//                        Employee employee = taskFormActivity.employees.get(i);
//                        if(employee.getEmpID().trim().equalsIgnoreCase(dataString[0].trim())){
//                            Log.e("QRResule","Got you");
//                            employeeDialog(i);
//                            break;
//                        } else {
//                            Log.e("QRResule","What is that!!!!");
//                            Toast.makeText(taskFormActivity,"ไม่พบหมายเลขพนักงาน หมายเลข dataString[0].trim()",Toast.LENGTH_LONG).show();
//                        }
//                    }
                    for (int i = 0; i < taskFormActivity.taskDataEntry.getEmployees().size(); i++) {
                        Employee employee = taskFormActivity.taskDataEntry.getEmployees().get(i);
                        if(employee.getEmpID().trim().equalsIgnoreCase(dataString[0].trim())){
                            Log.e("QRResule","Got you");
                            employeeDialog(i);
                            break;
                        } else {
                            Log.e("QRResule","What is that!!!!");
                            Toast.makeText(taskFormActivity,"ไม่พบหมายเลขพนักงาน หมายเลข dataString[0].trim()",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }
}
