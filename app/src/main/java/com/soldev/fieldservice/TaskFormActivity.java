package com.soldev.fieldservice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.soldev.fieldservice.dataentity.DocumentClass;
import com.soldev.fieldservice.dataentity.Employee;
import com.soldev.fieldservice.dataentity.TaskDataExtraFee;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.SQLiteHelperUtility;
import com.soldev.fieldservice.utilities.WebService;
import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.dataentity.EnvironmentCheckListForJob;
import com.soldev.fieldservice.dataentity.EnvironmentCheckListForJobListByTaskType;
import com.soldev.fieldservice.dataentity.EquipmentForJob;
import com.soldev.fieldservice.dataentity.EquipmentForJobListByTaskType;
import com.soldev.fieldservice.dataentity.ImageInfo;
import com.soldev.fieldservice.dataentity.TaskDataConcreteTruck;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskDataEnvironment;
import com.soldev.fieldservice.dataentity.TaskDataEnvironmentList;
import com.soldev.fieldservice.dataentity.TaskDataEquipment;
import com.soldev.fieldservice.dataentity.TaskDataEquipmentList;
import com.soldev.fieldservice.dataentity.TaskDataMachine;
import com.soldev.fieldservice.dataentity.TaskDataWorking;
import com.soldev.fieldservice.dataentity.TaskDataWorkingList;
import com.soldev.fieldservice.dataentity.WorkingCheckListForJob;
import com.soldev.fieldservice.dataentity.WorkingCheckListForJoblistByTaskType;
import com.soldev.fieldservice.uiadapter.TaskTabsViewPagerAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class TaskFormActivity extends AppCompatActivity {
    private TextView txtTaskTypeName,txtGPS,txtExtraOrder,txtVersion,btSignal;
    private EditText edtJobNo,edtTaskNo;
    private EditText edtJobDate;
    private EditText edtCustomerName;
    private EditText edtLocation,edtProject;
    private EditText edtGPS,edtExtraOrder;
    private ViewPager vpDetailTaskTab;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public TaskDataEntry taskDataEntry;
    private Button btSave,btSubmit,btReject;
    public List<TaskDataMachine> taskDataMachineList;
    public List<TaskDataConcreteTruck> taskDataConcreteTrucks;
    public List<ImageInfo> imageInfos;
    private TaskTabsViewPagerAdapter fragmentPagerAdapter;
    public double longitude;
    public double latitude;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private AlertDialog progressDialog;
    private ProgressBar pb_horizontal;
    public boolean deliveryModeOnly = false,displayMode = false;
    private String formType;
    private String errorMessage;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    private ConnectivityManager connectivityManager;
    private boolean dataConnection = false;
    private int totalImageQue=0,countSuccessQue = 0;
    private boolean unsuccessSending = false;
    private boolean hasBeenSubmit = false;

    private NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        hasBeenSubmit = false;
        if ( Build.VERSION.SDK_INT >= 23) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstant.REQUEST_FINE_LOCATION);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstant.REQUEST_COARSE_LOCATION);
            }
        }
//        try {
        startLocationUpdates();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getBoolean("DISPLAY")){
            displayMode = true;
        }
        String taskRecord = getDataFromLocal(bundle.getString("TASKNO"));
        if(taskRecord==null||displayMode){

            taskRecord = bundle.get("DATA").toString(); //ใช้ data จาก firebase

        }

        formType = bundle.getString("FORMTYPE");

        taskDataEntry = new Gson().fromJson(taskRecord,TaskDataEntry.class);
//        if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
//            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_QC_LIST).child(taskDataEntry.getTaskID()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    TaskDataEntry taskQCDataEntry = new TaskDataEntry();
//                    Log.e("firebasedata", "no data>" + dataSnapshot.toString());
//                    if(dataSnapshot.getValue()==null) {
//                        taskQCDataEntry = dataSnapshot.getValue(TaskDataEntry.class);
//                    }
////                    for(DataSnapshot dataRow:dataSnapshot.getChildren()){
////                        Log.e("showdata",dataSnapshot.toString());
////
////                        taskDataEntry.setQcBefTime("00");
////                        taskDataEntry.setQcBetweenTime("00");
////                        taskDataEntry.setQcAfterTime("00");
////                    }
//                    if(taskQCDataEntry==null) {
//                        Log.e("firebasedata", "no data>" + dataSnapshot.toString());
//                        setTaskData();
//                    } else {
//                        taskDataEntry = taskQCDataEntry;
//                        Log.e("firebasedata", "hasdata>" + dataSnapshot.toString());
//                        setTaskData();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//
//
//            });
//        } else {
//            setTaskData();
//        }
        setTaskData();
        if(!displayMode&&taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)&&
                AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                    .child("taskStatus").setValue(AppConstant.TASK_STATUS_READ);
        } else if(AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)&&taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)){
            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_QC_LIST).child(taskDataEntry.getTaskID())
                    .child("taskStatus").setValue(AppConstant.TASK_STATUS_READ);
            databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_QC_LIST).child(taskDataEntry.getTaskID())
                    .child("qcStatus").setValue(AppConstant.TASK_STATUS_READ);
        }
    }

    private String toJson(String s) {
        s = s.substring(0, s.length()).replace("{", "{\"");
        s = s.substring(0, s.length()).replace("}", "\"}");
        s = s.substring(0, s.length()).replace(", ", "\", \"");
        s = s.substring(0, s.length()).replace("=", "\":\"");
        s = s.substring(0, s.length()).replace("\"[", "[");
        s = s.substring(0, s.length()).replace("]\"", "]");
        s = s.substring(0, s.length()).replace("}\", \"{", "}, {");
        return s;
    }

    private void setTaskData(){
        if(taskDataEntry!=null) {
            latitude = taskDataEntry.getLatitude();
            longitude = taskDataEntry.getLongitude();

            setBinding();
            setEvent();
            setHeader(taskDataEntry);
            setDetailTabs();
            if (!displayMode) {
//            if (taskDataEntry.getTaskScreenType().equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE) &&
                if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE) &&
                        !deliveryModeOnly) {
                    //generate data
                    if (taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_PUMP)) {//Pump Works
                        if (taskDataEntry.getEquipmentList() == null || taskDataEntry.getEquipmentList().size() == 0) {
                            generateDataEquiptment(taskDataEntry.getTaskTypeCheckList());
                        }
                        if (taskDataEntry.getEnvironmentCheckLists() == null || taskDataEntry.getEnvironmentCheckLists().size() == 0) {
                            generateDataCheckList(taskDataEntry.getTaskTypeCheckList());
                        } else if (taskDataEntry.getWorkingSiteCheckList() == null || taskDataEntry.getWorkingSiteCheckList().size() == 0) {
                            generateDataSiteCheckList(taskDataEntry.getTaskTypeCheckList());
                        }
                        if (taskDataEntry.getExtraFeeList() == null || taskDataEntry.getExtraFeeList().size() == 0) {
                            generateExtraFee();
                        }
//                    generateQCDataCheckList(taskDataEntry.getTaskTypeCheckList());

                    } else if (taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                            taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH) ||
                            taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_OTHER)) {//Polist and other Works

                        if (taskDataEntry.getEquipmentList() == null || taskDataEntry.getEquipmentList().size() == 0) {
                            Log.e("generatedata","equiptment");
                            generateDataEquiptment(taskDataEntry.getTaskTypeCheckList());
                        }
                        if (taskDataEntry.getEnvironmentCheckLists() == null || taskDataEntry.getEnvironmentCheckLists().size() == 0) {
                            generateDataCheckList(taskDataEntry.getTaskTypeCheckList());
                        } else if (taskDataEntry.getWorkingSiteCheckList() == null || taskDataEntry.getWorkingSiteCheckList().size() == 0) {
                            generateDataSiteCheckList(taskDataEntry.getTaskTypeCheckList());
                        }
//                    generateQCDataCheckList(taskDataEntry.getTaskTypeCheckList());

                    }

                } else if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)
//                    else if (taskDataEntry.getTaskScreenType().equalsIgnoreCase(AppConstant.USER_QC_ROLE)
                        && (taskDataEntry.getTaskGroupCode().equalsIgnoreCase("2") ||
                        taskDataEntry.getTaskGroupCode().equalsIgnoreCase("3"))) {
                    Log.e("generateQC", "gen1");
                    try{
                        Log.e("generateQC", "gen1 number:"+taskDataEntry.getQcWorkingSiteCheckList().size() );
                        for (int i = 0; i < taskDataEntry.getQcWorkingSiteCheckList().size(); i++) {
                            Log.e("generateQC", "shoedata:"+taskDataEntry.getQcWorkingSiteCheckList().get(i).getRowNumber() );
                            Log.e("generateQC", "shoedata:"+taskDataEntry.getQcWorkingSiteCheckList().get(i).getDescription() );
                        }
                    } catch(Exception e){

                    }
                    if (taskDataEntry.getQcWorkingSiteCheckList() == null || taskDataEntry.getQcWorkingSiteCheckList().size() == 0 || taskDataEntry.getQcWorkingSiteCheckList().size() == 1) {
                        Log.e("generateQC", "gen2");
                        generateQCDataSiteCheckList(taskDataEntry.getTaskTypeCheckList());
                    }
                }
            }
