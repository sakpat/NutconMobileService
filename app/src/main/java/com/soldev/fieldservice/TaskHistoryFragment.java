package com.soldev.fieldservice;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.soldev.fieldservice.dataentity.Personal;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskHeader;
import com.soldev.fieldservice.uiadapter.TaskListHistoryViewHolderAdapter;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.WebService;
import com.soldev.prod.mobileservice.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskHistoryFragment extends Fragment {

    private List<TaskHeader> taskHeaders;
    private View fragmentView;
    private Button btnSelectedDate;
    private RecyclerView recTaskList;
    private DatabaseReference databaseReference;
    private TaskListActivity taskListActivity;
    private List<Personal> personals;
    private FirebaseDatabase database;
    private AutoCompleteTextView edtPerformer;
    private Button btnSearch,btnReset;
    private TaskListHistoryViewHolderAdapter rvAdapter;
    private TextView txtTotalJob;
    public TaskHistoryFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskHistoryFragment newInstance(String param1, String param2) {
        TaskHistoryFragment fragment = new TaskHistoryFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentView = inflater.inflate(R.layout.fragment_task_history, container, false);
        setBinding();
        setEvent();
        return fragmentView;
    }

    private void setBinding(){
        taskListActivity = (TaskListActivity) getActivity();
        recTaskList = fragmentView.findViewById(R.id.recTaskList);
        btnReset = fragmentView.findViewById(R.id.btnReset);
        btnSearch = fragmentView.findViewById(R.id.btnSearch);
        btnSelectedDate = fragmentView.findViewById(R.id.btnSelectedDate);
        txtTotalJob = fragmentView.findViewById(R.id.txtTotalJob);
        Date toDay = Calendar.getInstance().getTime();
        String pickedDate = AppUitility.convertDateObjToString(AppUitility.formatddMMyy,toDay);
        btnSelectedDate.setText(pickedDate);
//        getTaskList("",pickedDate);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recTaskList.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST);
        edtPerformer = fragmentView.findViewById(R.id.edtPerformer);
        getUserList();
    }

    private void setEvent(){
        edtPerformer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((AutoCompleteTextView) v).showDropDown();
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String userName = getUserName(edtPerformer.getText().toString());
                Log.e("showdataselect",">>>>"+btnSelectedDate.getText().toString());
                Date selectedDate = AppUitility.convertStringToDateObj(AppUitility.formatddMMyy,btnSelectedDate.getText().toString());
                String selectedDateStr = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMdd,selectedDate);
                getTaskList("",selectedDateStr);

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPerformer.setText("");
                rvAdapter.deleteData();
                rvAdapter.notifyDataSetChanged();
            }
        });

        btnSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDate = ((Button) view).getText().toString();
                Date date = AppUitility.convertStringToDateObj(AppUitility.formatddMMyy,txtDate);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(date.getTime());
                showDateTimePicker(cal,(Button) view);
            }
        });
    }

    private void getTaskList(String username,String dateSelected){
        try {

            Query queryDataRef;
            Log.e("showdate",dateSelected);
            if(username.equalsIgnoreCase("")){
                queryDataRef = databaseReference.orderByChild("compareTaskDate").equalTo(dateSelected);
            } else {
                queryDataRef = databaseReference.orderByChild("taskPerformer").equalTo(username);
            }
//            queryDataRef.limitToFirst(5);
//            .addValueEventListener(new ValueEventListener() {
            queryDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<TaskDataEntry> listData = new ArrayList<>();
                    int countRecord = 0;
                    txtTotalJob.setText("0");
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        countRecord++;
                        TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);
                        listData.add(taskDataEntry);
                    }
                    txtTotalJob.setText(Integer.toString(countRecord));
                    Collections.sort(listData, new Comparator<TaskDataEntry>(){
                        public int compare(TaskDataEntry obj1, TaskDataEntry obj2) {
                            // ## Ascending order
                            return obj2.getCompareTaskDate().compareToIgnoreCase(obj1.getCompareTaskDate()); // To compare string values
                            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                        }
                    });
                    setTaskList(listData);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e){

        }
    }
    private void setTaskList(List<TaskDataEntry> listData){
        rvAdapter = new TaskListHistoryViewHolderAdapter(getActivity(),listData);
        rvAdapter.setOnContactClick(new TaskListHistoryViewHolderAdapter.OnContactClick() {
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
                bundle.putBoolean("DISPLAY",true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recTaskList.setAdapter(rvAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
//        getTaskList();
    }

    public void getUserList()  {
        RequestQueue requestQueue = Volley.newRequestQueue(taskListActivity);
        WebService webService = new WebService();
        String url =webService.getUserList();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                personals = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Gson gson = new Gson();
                                    String jsonString = jsonArray.getString(i);
                                    Personal personal = gson.fromJson(jsonString, Personal.class);
                                    if(!personal.isInActivated()&&!personal.getGetUserName().trim().equalsIgnoreCase("")&&personal.getUserRole().equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
                                        personals.add(personal);
//                                        Log.e("showuser",personal.getUserName()+":"+personal.getGetUserName()+":"+personal.getUserRole());
                                    }
                                }
                                Collections.sort(personals,new SortUserList());
                                String[] stringUser = new String[personals.size()];
                                for (int i = 0; i < stringUser.length ; i++) {
                                    stringUser[i] = personals.get(i).getGetUserName();
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(taskListActivity, android.R.layout.simple_list_item_1, stringUser);
//                                ArrayAdapter<Personal> adapter = new ArrayAdapter<>(taskListActivity,android.R.layout.simple_list_item_1,personals);
                                edtPerformer.setAdapter(adapter);
                            } catch (Exception e) {

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
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(taskListActivity));
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }
    }

    public class SortUserList implements Comparator<Personal> {
        @Override
        public int compare(Personal o1, Personal o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }

    private String getUserName(String userName){
        for (int i = 0; i < personals.size() ; i++) {
            if(userName.equalsIgnoreCase(personals.get(i).getGetUserName())){
                return personals.get(i).getUserName();
            }
        }
        return "";
    }
    public void showDateTimePicker(final Calendar calendar, final Button editText) {
        new DatePickerDialog(taskListActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                Date selectedDate = calendar.getTime();
                String pickedDate = AppUitility.convertDateObjToString(AppUitility.formatddMMyy,selectedDate);
                editText.setText(pickedDate);
                if(rvAdapter!=null) {
                    rvAdapter.deleteData();
                    rvAdapter.notifyDataSetChanged();
                }
//                txtTotalJob.setText(getString(R.string.label_retreiving_data));
//                getTaskList("",pickedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
    }
}
