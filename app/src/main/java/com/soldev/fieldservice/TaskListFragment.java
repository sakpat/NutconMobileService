package com.soldev.fieldservice;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskHeader;
import com.soldev.fieldservice.uiadapter.TaskListViewHolderAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.SQLiteHelperUtility;
import com.soldev.prod.mobileservice.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<TaskHeader> taskHeaders;
    private View fragmentView;
    private RecyclerView recTaskList;
    private DatabaseReference databaseReference;
    private TaskListActivity taskListActivity;
    private ImageButton btnBack,btnNext;
    private FirebaseDatabase database;
    private List<TaskDataEntry> listData;
    private ArrayList<TaskDataEntry> listDataPerform,listDataDeliver,listDataQC;
    private Date startDate,endDate;
    private TextView txtDateToShow;
    private ProgressDialog p;
    public TaskListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        endDate = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-5);
        startDate = calendar.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_task_list, container, false);
        setBinding();
//        testData();
//        getTaskList();
        getTaskListByCompareDate();
        return fragmentView;
    }

    private void setBinding(){
        taskListActivity = (TaskListActivity) getActivity();
        recTaskList = fragmentView.findViewById(R.id.recTaskList);
        txtDateToShow = fragmentView.findViewById(R.id.txtDateToShow);
        btnBack = fragmentView.findViewById(R.id.btnBack);
        btnNext = fragmentView.findViewById(R.id.btnNext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recTaskList.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DATE,-7);
                startDate = c.getTime();
                c.setTime(endDate);
                c.add(Calendar.DATE,-7);
                endDate = c.getTime();
                getTaskListByCompareDate();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DATE,+7);
                startDate = c.getTime();
                c.setTime(endDate);
                c.add(Calendar.DATE,+7);
                endDate = c.getTime();
                getTaskListByCompareDate();
            }
        });
    }

    private void getTaskListByCompareDate(){
        showDataListFromDB();
        if(AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            databaseReference = database.getReference().child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST);
            Log.e("showdataxtype","Mobile");
        } else {
            databaseReference = database.getReference().child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_QC_LIST);
            Log.e("showdataxtype","qc");
        }
        try {

            Query queryDataRef;
            String userName = AppPreference.getUserName(taskListActivity);
            String startStrTime,endStrTime;
            txtDateToShow.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyy,startDate)+"-"+AppUitility.convertDateObjToString(AppUitility.formatddMMyy,endDate));
            startStrTime = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMdd,startDate);
            endStrTime = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMdd,endDate);
            queryDataRef = databaseReference.orderByChild("compareTaskDate").startAt(startStrTime).endAt(endStrTime);

//            .addValueEventListener(new ValueEventListener() {
            queryDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listDataPerform = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        try {
                            TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);

                            if (taskDataEntry != null && taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
//                                    && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)
                                    && !taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)
                                    && (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity)) ||
                                    taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity)))) {
                                taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
                                listDataPerform.add(taskDataEntry);
                            }
                            else if (taskDataEntry != null && taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
                                    && taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)) {
                                if (taskDataEntry.getTaskDeliver() != null
                                        && taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
//                                        && taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)) {

//                                    taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
                                    listDataPerform.add(taskDataEntry);
                                }
                            }
                            else if (taskDataEntry != null
//                                    && !taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)
                            &&AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)) {
//                            Log.e("showtask","qc:"+taskDataEntry.getTaskNo()+"  "+taskDataEntry.getWorkerQC()+":"+AppPreference.getUserName(taskListActivity));
                                if (taskDataEntry.getWorkerQC() != null
                                        && taskDataEntry.getWorkerQC().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                        //&& taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_NEW)
                                ) {
                                    taskDataEntry.setTaskScreenType(AppConstant.USER_QC_ROLE);
                                    listDataPerform.add(taskDataEntry);
                                }
                            } else if(taskDataEntry!=null && !taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)){
                                listDataPerform.add(taskDataEntry);
                            }
                        } catch (Exception e){
                            Log.e("error","getdatafrom firebase:"+data.toString());
                        }