//        } catch (Exception e){
//            Log.e("error","at start:"+e.toString());
//        }
        }
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        // You can now create a LatLng Object for use with maps
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    private void setBinding(){
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new TaskFormActivity.AndroidPhoneStateListener();

        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT)
        {
            // SIM card
            telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
        txtTaskTypeName = findViewById(R.id.txtTaskTypeName);
        edtJobNo = findViewById(R.id.edtJobNo);
        edtTaskNo = findViewById(R.id.edtTaskNo);
        edtJobDate = findViewById(R.id.edtJobDate);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtLocation = findViewById(R.id.edtLocation);
        edtProject = findViewById(R.id.edtProject);
        edtGPS = findViewById(R.id.edtGPS);
        edtExtraOrder = findViewById(R.id.edtExtraOrder);
        txtGPS = findViewById(R.id.txtGPS);
        txtExtraOrder = findViewById(R.id.txtExtraOrder);
        vpDetailTaskTab = findViewById(R.id.vpDetailTaskTab);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(vpDetailTaskTab);
        btSave = findViewById(R.id.btSave);
        btSubmit = findViewById(R.id.btSubmit);
        btReject = findViewById(R.id.btCancel);
        btSignal = findViewById(R.id.btSignal);
        txtVersion = findViewById(R.id.txtVersion);
        if(displayMode){
            btSignal.setVisibility(View.GONE);
            btSubmit.setVisibility(View.GONE);
            btSave.setVisibility(View.GONE);
            btReject.setVisibility(View.GONE);
        }
//        employees = new ArrayList<>();
        imageInfos = new ArrayList<>();
        uploadPrgressDialog();
        if(!displayMode) {
            if (taskDataEntry.getDeliveryStatus() != null &&
                    taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)) {
                deliveryModeOnly = true;
            } else {
                deliveryModeOnly = false;
            }

//            if (taskDataEntry.getTaskScreenType().equalsIgnoreCase(AppConstant.USER_QC_ROLE)) {
            if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
                btReject.setVisibility(View.GONE);
            }
        } else {
//            taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
        }
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            txtVersion.setText("Version "+version);
        } catch (PackageManager.NameNotFoundException e) {
            txtVersion.setText("");
            e.printStackTrace();
        }

    }

    private void setEvent(){
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                view.setClickable(false);
                saveData(taskDataEntry.getTaskGroupCode(),false,true,false);
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                view.setClickable(false);
                btSave.setClickable(false);
                btSave.setEnabled(false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TaskFormActivity.this);
                alertDialog.setTitle(getString(R.string.label_send_task))
                        .setMessage(getString(R.string.label_confirm_send_task))
                        .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btSubmit.setEnabled(true);
                                btSubmit.setClickable(true);

                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveData(taskDataEntry.getTaskGroupCode(),true,true,false);
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });
        edtGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGoogleMapRequest();
            }
        });
        txtGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGoogleMapRequest();
            }
        });
        btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectTask();
            }
        });

    }

    private void sendGoogleMapRequest(){
        String latString = AppUitility.isStringNull(Double.toString(taskDataEntry.getLatitude()),"0");
        String lonString = AppUitility.isStringNull(Double.toString(taskDataEntry.getLongitude()),"0");
//        Uri gmmIntentUri = Uri.parse("geo:"+latString+","+lonString);
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latString+","+lonString+"&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void setHeader(TaskDataEntry taskDataEntry){
        try {
            String taskTitle = taskDataEntry.getTaskType();
            if(taskDataEntry.getCreateRemark()!=null&&taskDataEntry.getCreateRemark().length()>0){
                taskTitle = taskTitle +" "+ taskDataEntry.getCreateRemark();
            }
            txtTaskTypeName.setText(taskTitle);
            edtJobNo.setText(taskDataEntry.getJobNo());
            edtTaskNo.setText(taskDataEntry.getTaskNo());
            String jobDate = AppUitility.convertDateObjToString(AppUitility.formatddMMyy,
                    AppUitility.convertStringToDateObj(AppUitility.formatddMMyy,taskDataEntry.getTaskDate()));
            edtJobDate.setText(jobDate);
            edtCustomerName.setText(taskDataEntry.getCustomerName());
            edtLocation.setText(taskDataEntry.getLocation());
            edtProject.setText(taskDataEntry.getProjectName());
            edtGPS.setText(taskDataEntry.getLatitude() + "," + taskDataEntry.getLongitude());
            if(taskDataEntry.getTaskGroupCode().equalsIgnoreCase("1")){
//                edtExtraOrder.setText(taskDataEntry.getCreateRemark());
                edtExtraOrder.setVisibility(View.GONE);
                txtExtraOrder.setVisibility(View.GONE);
            } else if (taskDataEntry.getTaskGroupCode().equalsIgnoreCase("5")){
                String extraOrder = taskDataEntry.getContactName();
//                if(taskDataEntry.getContactName()!=null&&!taskDataEntry.getContactName().equalsIgnoreCase("")){
//                    extraOrder = extraOrder +","+taskDataEntry.getContactName();
//                }
                edtExtraOrder.setText(extraOrder);
            } else {
                String extraOrder = getString(R.string.label_polished_order_area_short) + ":"
                        + taskDataEntry.getAreaByOrder();
//                if(extraOrder!=null&&extraOrder.length()>0) {
//                    extraOrder = taskDataEntry.getCreateRemark() + "," + getString(R.string.label_polished_order_area_short) + ":"
//                            + taskDataEntry.getAreaByOrder();
//                } else {
//                    extraOrder  = getString(R.string.label_polished_order_area_short) + ":"
//                            + taskDataEntry.getAreaByOrder();
//                }
                edtExtraOrder.setText(extraOrder);
            }
        } catch (Exception e){

        }
    }
    private void setDetailTabs(){
        vpDetailTaskTab.setOffscreenPageLimit(8);
        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentPagerAdapter = new TaskTabsViewPagerAdapter(TaskFormActivity.this, fragmentManager, taskDataEntry.getTaskID(),taskDataEntry.getTaskGroupCode(),taskDataEntry.getTaskScreenType());
        fragmentPagerAdapter = new TaskTabsViewPagerAdapter(TaskFormActivity.this, fragmentManager, taskDataEntry.getTaskID(),taskDataEntry.getTaskGroupCode(),AppPreference.getUserRole(TaskFormActivity.this));
        vpDetailTaskTab.setAdapter(fragmentPagerAdapter);
    }

    private void generateDataEquiptment(final String taskTypeCode){
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT)
                .orderByChild("taskTypeCode").equalTo(taskTypeCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataEquipmentList> taskDataEquipmentLists = new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    try {
                        EquipmentForJobListByTaskType equipmentForJobListByTaskType = dataSnapshot1.getValue(EquipmentForJobListByTaskType.class);

                        for (int i = 0; i < equipmentForJobListByTaskType.getEquipmentForJobsGroup().size(); i++) {
//                            Log.e("gemerate","dataSnapshot:"+equipmentForJobListByTaskType.getEquipmentForJobsGroup().get(i).getDescription());
                            List<TaskDataEquipment> taskDataEquipments = new ArrayList<>();
                            for (int j = 0; j < equipmentForJobListByTaskType.getEquipmentForJobsGroup().get(i).getEquipmentForJobs().size(); j++) {
                                EquipmentForJob equipmentForJob = equipmentForJobListByTaskType.getEquipmentForJobsGroup().get(i).getEquipmentForJobs().get(j);
                                TaskDataEquipment taskDataEquipment = new TaskDataEquipment(equipmentForJob.getRowNo(),
                                        equipmentForJob.getDescription(),
                                        equipmentForJob.getUnits(),
                                        -1,
                                        -1);
                                taskDataEquipments.add(taskDataEquipment);

                            }
                            TaskDataEquipmentList taskDataEquipmentList = new TaskDataEquipmentList(equipmentForJobListByTaskType.getEquipmentForJobsGroup().get(i).getRowNumber(),
                                    equipmentForJobListByTaskType.getEquipmentForJobsGroup().get(i).getDescription(),
                                    taskDataEquipments);
                            taskDataEquipmentLists.add(taskDataEquipmentList);
                        }
                    } catch (Exception e){
                        Log.e("equiptmenterror",e.toString());
                    }

                }
                if(!displayMode) {
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                            .child("equipmentList").setValue(taskDataEquipmentLists);
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                            .child("taskStatus").setValue(AppConstant.TASK_STATUS_READ);
                    if(taskDataEntry.getEquipmentList()==null||taskDataEntry.getEquipmentList().size()!=taskDataEquipmentLists.size()) {
                        taskDataEntry.setEquipmentList(taskDataEquipmentLists);
                    }
                }
                TaskFormEquipmentTabFragment fragment = (TaskFormEquipmentTabFragment)fragmentPagerAdapter.getRegisteredFragment(1);
                if(fragment!=null) {
                    fragment.showTask(taskDataEntry.getTaskID());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    private void generateDataCheckList(String taskTypeCode){
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST)
                .orderByChild("taskTypeCode").equalTo(taskTypeCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataEnvironmentList> taskDataEnvironmentLists = new ArrayList<>();
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = dataSnapshot1.getValue(EnvironmentCheckListForJobListByTaskType.class);
                        for (int i = 0; i < environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().size(); i++) {
                            List<TaskDataEnvironment> taskDataEnvironments = new ArrayList<>();
                            for (int j = 0; j < environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().size(); j++) {
                                EnvironmentCheckListForJob environmentCheckListForJob = environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j);
                                TaskDataEnvironment taskDataEnvironment = new TaskDataEnvironment(environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j).getRowNo(),
                                        environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j).getDescription(),
                                        environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j).isCheckBefore(),
                                        0,
                                        "",
                                        environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j).isCheckBetween(),
                                        0,
                                        "",
                                        environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j).isCheckAfter(),
                                        0,
                                        "");
                                taskDataEnvironments.add(taskDataEnvironment);
                            }
                            TaskDataEnvironmentList taskDataEnvironmentList = new TaskDataEnvironmentList(environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getRowNumber(),
                                    environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getDescription(),
                                    taskDataEnvironments);
                            taskDataEnvironmentLists.add(taskDataEnvironmentList);
                        }
                    }
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                            .child("environmentCheckLists").setValue(taskDataEnvironmentLists);
                    if(taskDataEntry.getEnvironmentCheckLists()==null||taskDataEntry.getEnvironmentCheckLists().size()!=taskDataEnvironmentLists.size()){
                        taskDataEntry.setEnvironmentCheckLists(taskDataEnvironmentLists);
                    }
                } catch (Exception e){
                    Log.e("errorgenerate","001:"+e.toString());
                }

                generateDataSiteCheckList(taskDataEntry.getTaskTypeCode());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void generateDataSiteCheckList(String taskTypeCode){

        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST)
                .orderByChild("taskTypeCode").equalTo(taskTypeCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataWorkingList> taskDataEnvironmentLists = new ArrayList<>();
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = dataSnapshot1.getValue(WorkingCheckListForJoblistByTaskType.class);
                        for (int i = 0; i < workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().size(); i++) {
                            List<TaskDataWorking> taskDataWorkings = new ArrayList<>();
                            for (int j = 0; j < workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getWorkingCheckListForJobs().size(); j++) {
                                WorkingCheckListForJob workingCheckListForJob = workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getWorkingCheckListForJobs().get(j);
                                TaskDataWorking taskDataWorking = new TaskDataWorking(workingCheckListForJob.getRowNo(),
                                        workingCheckListForJob.getDescription(),
                                        workingCheckListForJob.isCheckBefore(),
                                        0,
                                        "",
                                        workingCheckListForJob.isCheckBetween(),
                                        0,
                                        "",
                                        workingCheckListForJob.isCheckAfter(),
                                        0,
                                        "");
                                taskDataWorkings.add(taskDataWorking);

                            }
                            TaskDataWorkingList taskDataWorkingList = new TaskDataWorkingList(workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getRowNumber(),
                                    workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getDescription(),
                                    taskDataWorkings);
                            taskDataEnvironmentLists.add(taskDataWorkingList);
                        }
                    }
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                            .child("workingSiteCheckList").setValue(taskDataEnvironmentLists);
//                databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskHeader.getTaskID())
//                        .child("taskStatus").setValue(AppConstant.TASK_STATUS_READ);
                    if(taskDataEntry.getWorkingSiteCheckList()==null||taskDataEnvironmentLists.size()!=taskDataEntry.getWorkingSiteCheckList().size()){
                        taskDataEntry.setWorkingSiteCheckList(taskDataEnvironmentLists);
                    }
                    TaskFormCheckingListTabFragement fragment = (TaskFormCheckingListTabFragement) fragmentPagerAdapter.getRegisteredFragment(2);
                    if (taskDataEntry != null && taskDataEntry.getTaskID() != null && fragment != null) {
                        fragment.showTask(taskDataEntry.getTaskID());
                    }
                } catch(Exception e){
                    Log.e("error","generate002:"+e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void generateExtraFee(){
        List<TaskDataExtraFee> taskDataExtraFees = new ArrayList<>();
        List<String> extraFee = new ArrayList<>();
        extraFee.add("ค่า Standby 4-8 ชั่วโมง(เที่ยว)");
        extraFee.add("ค่า Standby เกิน 8 ชั่วโมง(ชั่วโมง)");
        extraFee.add("ค่าติดตั้งท่อแนวดิ่ง(ชั้น)");
        extraFee.add("ค่าติดตั้งท่อแนวนอน(เมตร)");
        extraFee.add("ค่าเดินทางกรณีปั๊มไม่ได้(เที่ยว)");
        extraFee.add("ค่าเคลื่อนย้ายเกิน 300 เมตรหรือครั้งที่ 4(ครั้ง)");
        extraFee.add("ค่าเคลื่อนย้ายปั๊มลาก (ครั้ง)");
        extraFee.add("ค่าเดินทางนอกเขตปฏิบัติงาน(เที่ยว)");
        for (int i = 0; i < extraFee.size() ; i++) {
            TaskDataExtraFee taskDataExtraFee = new TaskDataExtraFee(i,extraFee.get(i),0,"I","");
            taskDataExtraFees.add(taskDataExtraFee);
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                .child("extraFeeList").setValue(taskDataExtraFees);
        taskDataEntry.setExtraFeeList(taskDataExtraFees);
        TaskFormExtraFeeTabFragment fragment = (TaskFormExtraFeeTabFragment) fragmentPagerAdapter.getRegisteredFragment(4);
        if(fragment!=null) {
            fragment.showTask();
        }
    }

    private void generateQCDataCheckList(String taskTypeCode){

        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST)
                .orderByChild("taskTypeCode").equalTo(taskTypeCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataEnvironmentList> taskDataEnvironmentLists = new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = dataSnapshot1.getValue(EnvironmentCheckListForJobListByTaskType.class);
                    for (int i = 0; i  < environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().size() ; i++) {
                        List<TaskDataEnvironment> taskDataEnvironments = new ArrayList<>();
                        for (int j = 0; j < environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().size(); j++) {
                            EnvironmentCheckListForJob environmentCheckListForJob = environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getEnvironmentCheckListForJobs().get(j);
                            TaskDataEnvironment taskDataEnvironment = new TaskDataEnvironment(environmentCheckListForJob.getRowNo(),
                                    environmentCheckListForJob.getDescription(),
                                    true,
                                    0,
                                    "",
                                    true,
                                    0,
                                    "",
                                    true,
                                    0,
                                    "");
                            taskDataEnvironments.add(taskDataEnvironment);

                        }
                        TaskDataEnvironmentList taskDataEnvironmentList = new TaskDataEnvironmentList(environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getRowNumber(),
                                environmentCheckListForJobListByTaskType.getEnvironmentCheckListForJobGroups().get(i).getDescription(),
                                taskDataEnvironments);
                        taskDataEnvironmentLists.add(taskDataEnvironmentList);
                    }
                }
                databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                        .child("qcEnvironmentCheckLists").setValue(taskDataEnvironmentLists);
//                databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskHeader.getTaskID())
//                        .child("taskStatus").setValue(AppConstant.TASK_STATUS_READ);

//                generateQCDataSiteCheckList(taskDataEntry.getTaskTypeCode());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void generateQCDataSiteCheckList(String taskTypeCode){

        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST)
                .orderByChild("taskTypeCode").equalTo(taskTypeCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataWorkingList> taskDataEnvironmentLists = new ArrayList<>();
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = dataSnapshot1.getValue(WorkingCheckListForJoblistByTaskType.class);
                        for (int i = 0; i < workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().size(); i++) {
                            List<TaskDataWorking> taskDataWorkings = new ArrayList<>();
                            for (int j = 0; j < workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getWorkingCheckListForJobs().size(); j++) {
                                WorkingCheckListForJob workingCheckListForJob = workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getWorkingCheckListForJobs().get(j);
//                                Log.e("generateQC", "decription>"+workingCheckListForJob.getDescription());
//                                Log.e("generateQC", "before>" + workingCheckListForJob.isCheckBefore() +
//                                        "between>" + workingCheckListForJob.isCheckBetween() +
//                                        "after>" + workingCheckListForJob.isCheckAfter());
                                Log.e("generateQC","gen"+workingCheckListForJob.getDescription());
                                TaskDataWorking taskDataWorking = new TaskDataWorking(workingCheckListForJob.getRowNo(),
                                        workingCheckListForJob.getDescription(),
                                        workingCheckListForJob.isCheckBefore(),
                                        0,
                                        "",
                                        workingCheckListForJob.isCheckBetween(),
                                        0,
                                        "",
                                        workingCheckListForJob.isCheckAfter(),
                                        0,
                                        "");
                                taskDataWorkings.add(taskDataWorking);

                            }
                            TaskDataWorkingList taskDataWorkingList = new TaskDataWorkingList(workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getRowNumber(),
                                    workingCheckListForJoblistByTaskType.getWorkingCheckListForJobGroups().get(i).getDescription(),
                                    taskDataWorkings);
                            taskDataEnvironmentLists.add(taskDataWorkingList);
                        }
                    }
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_QC_LIST).child(taskDataEntry.getTaskID())
                            .child("qcWorkingSiteCheckList").setValue(taskDataEnvironmentLists);
                    TaskFormPerformQCFragment fragment = (TaskFormPerformQCFragment) fragmentPagerAdapter.getRegisteredFragment(0);
                    taskDataEntry.setQcWorkingSiteCheckList(taskDataEnvironmentLists);
                    Log.e("generateQC","beforecallfragment"+taskDataEntry.getQcWorkingSiteCheckList().size());
                    for (int i = 0; i < taskDataEntry.getQcWorkingSiteCheckList().get(0).getTaskDataWorkings().size(); i++) {
                        Log.e("generateQC",">>>>>"+taskDataEntry.getQcWorkingSiteCheckList().get(0).getTaskDataWorkings().get(i).getDescription());
                        Log.e("generateQC","isCheckBefore>>>>>"+taskDataEntry.getQcWorkingSiteCheckList().get(0).getTaskDataWorkings().get(i).isCheckBefore());
                        Log.e("generateQC","isCheckBetween>>>>>"+taskDataEntry.getQcWorkingSiteCheckList().get(0).getTaskDataWorkings().get(i).isCheckBetween());
                        Log.e("generateQC","isCheckAfter>>>>>"+taskDataEntry.getQcWorkingSiteCheckList().get(0).getTaskDataWorkings().get(i).isCheckAfter());
                    }
                    if (fragment != null) {
                        fragment.showTask(taskDataEntry.getTaskID());
                    }
                } catch (Exception e){
                    Log.e("generateQCerror","generateQC:"+e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isCheckListDataComplete(List<TaskDataEnvironmentList> checkList){
        boolean isComplete = true;
        for (int i = 0; i < checkList.size() ; i++) {
            TaskDataEnvironmentList taskDataEnvironmentList = checkList.get(i);
            for (int j = 0; j < taskDataEnvironmentList.getTaskDataEnvironment().size() ; j++) {
                TaskDataEnvironment taskDataEnvironment = taskDataEnvironmentList.getTaskDataEnvironment().get(j);
                if((taskDataEnvironment.isCheckBefore()&&taskDataEnvironment.getFoundBefore()==0)||
                        (taskDataEnvironment.isCheckBefore()&&taskDataEnvironment.getFoundBefore()!=1&&taskDataEnvironment.getRemarkBefore().trim().length()==0)){
                    isComplete = false;
                    btSubmit.setEnabled(true);
                    btSubmit.setClickable(true);
                    errorMessage= getString(R.string.error_checklist_not_complete);
                    break;
                }
                if(taskDataEnvironment.isCheckBetween()&&taskDataEnvironment.getFoundBetween()==0||
                        (taskDataEnvironment.isCheckBetween()&&taskDataEnvironment.getFoundBetween()!=1&&taskDataEnvironment.getRemarkBetween().trim().length()==0)){
                    isComplete = false;
                    btSubmit.setEnabled(true);
                    btSubmit.setClickable(true);
                    errorMessage= getString(R.string.error_checklist_not_complete);
                    break;
                }
                if(taskDataEnvironment.isCheckAfter()&&taskDataEnvironment.getFoundAfter()==0||
                        (taskDataEnvironment.isCheckAfter()&&taskDataEnvironment.getFoundAfter()!=1&&taskDataEnvironment.getRemarkAfter().trim().length()==0)){
                    isComplete = false;
                    btSubmit.setEnabled(true);
                    btSubmit.setClickable(true);
                    errorMessage= getString(R.string.error_checklist_not_complete);
                    break;
                }
            }
        }

        if(taskDataEntry.getTaskDataVehicle().getStartMileage()==0||taskDataEntry.getTaskDataVehicle().getEndMileage()==0){
            isComplete = false;
            if(errorMessage.trim().length()==0){
                errorMessage= getString(R.string.error_kilometers);
            } else {
                errorMessage = errorMessage + "," +getString(R.string.error_kilometers);
            }
        }

        if(taskDataEntry.getTaskDataVehicle().getAssetNo().trim().equalsIgnoreCase("")
                ||taskDataEntry.getTaskDataVehicle().getLicensePlate().trim().equalsIgnoreCase("")){
            isComplete = false;
            if(errorMessage.trim().length()==0){
                errorMessage= getString(R.string.error_vehicle_info);
            } else {
                errorMessage = errorMessage + "," +getString(R.string.error_vehicle_info);
            }
        }
        return isComplete;
    }

    private boolean isEquiptmentComplete(){
        boolean isComplete = true;
        int counIncomplete = 0;
        for (int i = 0; i < taskDataEntry.getEquipmentList().size() ; i++) {
            TaskDataEquipmentList taskDataEquipmentList  =  taskDataEntry.getEquipmentList().get(i);
            for (int j = 0; j < taskDataEquipmentList.getTaskDataEquipments().size(); j++) {
                TaskDataEquipment taskDataEquipment = taskDataEquipmentList.getTaskDataEquipments().get(j);
                if (taskDataEquipment.getCheckBeforeWork() == -1 || taskDataEquipment.getCheckAfterWork() == -1) {
                    counIncomplete++;
                }
            }
        }
        if(counIncomplete>0){
            isComplete = false;
            if(errorMessage.trim().length()==0){
                errorMessage= getString(R.string.error_equipment);
            } else {
                errorMessage = errorMessage + "," +getString(R.string.error_equipment);
            }

        }
        return isComplete;
    }

    private boolean isMachineExist(){
        if (taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH)) {
            if (taskDataMachineList.size() > 0) {
                return true;
            } else {
                if (errorMessage.trim().length() == 0) {
                    errorMessage = getString(R.string.error_machine);
                } else {
                    errorMessage = errorMessage + "," + getString(R.string.error_machine);
                }
                return false;
            }
        }
        return true;
    }

    public boolean isEmployeeComplete(){
        boolean isComplete = true;

        //use Employee from task
        List<Employee> employees = taskDataEntry.getEmployees();
        if(employees!=null){
            for (int i = 0; i < employees.size(); i++) {
                if(employees.get(i).getAbsent()==null||employees.get(i).getAbsent().equalsIgnoreCase("N")) {
                    if (employees.get(i).getTimeStart() == null ||
                            employees.get(i).getTimeStart().trim().equalsIgnoreCase("") ||
                            employees.get(i).getTimeEnd() == null ||
                            employees.get(i).getTimeEnd().trim().equalsIgnoreCase("")) {
                        isComplete = false;
                        if (errorMessage.trim().length() == 0) {
                            errorMessage = getString(R.string.error_employee_time);
                        } else {
                            errorMessage = errorMessage + "," + getString(R.string.error_employee_time);
                        }
                        break;
                    }
                }
            }
        }
        //--------


//        if(employees!=null){
//            for (int i = 0; i < employees.size(); i++) {
//                if(employees.get(i).getAbsent()==null||employees.get(i).getAbsent().equalsIgnoreCase("N")) {
//                    if (employees.get(i).getTimeStart() == null ||
//                            employees.get(i).getTimeStart().trim().equalsIgnoreCase("") ||
//                            employees.get(i).getTimeEnd() == null ||
//                            employees.get(i).getTimeEnd().trim().equalsIgnoreCase("")) {
//                        isComplete = false;
//                        if (errorMessage.trim().length() == 0) {
//                            errorMessage = getString(R.string.error_employee_time);
//                        } else {
//                            errorMessage = errorMessage + "," + getString(R.string.error_employee_time);
//                        }
//                        break;
//                    }
//                }
//            }
//        }
        return isComplete;
    }

    private boolean isAreaFilled(TaskDataEntry taskDataEntry){
        if(taskDataEntry.getAreaPerform().equalsIgnoreCase("")){
            if(errorMessage.trim().length()==0){
                errorMessage= getString(R.string.error_actual_area);
            } else {
                errorMessage = errorMessage + "," +getString(R.string.error_actual_area);
            }
            return false;
        }
        return true;
    }

    public boolean isTaskDataWorkingListComplete(List<TaskDataWorkingList> checkList){
        boolean isComplete = true;
        for (int i = 0; i < checkList.size() ; i++) {
            TaskDataWorkingList taskDataWorkingList = checkList.get(i);
            for (int j = 0; j < taskDataWorkingList.getTaskDataWorkings().size() ; j++) {
                TaskDataWorking taskDataWorking = taskDataWorkingList.getTaskDataWorkings().get(j);
                if((taskDataWorking.isCheckBefore()&&taskDataWorking.getFoundBefore()==0)||
                        (taskDataWorking.isCheckBefore()&&taskDataWorking.getFoundBefore()!=1&&taskDataWorking.getRemarkBefore().trim().length()==0)){
                    isComplete = false;
                    break;
                }
                if(taskDataWorking.isCheckBetween()&&taskDataWorking.getFoundBetween()==0||
                        (taskDataWorking.isCheckBetween()&&taskDataWorking.getFoundBetween()!=1&&taskDataWorking.getRemarkBetween().trim().length()==0)){
                    isComplete = false;
                    break;
                }
                if(taskDataWorking.isCheckAfter()&&taskDataWorking.getFoundAfter()==0||
                        (taskDataWorking.isCheckAfter()&&taskDataWorking.getFoundAfter()!=1&&taskDataWorking.getRemarkAfter().trim().length()==0)){
                    isComplete = false;
                    break;
                }
            }
        }
        return  isComplete;
    }

    private boolean isDeliveryDataComplete(boolean hasReceiver,String receiverName,String receiverContact, String teamBehavior,String teamPerformQuality){
        boolean isComplete = true;
        if(hasReceiver ){
            if(receiverName.trim().length()>0&&receiverContact.trim().length()>0&&
                    teamBehavior.trim().length()>0&&teamPerformQuality.trim().length()>0){
                isComplete = true;
            } else {
                isComplete = false;
                errorMessage= getString(R.string.error_delivery);
            }
        }
        return isComplete;
    }

    private boolean isDeliveryDataCompleteForNonPerformed(boolean hasReceiver,String receiverName,String receiverContact, String teamBehavior,String teamPerformQuality){
        boolean isComplete = true;
        if(hasReceiver ){
            if(receiverName.trim().length()>0&&receiverContact.trim().length()>0){
                isComplete = true;
            } else {
                isComplete = false;
                errorMessage= getString(R.string.error_delivery);
            }
        }
        return isComplete;
    }

    private boolean isCommunicationAble(){
        if(!dataConnection){
            errorMessage= getString(R.string.error_connection);
        }
        return dataConnection;
    }

    public void saveData(String taskGroupCode, boolean isSubmit,boolean showDialog, boolean isSendback){
        try {
            errorMessage = "";
            uploadPrgressDialog();
            if (showDialog) {
                progressDialog.show();
            }
//        if(taskDataEntry.getTaskScreenType().equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                if (taskGroupCode.equalsIgnoreCase(AppConstant.TASK_TYPE_PUMP)) {
                    TaskFormEquipmentTabFragment taskFormEquipmentTabFragment = (TaskFormEquipmentTabFragment) fragmentPagerAdapter.getRegisteredFragment(1);
                    TaskFormCheckingListTabFragement taskFormCheckingListTabFragement = (TaskFormCheckingListTabFragement) fragmentPagerAdapter.getRegisteredFragment(2);
                    TaskFormPumpPerformTabFragment taskFormPumpPerformTabFragment = (TaskFormPumpPerformTabFragment) fragmentPagerAdapter.getRegisteredFragment(3);
                    TaskFormExtraFeeTabFragment taskFormExtraFeeTabFragment = (TaskFormExtraFeeTabFragment) fragmentPagerAdapter.getRegisteredFragment(4);
                    TaskFormCloseJobTabFragment taskFormCloseJobTabFragment = (TaskFormCloseJobTabFragment) fragmentPagerAdapter.getRegisteredFragment(7);

                    //get Equipment tab
                    taskDataEntry.setEquipmentList(taskFormEquipmentTabFragment.getEquiptmentList());
                    taskDataEntry.setMachineList(taskDataMachineList);
                    //get Checking Tab
                    taskDataEntry.setEnvironmentCheckLists(taskFormCheckingListTabFragement.getTaskDataEnvironmentList());
                    taskDataEntry.setWorkingSiteCheckList(taskFormCheckingListTabFragement.getTaskDataWorkingList());
                    taskDataEntry.setTaskDataVehicle(taskFormCheckingListTabFragement.getVehicle());
//                taskDataEntry.setEmployees(employees);
                    // Image tab
                    taskDataEntry.setImageInfos(imageInfos);
                    //get perform tab
                    HashMap<String, String> hashMap = taskFormPumpPerformTabFragment.getData();
                    taskDataEntry.setConcreteType(AppUitility.isStringNull(hashMap.get("edtUsedConcrete"), ""));
                    taskDataEntry.setPipeLength(AppUitility.convertStringToLong(AppUitility.isStringNull(hashMap.get("edtPipeLenght"), "0")));
                    taskDataEntry.setPipeRemark(AppUitility.isStringNull(hashMap.get("edtPipeRemark"), ""));
                    getCheckInLocation(hashMap.get("edtCheckIn"));
                    taskDataEntry.setCheckInSignImage(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg");
                    taskDataEntry.setDrawingImage(AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg");
                    taskDataEntry.setPipeImage(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING + ".jpg");
                    taskDataEntry.setTaskDataConcreteTrucks(taskDataConcreteTrucks);
                    Log.e("showcompare", "a1:" + hashMap.get("edtTotalVolumn"));
                    taskDataEntry.setTotalCementVolumn(AppUitility.convertStringToDouble(AppUitility.isStringNull(hashMap.get("edtTotalVolumn"), "0")));
                    Log.e("showcompare", "get back:" + taskDataEntry.getTotalCementVolumn());
                    // get Extra Fee
                    taskDataEntry.setExtraFeeList(taskFormExtraFeeTabFragment.getExtraFeeList());
                    //deliver tab
                    HashMap<String, String> hashMapDelivery = taskFormCloseJobTabFragment.getData();
                    if (hashMapDelivery.get("hasReceiver").equalsIgnoreCase("Y")) {
                        taskDataEntry.setHasReceiver(true);
                    } else {
                        taskDataEntry.setHasReceiver(false);
                    }
                    taskDataEntry.setTaskReceiver(hashMapDelivery.get("receiverName"));
                    taskDataEntry.setTaskReceiverContact(hashMapDelivery.get("receiverContact"));
                    taskDataEntry.setTeamBehavior(hashMapDelivery.get("teamBehavior"));
                    taskDataEntry.setTeamPerformQaulity(hashMapDelivery.get("teamPerformQuality"));
                    taskDataEntry.setTaskRemark(hashMapDelivery.get("edtTaskRemark"));
                    taskDataEntry.setReceiveSignature(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE + ".jpg");
                    taskDataEntry.setDeliverSignature(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER + ".jpg");
                    //save to Firebase

                    if (isSubmit) {
                        if (isCommunicationAble()) {
                            if (isSendback) {
                                taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REASSIGN);
                                taskDataEntry.setAlreadyPrinted(false);
                                sendImagesToServer(true, false, taskDataEntry.getTaskGroupCode());
                            } else {
                                if (checkPumpQuantity() && isCheckListDataComplete(taskFormCheckingListTabFragement.getTaskDataEnvironmentList())
                                        && isTaskDataWorkingListComplete(taskFormCheckingListTabFragement.getTaskDataWorkingList())
                                        && isDeliveryDataComplete(taskDataEntry.isHasReceiver(), hashMapDelivery.get("receiverName"), hashMapDelivery.get("receiverContact")
                                        , hashMapDelivery.get("teamBehavior"), hashMapDelivery.get("teamPerformQuality"))
                                        && isEmployeeComplete() && isEquiptmentComplete()) {
//                                    if (taskDataEntry.isHasReceiver()) {
//                                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
//                                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
//                                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
//                                    } else {
//                                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REDELIVER);
//                                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
//                                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_PENDING);
//                                    }
                                    sendImagesToServer(false, taskDataEntry.isHasReceiver(), taskDataEntry.getTaskGroupCode());
                                } else {
                                    dataNotCompleteDialog();
                                }
                            }
                        } else {
                            noConnectionDialog();
                        }
                    } else {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_PROGRESS);
                        if(AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
                            taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_PROGRESS);
                        }
//                    if(saveLocalData()) {
                        writeToFirebase(showDialog, isSubmit,isSendback);
//                    }
                    }

                } else if (taskGroupCode.equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                        taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH) ||
                        taskDataEntry.getTaskGroupCode().equalsIgnoreCase(AppConstant.TASK_TYPE_OTHER)) {
                    TaskFormEquipmentTabFragment taskFormEquipmentTabFragment = (TaskFormEquipmentTabFragment) fragmentPagerAdapter.getRegisteredFragment(1);
                    TaskFormCheckingListTabFragement taskFormCheckingListTabFragement = (TaskFormCheckingListTabFragement) fragmentPagerAdapter.getRegisteredFragment(2);
                    TaskFormPolishPerformTabFragment taskFormPolishPerformTabFragment = (TaskFormPolishPerformTabFragment) fragmentPagerAdapter.getRegisteredFragment(3);
                    TaskFormCloseJobTabFragment taskFormCloseJobTabFragment = (TaskFormCloseJobTabFragment) fragmentPagerAdapter.getRegisteredFragment(6);
                    //get Equipment tab
                    taskDataEntry.setEquipmentList(taskFormEquipmentTabFragment.getEquiptmentList());
                    taskDataEntry.setMachineList(taskDataMachineList);
                    //get Checking Tab
                    taskDataEntry.setEnvironmentCheckLists(taskFormCheckingListTabFragement.getTaskDataEnvironmentList());
                    taskDataEntry.setWorkingSiteCheckList(taskFormCheckingListTabFragement.getTaskDataWorkingList());
                    taskDataEntry.setTaskDataVehicle(taskFormCheckingListTabFragement.getVehicle());
                    //get Employee
//                taskDataEntry.setEmployees(employees);
                    //get Images
                    taskDataEntry.setImageInfos(imageInfos);
                    //get perform tab
                    HashMap<String, String> hashMap = taskFormPolishPerformTabFragment.getData();
                    taskDataEntry.setProductType(AppUitility.isStringNull(hashMap.get("edtUsedConcrete"), ""));
//                taskDataEntry.setAreaByPo(AppUitility.convertStringToLong(AppUitility.isStringNull(hashMap.get("edtPlanArea"), "0")));
//                taskDataEntry.setAreaActual(AppUitility.convertStringToLong(AppUitility.isStringNull(hashMap.get("edtActualArea"), "0")));
                    taskDataEntry.setAreaPerform(hashMap.get("edtActualArea"));
                    taskDataEntry.setPolishRemark(AppUitility.isStringNull(hashMap.get("edtPolishRemark"), ""));
                    getCheckInLocation(hashMap.get("edtCheckIn"));
                    taskDataEntry.setCheckInSignImage(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg");
                    taskDataEntry.setDrawingImage(AppConstant.DB_FIELD_FILE_NAME_DRAWING + ".jpg");
                    //deliver tab
                    HashMap<String, String> hashMapDelivery = taskFormCloseJobTabFragment.getData();
                    if (hashMapDelivery.get("hasReceiver").equalsIgnoreCase("Y")) {
                        taskDataEntry.setHasReceiver(true);
                    } else {
                        taskDataEntry.setHasReceiver(false);
                    }
                    taskDataEntry.setTaskRemark(hashMapDelivery.get("edtTaskRemark"));
                    taskDataEntry.setTaskReceiver(hashMapDelivery.get("receiverName"));
                    taskDataEntry.setTaskReceiverContact(hashMapDelivery.get("receiverContact"));
                    taskDataEntry.setTeamBehavior(hashMapDelivery.get("teamBehavior"));
                    taskDataEntry.setTeamPerformQaulity(hashMapDelivery.get("teamPerformQuality"));
                    taskDataEntry.setReceiveSignature(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE + ".jpg");
                    taskDataEntry.setDeliverSignature(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE_DELIVER + ".jpg");
                    //save to Firebase
                    if (isSubmit) {
                        if (isCommunicationAble()) {
                            if (isSendback) {
                                taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REASSIGN);
                                taskDataEntry.setAlreadyPrinted(false);
                                sendImagesToServer(true, false, taskDataEntry.getTaskGroupCode());
                            } else {
                                if (isCheckListDataComplete(taskFormCheckingListTabFragement.getTaskDataEnvironmentList())
                                        && isTaskDataWorkingListComplete(taskFormCheckingListTabFragement.getTaskDataWorkingList())
                                        && isDeliveryDataComplete(taskDataEntry.isHasReceiver(), hashMapDelivery.get("receiverName"), hashMapDelivery.get("receiverContact")
                                        , hashMapDelivery.get("teamBehavior"), hashMapDelivery.get("teamPerformQuality"))
                                        && isEmployeeComplete() && isAreaFilled(taskDataEntry) && isEquiptmentComplete()&&isMachineExist()) {
//                                    if (taskDataEntry.isHasReceiver()) {
//                                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
//                                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
//                                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
//                                    } else {
//                                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REDELIVER);
//                                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
//                                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_PENDING);
//                                    }
                                    sendImagesToServer(false, taskDataEntry.isHasReceiver(), taskDataEntry.getTaskGroupCode());
                                } else {
                                    dataNotCompleteDialog();
                                }
                            }
                        } else {
                            noConnectionDialog();
                        }
                    } else {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_PROGRESS);
                        if(AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
                            taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_PROGRESS);
                        }
                        writeToFirebase(showDialog, isSubmit,isSendback);
                    }
                } else if (taskGroupCode.equalsIgnoreCase(AppConstant.TASK_TYPE_DOCUMENT)) {
                    TaskFormPerformDocumentTabFragment taskFormPerformDocumentTabFragment = (TaskFormPerformDocumentTabFragment) fragmentPagerAdapter.getRegisteredFragment(0);
                    //get Images
                    HashMap<String, String> hashMapDelivery = taskFormPerformDocumentTabFragment.getData();
                    taskDataEntry.setTaskReceiver(hashMapDelivery.get("edtCheckBy"));
                    taskDataEntry.setTaskReceiverContact(hashMapDelivery.get("edtContactNo"));
                    taskDataEntry.setTaskDocRemark(hashMapDelivery.get("edtTaskRemark"));
                    getCheckInLocation(hashMapDelivery.get("edtCheckIn"));
                    taskDataEntry.setImageInfos(imageInfos);
                    updateUpdateData();
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID()).setValue(taskDataEntry);
                    if (isSubmit) {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
                        taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_NOTHING);
                        if(AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
                            taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_DONE);
                        }
                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
                        sendImagesToServer(false, taskDataEntry.isHasReceiver(), taskDataEntry.getTaskGroupCode());

//                    writeToFirebase(showDialog,isSubmit);
                    } else {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_PROGRESS);
                        if(AppPreference.getUserRole(this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
                            taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_PROGRESS);
                        }
                        writeToFirebase(showDialog, isSubmit,isSendback);
                    }
                }
            }
//        else if(taskDataEntry.getTaskScreenType().equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            else if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)) {
                TaskFormPerformQCFragment fragment = (TaskFormPerformQCFragment) fragmentPagerAdapter.getRegisteredFragment(0);
                HashMap<String, String> dataToSave = fragment.getData();
                Log.e("checkqc", "หshowdata:" + dataToSave.toString());
                taskDataEntry.setQcBefTime(dataToSave.get("edtQcBeforeTime"));
                taskDataEntry.setQcBetweenTime(dataToSave.get("edtQcBetweenTime"));
                taskDataEntry.setQcAfterTime(dataToSave.get("edtQcAfterTime"));
                taskDataEntry.setImageInfosQC(imageInfos);

//            TaskFormPerformQCFragment taskFormPerformQCFragment = (TaskFormPerformQCFragment) fragmentPagerAdapter.getRegisteredFragment(0);
                taskDataEntry.setQcWorkingSiteCheckList(fragment.getTaskDataWorkingList());
                if (isSubmit) {
                    taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_DONE);
                    hasBeenSubmit = true;
                    Log.e("showdata",taskDataEntry.getTaskNo()+":"+taskDataEntry.getCompareTaskDate()+","+taskDataEntry.getQcStatus());