//                        listDataPerform.add(taskDataEntry);
                    }
                    showListDataByCompareDate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("TaskListFragment","firebase error:"+databaseError.toString());
                }

            });
        } catch (Exception e){
            Log.e("TaskListFragment","getTaskList error:"+e.toString());
        }
    }

//    private void getTaskList(){
//        try {
//
//            Query queryDataRef;
//            String userName = AppPreference.getUserName(taskListActivity);
//            queryDataRef = databaseReference.orderByChild("taskPerformer").equalTo(userName);
//
////            .addValueEventListener(new ValueEventListener() {
//            queryDataRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    listDataPerform = new ArrayList<>();
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);
////                        Log.e("showdata",taskDataEntry.getTaskNo());
//                        if(taskDataEntry!=null&&taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
//                                &&!taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)
//                                &&!taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_REASSIGN)) {
//                            taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
//                            listDataPerform.add(taskDataEntry);
//                        }
////                        if(taskDataEntry!=null&&taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE) ) {
////                            if (taskDataEntry.getDeliveryStatus() == null || taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_NEW)) { //case ปกติ
////                                if (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
////                                        &&!taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_REASSIGN)  ) {
////                                    taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
////                                    listData.add(taskDataEntry);
////                                }
////                            } else if(taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_REDELIVER)&& //case ส่งงานซ้ำ
////                                    taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))){
////                                taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
////                                listData.add(taskDataEntry);
////                            }
////
////                        }
//                    }
//                    getQCTaskList();
////                    Collections.sort(listData, new Comparator<TaskDataEntry>(){
////                        public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
////                            // ## Ascending order
////                            return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values
////                            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
////
////                            // ## Descending order
////                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
////                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
////                        }
////                    });
////                    setTaskList(listData);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception e){
////            Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_LONG).show();
//            Log.e("TaskListFragment","getTaskList error:"+e.toString());
//        }
//    }

//    private void getQCTaskList(){
//        try {
//            Query queryDataRef;
//
//            queryDataRef = databaseReference.orderByChild("workerQC").equalTo(AppPreference.getUserName(taskListActivity));
//
////            .addValueEventListener(new ValueEventListener() {
//            queryDataRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    listDataQC = new ArrayList<>();
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);
//                        if(taskDataEntry!=null && !taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)) {
////                            Log.e("showtask","qc:"+taskDataEntry.getTaskNo()+"  "+taskDataEntry.getWorkerQC()+":"+AppPreference.getUserName(taskListActivity));
//                            if (taskDataEntry.getWorkerQC()!=null&&taskDataEntry.getWorkerQC().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))) {
//
//                                taskDataEntry.setTaskScreenType(AppConstant.USER_QC_ROLE);
//                                listDataQC.add(taskDataEntry);
//                            }
//                        }
//                    }
////                    Collections.sort(listDataQC, new Comparator<TaskDataEntry>(){
////                        public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
////                            // ## Ascending order
////                            return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values
////                            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
////
////                            // ## Descending order
////                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
////                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
////                        }
////                    });
//
//                    getDeliveryTaskList();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception e){
////            Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_LONG).show();
//            Log.e("TaskListFragment","getTaskList error:"+e.toString());
//        }
//    }

//    private void getDeliveryTaskList(){
//        try {
//
//            Query queryDataRef;
//
//            queryDataRef = databaseReference.orderByChild("taskDeliver").equalTo(AppPreference.getUserName(taskListActivity));
//
////            .addValueEventListener(new ValueEventListener() {
//            queryDataRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    listDataDeliver = new ArrayList<>();
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);
//                        if(taskDataEntry!=null&&taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
//                                && taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)) {
//                            if (taskDataEntry.getTaskDeliver()!=null&&taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))) {
//
//                                taskDataEntry.setTaskScreenType(AppConstant.USER_MOBILE_ROLE);
//                                listDataDeliver.add(taskDataEntry);
//                            }
//                        }
//                    }
////                    Collections.sort(listData, new Comparator<TaskDataEntry>(){
////                        public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
////                            // ## Ascending order
////                            return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values
////                            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
////
////                            // ## Descending order
////                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
////                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
////                        }
////                    });
////                    setTaskList(listData);
//                    showListData();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception e){
////            Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_LONG).show();
//            Log.e("TaskListFragment","getTaskList error:"+e.toString());
//        }
//    }