//                    writeToFirebase(showDialog, isSubmit,isSendback);
                    sendImagesToServer(false, taskDataEntry.isHasReceiver(), taskDataEntry.getTaskGroupCode());
                } else {
//                    taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_PROGRESS);
                    taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_PROGRESS);
                    taskDataEntry.setQcStatus(AppConstant.TASK_STATUS_PROGRESS);
                    writeToFirebase(showDialog, isSubmit,isSendback);
                }
            }
        } catch (Exception e){
            Log.e("error","errorsave:"+e.toString());
        }
    }

    public void updateUpdateData(){
        taskDataEntry.setLastUpDateBy(AppPreference.getUserName(TaskFormActivity.this));
        taskDataEntry.setLastUpDateDate(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss, Calendar.getInstance().getTime()));
    }

    public boolean saveLocalData(){
        if(!displayMode) {
            boolean isDBWorkSucess = false;
            SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(TaskFormActivity.this);
            SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
            String queryData = "select * from " + SQLiteHelperUtility.TB_TASKLIST + " where " + SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
            Cursor dataCursor = sqLiteDatabase.rawQuery(queryData, null);
            if (dataCursor.getCount() == 0) {
                Log.e("updateLocalData", "insertdata");
                Gson gson = new Gson();
                String jsonData = gson.toJson(taskDataEntry);
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                if (sqLiteDatabase.insert(SQLiteHelperUtility.TB_TASKLIST, null, contentValues) > 0) {
                    isDBWorkSucess = true;
                }
            } else {
                Log.e("updateLocalData", "founddata");
                Gson gson = new Gson();
                String jsonData = gson.toJson(taskDataEntry);
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                String whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                int recordCount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, whereClause, null);
                if (recordCount > 0) {
                    Log.e("updateLocalData", "updatedata>" + recordCount + " records");
                    isDBWorkSucess = true;
                }

            }
            sqLiteDatabase.close();
            return isDBWorkSucess;
        }
        return true;
    }

    private String getDataFromLocal(String taskNo){
        SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(TaskFormActivity.this);
        SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
        String queryData = "select * from "+SQLiteHelperUtility.TB_TASKLIST+" where "+SQLiteHelperUtility.PERFORMTASKID+" = '"+taskNo+"'";
        Cursor dataCursor = sqLiteDatabase.rawQuery(queryData,null);
        if(dataCursor.getCount()>0){
            dataCursor.moveToFirst();
            Log.e("updateLocalData","getdatafromlocal>");
            return dataCursor.getString(dataCursor.getColumnIndex(SQLiteHelperUtility.PERFORMTASKDATA));

        }
        sqLiteDatabase.close();
//        Log.e("updateLocalData","nolocaldata>");
        return null;
    }

    private void sendImagesToServer(boolean isSendingBack,boolean hasReceiver,String forTaskGroupCode){
        saveLocalData();
        if(!processImage(isSendingBack,hasReceiver,forTaskGroupCode)){
//            Toast.makeText(TaskFormActivity.this,getString(R.string.error_send_picture),Toast.LENGTH_LONG).show();
            btSubmit.setEnabled(true);
            btSubmit.setClickable(true);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            submitFailDialog();
        } else {

        }
    }

    private void submitFailDialog(){
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) ) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TaskFormActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("การส่งงานไม่สำเร็จ").setMessage(getString(R.string.error_send_picture))
                    .setPositiveButton("รับทราบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }
    }

    private boolean processImage(final boolean isSendingBack, boolean hasReceiver, String forTaskGroupCode){

        //image
        final RequestQueue queue = Volley.newRequestQueue(TaskFormActivity.this);
        List<StringRequest> stringRequests = new ArrayList<>();
        String docData = "";

        //perform
        if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            docData = getImageDataAsString(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg");
            if (!docData.equalsIgnoreCase("NA")) {
//            totalImageQue++;
                stringRequests.add(uploadDoc(AppConstant.DB_FIELD_FILE_NAME_SIGNATURE + ".jpg", "JPG", docData, AppConstant.IMAGE_CAT_PERFORM));
            }
            ;
            docData = getImageDataAsString(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE + ".jpg");
            if (!docData.equalsIgnoreCase("NA")) {
//            totalImageQue++;
                stringRequests.add(uploadDoc(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE + ".jpg", "JPG", docData, AppConstant.IMAGE_CAT_PERFORM));
            }
            ;
            docData = getImageDataAsString(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING + ".jpg");
            if (!docData.equalsIgnoreCase("NA")) {
//            totalImageQue++;
                stringRequests.add(uploadDoc(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING + ".jpg", "JPG", docData, AppConstant.IMAGE_CAT_PERFORM));
            }
            ;

            //deliver
            docData = getImageDataAsString(taskDataEntry.getReceiveSignature());
            if (!docData.equalsIgnoreCase("NA")) {
//            totalImageQue++;
                stringRequests.add(uploadDoc(taskDataEntry.getReceiveSignature(), "JPG", docData, AppConstant.IMAGE_CAT_DELIVER));
            } else {
                if (!isSendingBack && hasReceiver) {
                    return false;
                }
            }
            docData = getImageDataAsString(taskDataEntry.getDeliverSignature());
            if (!docData.equalsIgnoreCase("NA")) {
//            totalImageQue++;
                stringRequests.add(uploadDoc(taskDataEntry.getDeliverSignature(), "JPG", docData, AppConstant.IMAGE_CAT_DELIVER));
            } else {
                if (!isSendingBack && hasReceiver) {
                    return false;
                }
            }


            if (forTaskGroupCode.equalsIgnoreCase("1")) {
                //truck
                if (taskDataConcreteTrucks != null) {
                    for (int i = 0; i < taskDataConcreteTrucks.size(); i++) {
                        TaskDataConcreteTruck taskDataConcreteTruck = taskDataConcreteTrucks.get(i);
                        List<ImageInfo> truckImageInfos = taskDataConcreteTruck.getImageInfoList();
                        if (truckImageInfos != null) {
                            for (int j = 0; j < truckImageInfos.size(); j++) {
                                ImageInfo imageInfo = truckImageInfos.get(j);
                                docData = getImageDataAsString(imageInfo.getImageName());
                                if (!docData.equalsIgnoreCase("NA")) {
//                                totalImageQue++;
                                    stringRequests.add(uploadDoc(imageInfo.getImageName(), "JPG", docData, AppConstant.IMAGE_CAT_TRUCK));
                                }
                                ;
                            }
                        }
                    }
                }
            }
        }
        //general
        Log.e("uploadDoc","before");
        if(imageInfos!=null) {
            Log.e("uploadDoc","start");
            for (int i = 0; i < imageInfos.size(); i++) {
                ImageInfo imageInfo = imageInfos.get(i);
                docData = getImageDataAsString(imageInfo.getImageName());
                if (!docData.equalsIgnoreCase("NA")) {
//                    totalImageQue++;
                    String imageCat;
                    if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)){
                        imageCat = AppConstant.IMAGE_CAT_GENERAL;
                    } else {
                        imageCat =AppConstant.IMAGE_CAT_QC;
                    }
                    stringRequests.add(uploadDoc(imageInfo.getImageName(), "JPG", docData,imageCat ));
                }
            }
        }
        //add to Volley Que