//    private void showListData(){
//        listData = new ArrayList<>();
//        listData.addAll(listDataPerform);
//        listData.addAll(listDataQC);
//        listData.addAll(listDataDeliver);
//        Collections.sort(listData, new Comparator<TaskDataEntry>(){
//            public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
//                // ## Ascending order
//                return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values
//                // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
//
//                // ## Descending order
//                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
//                // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
//            }
//        });
//        setTaskList(listData);
//
//    }

    private void showListDataByCompareDate(){
        listData = new ArrayList<>();
        listData.addAll(listDataPerform);
        Collections.sort(listData, new Comparator<TaskDataEntry>(){
            public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
                return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values

            }
        });
        setTaskList(listData);

    }

    private void setTaskList(List<TaskDataEntry> listData){

        //add data to db
        new DBLocalData(listData).execute();

    }

    private List<TaskHeader> testData(){
        List<TaskHeader> returnData = new ArrayList<>();
        for (int i = 1; i < 9 ; i++) {
            String status= AppConstant.TASK_STATUS_NEW;
            String taskGroupCode="1";
            String taskGroup="งานปั๊ม";
            String taskTypeCode="101";
            String taskType="งานปั๊มลาก";
            String location="ธรรมศาสตร์";
            double tempLat=99.3986862,templon=18.3170581;
            if(i>2){
                location = "Thai Oil Public Co., Ltd.";
                tempLat = 100.915983;
                templon = 13.111837;
                taskGroupCode="2";
                taskGroup="งานปาด";
                taskTypeCode="203";
                taskType="งานปาดเลเซอร์";
            }
            if(i>5){
                location = "ธนาคารกรุงศรี";
                tempLat = 100.915983;
                templon = 13.111837;
                taskGroupCode="5";
                taskGroup="งานเอกสาร";
                taskTypeCode="501";
                taskType="งานรรับเอกสาร";
            }
            TaskHeader taskHeader = new TaskHeader("200100"+Integer.toString(i),
                    "200100"+i,
                    "07/01/2020",
                    "",
                    taskTypeCode,
                    taskType,
                    taskGroupCode,
                    taskGroup,
                    "Customer: 00"+i,
                    "+662 900 5555",
                    location,
                    status,
                    "admin",
                    "Worker1",
                    tempLat,
                    templon,
                    AppConstant.TASK_FLOW_GROUP_MOBILE);
            databaseReference.child("200100"+Integer.toString(i)).setValue(taskHeader);
            returnData.add(taskHeader);
        }
        return  returnData;
    }

    @Override
    public void onResume() {
        showDataListFromDB();
        super.onResume();

    }
    private class DBLocalData extends AsyncTask<String, String, String> {
        private List<TaskDataEntry> listData;
        public DBLocalData(List<TaskDataEntry> listData) {
            this.listData = listData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            p = new ProgressDialog(taskListActivity);
//            p.setMessage("Please wait...It is downloading");
//            p.setIndeterminate(false);
//            p.setCancelable(false);
//            p.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(taskListActivity);
            SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
            if(listData.size()>0) {
                try {
                    sqLiteDatabase.beginTransaction();
                    for (int i = 0; i < this.listData.size(); i++) {
                        TaskDataEntry taskDataEntry = this.listData.get(i);

//                    if (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
//                            || taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))) {
                        String queryData = "select * from " + SQLiteHelperUtility.TB_TASKLIST + " where " + SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
//                        Log.e("updateLocalData", "taskno:" + taskDataEntry.getTaskNo()+":"+taskDataEntry.getDeliveryStatus());
                        Cursor dataCursor = sqLiteDatabase.rawQuery(queryData, null);
                        dataCursor.moveToFirst();
                        boolean isDBWorkSucess = false;
                        if (dataCursor.getCount() == 0) {
                            if(AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                                if (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                        || taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))) {
                                    Gson gson = new Gson();
                                    String jsonData = gson.toJson(taskDataEntry);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    if (sqLiteDatabase.insert(SQLiteHelperUtility.TB_TASKLIST, null, contentValues) > 0) {
                                        isDBWorkSucess = true;
                                    }
                                }
                            } else if(taskDataEntry.getWorkerQC().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                            &&!taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)){
                                Gson gson = new Gson();
                                String jsonData = gson.toJson(taskDataEntry);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskDataEntry.getTaskNo());
                                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                if (sqLiteDatabase.insert(SQLiteHelperUtility.TB_TASKLIST, null, contentValues) > 0) {
                                    isDBWorkSucess = true;
                                }
                            }

                        } else {
                            //update data
                            if(AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {

                                Gson gson = new Gson();
                                String jsonData = gson.toJson(taskDataEntry);
                                TaskDataEntry taskDataEntryExisting = new Gson().fromJson(dataCursor.getString(dataCursor.getColumnIndex(SQLiteHelperUtility.PERFORMTASKDATA)), TaskDataEntry.class);
                                if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_REVIEW)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)) {
//                                ContentValues contentValues = new ContentValues();
//                                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskToUpdate.getTaskNo());
//                                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
//                                int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST,contentValues,SQLiteHelperUtility.PERFORMTASKID+"='"+taskDataEntry.getTaskNo()+"'",null);
                                    String whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                    sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);

                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)
                                        && taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_ASSGINER)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_REDELIVER)) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

//                                Log.e("updateLocalData", "no case"+taskToUpdate.getTaskNo());
                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_ASSGINER)
                                ) {
                                    String whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                    sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);

                                } else if (!taskDataEntryExisting.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE) &&
                                        taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE) &&
                                        (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                                || taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                        )) {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

                                } else {
                                    Log.e("showdatax", "nothing");
                                }
                            } else if(AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_QC_ROLE)) {

                                Gson gson = new Gson();
                                String jsonData = gson.toJson(taskDataEntry);
                                TaskDataEntry taskDataEntryExisting = new Gson().fromJson(dataCursor.getString(dataCursor.getColumnIndex(SQLiteHelperUtility.PERFORMTASKDATA)), TaskDataEntry.class);
                                if ((taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_REVIEW)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE))||taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE)) {
//                                ContentValues contentValues = new ContentValues();
//                                contentValues.put(SQLiteHelperUtility.PERFORMTASKID, taskToUpdate.getTaskNo());
//                                contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
//                                int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST,contentValues,SQLiteHelperUtility.PERFORMTASKID+"='"+taskDataEntry.getTaskNo()+"'",null);
                                    String whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                    sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);

                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_MOBILE)
                                        && taskDataEntry.getDeliveryStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PENDING)) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_ASSGINER)
                                        && taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_REDELIVER)) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

                                } else if (taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_ASSGINER)) {
                                    String whereClause = SQLiteHelperUtility.PERFORMTASKID + " = '" + taskDataEntry.getTaskNo() + "'";
                                    sqLiteDatabase.delete(SQLiteHelperUtility.TB_TASKLIST, whereClause, null);

                                } else if (!taskDataEntryExisting.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE) &&
                                        taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE) &&
                                        (taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                                || taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))
                                        )) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SQLiteHelperUtility.PERFORMTASKDATA, jsonData);
                                    int recordcount = sqLiteDatabase.update(SQLiteHelperUtility.TB_TASKLIST, contentValues, SQLiteHelperUtility.PERFORMTASKID + "='" + taskDataEntry.getTaskNo() + "'", null);

                                } else {
                                    Log.e("showdataxqcjob", "nothing");
                                }
                            }
                            //continue here
                        }
                    }