//
        for (int i = 0; i < stringRequests.size(); i++) {
            try{
                queue.add(stringRequests.get(i));
            } catch (Exception e){

            }
        }

        totalImageQue = 0;
        countSuccessQue = 0;
        unsuccessSending = false;
        totalImageQue = stringRequests.size();
        Log.e("showstringreq","size:"+totalImageQue);
        if(totalImageQue>0) {
            queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
                @Override
                public void onRequestFinished(Request<StringRequest> request) {
                    if(unsuccessSending){
                        queue.cancelAll(new RequestQueue.RequestFilter() {
                            @Override
                            public boolean apply(Request<?> request) {
                                return true;
                            }
                        });
                        progressDialog.dismiss();
                        submitFailDialog();
                    } else {
                        Log.e("showstringreq", "compare size:" + totalImageQue + "=" + countSuccessQue);
                        if (totalImageQue == countSuccessQue) {
                            writeToFirebase(true, true,isSendingBack);
                        }
                    }
                }

            });
        } else{
            writeToFirebase(true, true,isSendingBack);
        }

        return true;
    }

    private void writeToFirebasexxx(final boolean showDialog, final boolean isSubmit){
//        if(isSubmit){
//            hasBeenSubmit = true;
//        }
        if(saveLocalData()) {
            if(isSubmit){
                taskDataEntry.setAlreadyPrinted(false);
                if (taskDataEntry.isHasReceiver()) {
                    taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
                    taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
                    taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
                } else {
                    taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REDELIVER);
                    taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
                    taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_PENDING);
                }
            }
            DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
            connectedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);
                    if (connected) {
                        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskNo()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                TaskDataEntry taskDataEntryFromFB = dataSnapshot.getValue(TaskDataEntry.class);
                                if(taskDataEntryFromFB!=null&&taskDataEntryFromFB.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)) {
                                    String taskNode = "";
                                    if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                                        taskNode = AppConstant.DB_TASK_LIST;
                                    } else {
                                        taskNode = AppConstant.DB_TASK_QC_LIST;
                                    }
                                    updateUpdateData();
                                    new saveTaskBackup(taskDataEntry).execute();
                                    databaseReference.child(AppConstant.DB_ROOT).child(taskNode).child(taskDataEntry.getTaskID()).setValue(taskDataEntry).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("showdata", "error>" + e.toString());
                                            progressDialog.dismiss();
                                            submitFailDialog();

                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.e("showdata", "sucess>" + task.toString());
                                            if (isSubmit) {
                                                hasBeenSubmit = true;
                                                Log.e("showdata", "delete");
                                                SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(TaskFormActivity.this);
                                                SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
                                                String whereClause;
                                                Gson gson = new Gson();
                                                String jsonData = gson.toJson(taskDataEntry);
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                                                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                                whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                                sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, whereClause, null);
                                                sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);