//                }
                    sqLiteDatabase.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.e("showdatax", "erroe>" + e.toString());
                } finally {
                    sqLiteDatabase.endTransaction();
                }
            }
            return "";
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            showDataListFromDB();
//            if(p.isShowing()){
//                p.dismiss();
//            }

        }
    }

    private List<TaskDataEntry> getTaskListFromDB(){
        List<TaskDataEntry> taskDataEntries = new ArrayList<>();
        SQLiteHelperUtility sqLiteHelperUtility = new SQLiteHelperUtility(taskListActivity);
        SQLiteDatabase sqLiteDatabase = sqLiteHelperUtility.getWritableDatabase();
        String queryData = "select * from "+SQLiteHelperUtility.TB_TASKLIST;
        Cursor dataCursor = sqLiteDatabase.rawQuery(queryData,null);
        Log.e("showdata","size>"+dataCursor.getCount());
        if(dataCursor.getCount()>0){
            dataCursor.moveToFirst();
            for (int i = 0; i < dataCursor.getCount(); i++) {
                String jsonData = dataCursor.getString(dataCursor.getColumnIndex(SQLiteHelperUtility.PERFORMTASKDATA));
                TaskDataEntry taskDataEntry = new Gson().fromJson(jsonData,TaskDataEntry.class);
                String startStrTime = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMdd,startDate);
                String endStrTime = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMdd,endDate);
                String dateForCompare = taskDataEntry.getCompareTaskDate();
//                Log.e("showdata",taskDataEntry.getTaskNo()+":"+startStrTime+","+dateForCompare+","+endStrTime);
                if((dateForCompare.compareTo(startStrTime)>=0)&&(dateForCompare.compareTo(endStrTime)<=0)){
//                    Log.e("showdata",taskDataEntry.getTaskNo()+":"+taskDataEntry.getTaskPerformer()+","+taskDataEntry.getTaskDeliver()+"<>"+taskDataEntry.getTaskFlowGroup());
                    if(AppPreference.getUserRole(taskListActivity).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                        if((taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))||
                                taskDataEntry.getTaskDeliver().equalsIgnoreCase(AppPreference.getUserName(taskListActivity)))
                                &&taskDataEntry.getTaskFlowGroup().equalsIgnoreCase(AppConstant.TASK_FLOW_GROUP_MOBILE)

                        ) {
//                            Log.e("showdata","at list:"+taskDataEntry.getTaskNo()+":"+taskDataEntry.getTaskFlowGroup()+":"+taskDataEntry.getTaskStatus());
                            taskDataEntries.add(taskDataEntry);
                        }
                    } else
//                        if(!taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_DONE))
                        {

                        if(taskDataEntry.getWorkerQC().equalsIgnoreCase(AppPreference.getUserName(taskListActivity))) {
//                            Log.e("showdataqc","at list qc:"+taskDataEntry.getTaskNo()+","+taskDataEntry.getWorkerQC());
                            taskDataEntries.add(taskDataEntry);
                        }
                    }
                }


                dataCursor.moveToNext();
            }

        }
        return taskDataEntries;
    }

    private void showDataListFromDB(){
        List<TaskDataEntry> taskDataEntries= getTaskListFromDB();
//        Log.e("showdata","records>"+taskDataEntries.size());
        if(taskDataEntries!=null){
            TaskListViewHolderAdapter adapter = new TaskListViewHolderAdapter(getActivity(),taskDataEntries);
            //emnd
            adapter.setOnContactClick(new TaskListViewHolderAdapter.OnContactClick() {
                @Override
                public void onContactClick(int position, TaskDataEntry taskDataEntry) {
                    //You will receive event when clicking item in list
                    Gson gson = new Gson();
                    String json = gson.toJson(taskDataEntry);
                    Intent intent = new Intent(getActivity(), TaskFormActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TASKNO",taskDataEntry.getTaskNo());
                    bundle.putString("DATA",json);
                    bundle.putString("FORMTYPE", AppPreference.getUserRole(taskListActivity));
                    bundle.putBoolean("DISPLAY",false);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            recTaskList.setAdapter(adapter);
        }
    }


}