//                                                if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
//                                                    sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);
//
//                                                } else {
//                                                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
//                                                            .child("qcStatus").setValue(AppConstant.TASK_STATUS_DONE);
//                                                }
                                                sqLiteDatabase.close();
                                            }
                                            if (showDialog) {
                                                pb_horizontal.setMax(100);
                                                pb_horizontal.setProgress(50);
                                                if (progressDialog.isShowing()) {
                                                    progressDialog.dismiss();
                                                }
                                                saveDialog(isSubmit);
                                            }
                                        }
                                    });
                                } else {
                                    submitFailDialog();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } else {
                        Log.e("showdata","not connected");
                        progressDialog.dismiss();
                        if(isSubmit) {
//                            Toast.makeText(TaskFormActivity.this, getString(R.string.error_send_signature), Toast.LENGTH_LONG).show();
                            submitFailDialog();
                        } else
                        if (showDialog) {
                            pb_horizontal.setMax(100);
                            pb_horizontal.setProgress(50);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            saveDialog(isSubmit);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.err.println("Listener was cancelled");
                }
            });



        }
    }
    private void writeToFirebase(final boolean showDialog, final boolean isSubmit,final boolean isRejected){
//        if(isSubmit){
//            hasBeenSubmit = true;
//        }
        if(saveLocalData()) {
            if(isSubmit){
                taskDataEntry.setAlreadyPrinted(false);
                if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                    if (isRejected) {
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REASSIGN);
                    } else if (taskDataEntry.isHasReceiver()) {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
                    } else {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REDELIVER);
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_PENDING);
                    }
                } else {
                    if (isRejected) {
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REASSIGN);
                    } else  {
                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_DONE);
                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_REVIEW);
                        taskDataEntry.setDeliveryStatus(AppConstant.TASK_STATUS_DONE);
                    }
                }
            }
            DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
            connectedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);
                    if (connected) {
                        Log.e("showdata","connected");
                        String taskNode = "";

                        Log.e("showdata",taskNode);
                        if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                            taskNode = AppConstant.DB_TASK_LIST;
                        } else {
                            taskNode = AppConstant.DB_TASK_QC_LIST;
                        }
                        updateUpdateData();
                        new saveTaskBackup(taskDataEntry).execute();
                        Log.e("showdatanode","taskNode>"+taskNode+":"+taskDataEntry.getTaskStatus()+":"+taskDataEntry.getQcStatus());
                        databaseReference.child(AppConstant.DB_ROOT).child(taskNode).child(taskDataEntry.getTaskID()).setValue(taskDataEntry).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("showdata","error>"+e.toString());
                                progressDialog.dismiss();
                                submitFailDialog();

                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e("showdata","sucess>"+task.toString());
                                if(isSubmit){
                                    hasBeenSubmit = true;
                                    Log.e("showdata","delete");
                                    SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(TaskFormActivity.this);
                                    SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
                                    String whereClause ;
                                    Gson gson = new Gson();
                                    String jsonData = gson.toJson(taskDataEntry);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                    sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, whereClause, null);
                                    if(AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                                        Log.e("deletedata","delete");
                                        sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);
                                    } else {
//                                        Gson gson = new Gson();
//                                        String jsonData = gson.toJson(taskDataEntry);
//                                        ContentValues contentValues = new ContentValues();
//                                        contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
//                                        contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
//                                        whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
//                                        sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, whereClause, null);
                                        sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);
                                       // databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID())
                                        //        .child("qcStatus").setValue(AppConstant.TASK_STATUS_DONE);
                                    }
                                    sqLiteDatabase.close();
                                }
                                if (showDialog) {
                                    pb_horizontal.setMax(100);
                                    pb_horizontal.setProgress(50);
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    saveDialog(isSubmit);
                                }
                            }
                        });

                    } else {
                        Log.e("showdata","not connected");
                        progressDialog.dismiss();
                        if(isSubmit) {
//                            Toast.makeText(TaskFormActivity.this, getString(R.string.error_send_signature), Toast.LENGTH_LONG).show();
                            submitFailDialog();
                        } else
                        if (showDialog) {
                            pb_horizontal.setMax(100);
                            pb_horizontal.setProgress(50);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            saveDialog(isSubmit);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.err.println("Listener was cancelled");
                }
            });



        }
    }

    public String getTaskID(){
        return taskDataEntry.getTaskID();
    }

    public boolean loadImageToView(String fileName, ImageView imageView){
        File directory = getExternalFilesDir(getTaskID());
        File imgFile = new File(directory, fileName+".jpg");
        Log.e("Showpath","loadimage:"+imgFile.getAbsolutePath());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            imageView.invalidate();
            return true;
        } else {
            return false;
        }
    }

    public String getImageDataAsString(String fileNamewithExtension){
        try {
            Log.e("uploadDoc","getfile:"+fileNamewithExtension);
            File directory = getExternalFilesDir(getTaskID());
            File imgFile = new File(directory, fileNamewithExtension);
            if (imgFile.exists()) {
                Bitmap bitmap = getResizedBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                return encoded;
            } else {
                return "NA";
            }
        } catch (Exception e){
            return "NA";
        }
    }


    public StringRequest uploadDoc(final String fileName, final String extension, final String docData,final String imageCategory){
//        if(imageCategory.equalsIgnoreCase(AppConstant.IMAGE_CAT_DELIVER)){
//            progressDialog.dismiss();
//            Toast.makeText(TaskFormActivity.this,"There is an error",Toast.LENGTH_LONG).show();
//            return  null;
//        }
        WebService webService = new WebService();
        String url =webService.getDocUploadURL();
        try{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseData = new JSONObject(response);
                                if (responseData.getBoolean("success")) {
                                    countSuccessQue++;
                                    Log.e("uploadDoc", "success responseData>" + responseData.toString());
                                }
                                else {
                                    unsuccessSending = true;
                                    Log.e("uploadDoc", "fail responseData>" + responseData.toString());
                                }
                            } catch (Exception e) {
                                Log.e("uploadDoc", "error>" + e.toString());
//                                Toast.makeText(TaskFormActivity.this, "ส่งงานไม่สำเร็จ กรุณาทดลองใหม่ภายหลัง", Toast.LENGTH_LONG).show();
                                submitFailDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("uploadDoc", "onErrorResponse>" + error.toString());
//                    Toast.makeText(TaskFormActivity.this, "ไม่สามารถส่งภาพได้ กรุณาทดลองใหม่ภายหลัง", Toast.LENGTH_LONG).show();
                    submitFailDialog();
                    progressDialog.dismiss();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(TaskFormActivity.this));
                    return params;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        Log.e("uploadDoc","show payload:"+imageCategory+","+fileName+","+extension);
                        DocumentClass documentClass = new DocumentClass(taskDataEntry.getTaskNo(),
                                imageCategory,
                                fileName,
                                extension,
                                docData);
                        Gson gson = new Gson();
                        String dataToSend = gson.toJson(documentClass);
                        return  dataToSend.getBytes();
                    } catch (Exception uee) {
                        Log.e("error","getBody:"+ edtCustomerName.toString());
                        return null;
                    }
                }
            };
            return stringRequest;
        } catch (Exception e){
            Log.e("error","upload doc:"+e.toString());
        }
        return null;
    }

    public void saveDialog(final boolean isSubmit){
        if(isSubmit) {
            hasBeenSubmit = true;
            finish();
        } else {
            btSave.setEnabled(true);
            btSave.setClickable(true);
            Toast.makeText(TaskFormActivity.this, getString(R.string.message_complete_data), Toast.LENGTH_LONG).show();
        }
    }

    public void dataNotCompleteDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.label_save_data)).setMessage( getString(R.string.message_incomplete_data)+" "+errorMessage).setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btSubmit.setClickable(true);
                btSubmit.setEnabled(true);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void noConnectionDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.label_save_data)).setMessage( errorMessage).setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btSubmit.setClickable(true);
                btSubmit.setEnabled(true);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void getImageFromServer(String docNo, String catName, final String fileName, final ImageView imageView ) {
        WebService webService = new WebService();
        RequestQueue queue = Volley.newRequestQueue(TaskFormActivity.this);
        String url =webService.getDocByNoURL()+docNo+","+catName+","+fileName + ",JPG";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
//                                Log.e("getImage","response>"+response);
                                Gson gson = new Gson();
                                JSONArray jsonArray = new JSONArray(response);
                                DocumentClass documentClass = gson.fromJson(jsonArray.getJSONObject(0).toString(),DocumentClass.class);
                                byte[] byteArray = Base64.decode(documentClass.getDocumentContent(),Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                File directory = getExternalFilesDir(taskDataEntry.getTaskID());
                                File imgFile = new File(directory, fileName);
                                try (FileOutputStream out = new FileOutputStream(imgFile)) {
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                                    // PNG is a lossless format, the compression factor (100) is ignored
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                Log.e("getImage","error>"+e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                recordWorkOrder();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(TaskFormActivity.this));
                    return params;
                }

            };
            queue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }

    }

    public void uploadPrgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        View dialogView = View.inflate(TaskFormActivity.this,R.layout.dialog_progress,null);
        pb_horizontal = dialogView.findViewById(R.id.pb_horizontal);
        builder.setView(dialogView);
        builder.setCancelable(true);
        progressDialog = builder.create();
    }

    private void rejectTask(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        builder.setCancelable(false);
        builder.setTitle("ยืนยันการยกเลิก").
                setMessage("งานที่ถูกยกเลิกจะถูกลบออกจากโปรแกรม กรุณายืนยัน").
                setPositiveButton(getString(R.string.label_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        taskDataEntry.setTaskFlowGroup(AppConstant.TASK_FLOW_GROUP_ASSGINER);
//                        taskDataEntry.setTaskStatus(AppConstant.TASK_STATUS_REASSIGN);
                        saveData(taskDataEntry.getTaskGroupCode(),true,true,true);
                        dialogInterface.dismiss();
                    }
                }).
                setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    public boolean checkPumpQuantity(){
        double totalConcrete = 0;
        for (int i = 0; i < taskDataConcreteTrucks.size(); i++) {
            TaskDataConcreteTruck taskDataConcreteTruck = taskDataConcreteTrucks.get(i);
            totalConcrete = totalConcrete + taskDataConcreteTruck.getAmount();
        }
//        Log.e("showcompare",totalConcrete+"<>"+taskDataEntry.getTotalCementVolumn());
        if(totalConcrete == taskDataEntry.getTotalCementVolumn()){
            return true;
        }

        errorMessage = getString(R.string.label_cue_total_not_correct);
        return false;
    }

    public void dialogRemark(final View clickedView, String textTitle, final boolean isNumericOnly){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        final View dialogView = View.inflate(TaskFormActivity.this,R.layout.dialog_edit_box,null);
        ((TextView)dialogView.findViewById(R.id.txtFieldName)).setText(textTitle);
        String valueText = ((TextView)clickedView).getText().toString();
        if(valueText.equalsIgnoreCase("0")){
            valueText="";
        }
        EditText edtBox = dialogView.findViewById(R.id.edtBox);
        edtBox.setText(valueText);
        edtBox.requestFocus();
        edtBox.setSelection(edtBox.getText().length());
        if(isNumericOnly) {
            edtBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                alertDialog.dismiss();
                String returnText =((EditText) dialogView.findViewById(R.id.edtBox)).getText().toString();
                if(returnText.trim().equalsIgnoreCase("")&&isNumericOnly){
                    returnText = "0";
                }
                ((TextView) clickedView).setText(returnText);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void dialogRemarkCheckLength(final View clickedView, final String textTitle, final int textLength){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        final View dialogView = View.inflate(TaskFormActivity.this,R.layout.dialog_edit_box,null);
        ((TextView)dialogView.findViewById(R.id.txtFieldName)).setText(textTitle);
        String valueText = ((TextView)clickedView).getText().toString();
        if(valueText.equalsIgnoreCase("0")){
            valueText="";
        }
        EditText edtBox = dialogView.findViewById(R.id.edtBox);
        edtBox.setText(valueText);
        edtBox.requestFocus();
        edtBox.setSelection(edtBox.getText().length());
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                String returnText =((EditText) dialogView.findViewById(R.id.edtBox)).getText().toString();
                if(returnText.length()==textLength) {
                    ((TextView) clickedView).setText(returnText);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(TaskFormActivity.this, textTitle+" "+textLength+" หลัก", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }

    public void dialogTotalPump(final View clickedView, String textTitle, boolean isNumericOnly, final double mimimumAmt, final TextView showedResultView, boolean hasDecimal){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskFormActivity.this);
        final View dialogView = View.inflate(TaskFormActivity.this,R.layout.dialog_edit_box,null);
        ((TextView)dialogView.findViewById(R.id.txtFieldName)).setText(textTitle);
        String valueText = ((TextView)clickedView).getText().toString();
        EditText edtBox = dialogView.findViewById(R.id.edtBox);
        if(valueText.equalsIgnoreCase("0")){
            valueText="";
        }
        edtBox.setText(valueText);
        edtBox.requestFocus();
        edtBox.setSelection(edtBox.getText().length());
        if(isNumericOnly) {
            if(hasDecimal){
                edtBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else {
                edtBox.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalAmount = 0;
                String returnString = "";
                try {
                    totalAmount = Double.parseDouble(((EditText) dialogView.findViewById(R.id.edtBox)).getText().toString());
                } catch (Exception e){
                    Log.e("showerror","dialogTotalPump:"+e.toString());
                }
                if(mimimumAmt>totalAmount){
                    returnString = AppUitility.convertDoubleToString(0,"###");
                } else {
                    returnString = AppUitility.convertDoubleToString(totalAmount-mimimumAmt,"###");
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                alertDialog.dismiss();
                String returnStringValue = ((EditText) dialogView.findViewById(R.id.edtBox)).getText().toString();
                if(returnStringValue.trim().equalsIgnoreCase("")){
                    returnStringValue = "0";
                }
                ((TextView) clickedView).setText(returnStringValue);
                showedResultView.setText(returnString);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void getCheckInLocation(String locationString) {
        if (locationString!=null&&!locationString.trim().equalsIgnoreCase("")) {
            String[] locationGPS = locationString.split(",");
            try {
                if (locationGPS.length == 2) {
                    taskDataEntry.setCheckinLatitude(Double.parseDouble(locationGPS[0]));
                    taskDataEntry.setCheckinLongitude(Double.parseDouble(locationGPS[1]));
                }
            } catch (Exception e) {
                taskDataEntry.setCheckinLatitude(0);
                taskDataEntry.setCheckinLongitude(0);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image) {
        int maxSize = 500;
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



    private class AndroidPhoneStateListener extends PhoneStateListener
    {
        int signalStrengthValue;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength)
        {
//            Log.e("signalStrengthValue","1:::"+signalStrengthValue);
            super.onSignalStrengthsChanged(signalStrength);

            networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null)
            {
                String signalDes = "";
                signalStrengthValue = -1*(113-(2*signalStrength.getGsmSignalStrength()));
                Log.e("signalStrengthValue",""+signalStrengthValue);
//                Toast.makeText(TaskFormActivity.this," signal Strength>"+signalStrengthValue,Toast.LENGTH_LONG).show();
                if(networkInfo.getTypeName().equalsIgnoreCase("mobile"))
                {
                    if(signalStrengthValue <= -100)
                    {
                        signalDes = getString(R.string.label_no_signal);
                        dataConnection = false ;
                    }
                    else
                    if(signalStrengthValue > -100 && signalStrengthValue <= -82)
                    {
                        signalDes = getString(R.string.label_weak_signal);
                        dataConnection = true;
                    }
                    else if(signalStrengthValue > -82)
                    {
                        signalDes = getString(R.string.label_good_signal);
                        dataConnection = true;
                    }

                }
                else
                {
                    signalDes ="WiFi";
                    dataConnection = true;
                }
                if(dataConnection){
                    if(signalStrengthValue<=-82){
                        Drawable drawable = getResources().getDrawable(R.drawable.baseline_signal_cellular_alt_black_36);
                        drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY );
                        btSignal.setCompoundDrawablesRelativeWithIntrinsicBounds(null,drawable,null,null);
                        btSignal.setText(signalDes);
                    } else {
                        btSignal.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getDrawable(R.drawable.baseline_signal_cellular_alt_black_36),null,null);
                        btSignal.setText(signalDes);
                    }

                } else {
                    {
                        btSignal.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getDrawable(R.drawable.baseline_signal_cellular_null_black_36),null,null);
                        btSignal.setText(signalDes);
                    }
                }
            }
            else
            {
                dataConnection = false;
                btSignal.setCompoundDrawablesRelativeWithIntrinsicBounds(null,getDrawable(R.drawable.baseline_signal_cellular_null_black_36),null,null);
                btSignal.setText(getString(R.string.label_no_signal));
            }
        }
    }



    @Override
    public void onBackPressed() {
        if(displayMode){
            finish();
        } else {
            if (!hasBeenSubmit) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(TaskFormActivity.this);
                dialog.setTitle("ยืนยันออกจากหน้าจอ")
                        .setMessage("ถ้าท่านยังไม่ได้บันทึกข้อมูล กรุณาทำการบันทึกข้อมูล ก่อนยืนยันการออกจากหน้าจอ")
                        .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface warning, int which) {
//                                if (AppPreference.getUserRole(TaskFormActivity.this).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
////                                    if (!taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)) {
//                                    if (!taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_REVIEW)) {
//                                        saveData(taskDataEntry.getTaskGroupCode(), false, false, false);
//                                    }
//
//                                } else if (!taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)) {
//                                    saveData(taskDataEntry.getTaskGroupCode(), false, false, false);
//                                }

                                warning.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface warning, int which) {
                                warning.dismiss();
                            }
                        })
                        .show();
            } else {
                finish();
            }
        }
    }

    private class saveTaskBackup extends AsyncTask<String,String,String >{
        private TaskDataEntry taskDataEntryBackup;

        public saveTaskBackup(TaskDataEntry taskDataEntryBackup) {
            this.taskDataEntryBackup = taskDataEntryBackup;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            WebService webService = new WebService();
            try {
                result = webService.saveTaskBackup(TaskFormActivity.this, taskDataEntryBackup);
            } catch (Exception e){
                return "1";
            }
            Log.e("saveTaskBackup","result>"+result);
            return result;
        }
    }
}
