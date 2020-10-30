package com.soldev.fieldservice;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.soldev.fieldservice.uiadapter.TaskListTabViewPagerAdapter;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.WebService;
import com.soldev.prod.mobileservice.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskListActivity extends AppCompatActivity {
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager vpPager;
    private TextView txtUserName,txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        setBinding();
    }

    private void setBinding(){
        vpPager = findViewById(R.id.vpPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentPagerAdapter = new TaskListTabViewPagerAdapter(TaskListActivity.this,fragmentManager);
        vpPager.setAdapter(fragmentPagerAdapter);
        txtUserName = findViewById(R.id.txtUserName);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(vpPager);
        findViewById(R.id.btSave).setVisibility(View.GONE);
        findViewById(R.id.btSubmit).setVisibility(View.GONE);
        findViewById(R.id.btCancel).setVisibility(View.GONE);
        findViewById(R.id.btSignal).setVisibility(View.GONE);
        String userInpf = getString(R.string.label_user_name)+":"+ AppPreference.getUserFullName(TaskListActivity.this);
        txtUserName.setText(userInpf);
        txtUserName.setVisibility(View.VISIBLE);
        txtVersion = findViewById(R.id.txtVersion);
        txtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePassword();
            }
        });
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            txtVersion.setText("Version "+version);
        } catch (PackageManager.NameNotFoundException e) {
            txtVersion.setText("");
            e.printStackTrace();
        }
        try {
            if(checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED) {
//                if (!isMyServiceRunning(MessageReceiver.class)) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        startForegroundService(new Intent(getApplicationContext(), MessageReceiver.class));
//                    } else {
//                        startService(new Intent(getApplicationContext(), MessageReceiver.class));
//                    }
//                    Log.e("service", "starting");
//
//                } else {
//                    Log.e("service", "service is running");
//                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
                builder.setTitle("เชื่อมต่อระบบข้อความ").setMessage("ไม่สามารถเชื่อมต่อได้เนื่องจากไม่ได้รับการอนุญาติจากผู้ใช้งาน")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        } catch (Exception e){
            Log.e("service", "error:"+e.toString());
        }
    }
    public void dialogChangePassword(){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
        final View dialogView = View.inflate(TaskListActivity.this, R.layout.dialog_change_pwd,null);
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
                String oldPassword = ((EditText)view.getRootView().findViewById(R.id.edtOldPassword)).getText().toString();
                String newPassword = ((EditText)view.getRootView().findViewById(R.id.edtNewPassword)).getText().toString();
                String newPasswordConfirm = ((EditText)view.getRootView().findViewById(R.id.edtConfirmNewPassword)).getText().toString();
                changePassword(oldPassword,newPassword,newPasswordConfirm);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void changePassword(String oldPassword, String newPassword,String newPasswordConfirm){
        String errorText = "";
        if(!oldPassword.equalsIgnoreCase(AppPreference.getUserPAssword(TaskListActivity.this))){
            errorText = getString(R.string.label_old_password_not_correct_error);
        }
        if(oldPassword.equalsIgnoreCase(newPassword)){
            if(errorText.trim().equalsIgnoreCase("")){
                errorText = getString(R.string.label_old_password_eq_new_error);
            } else {
                errorText = errorText +"."+getString(R.string.label_old_password_eq_new_error);
            }
        }
        if(!newPassword.equals(newPasswordConfirm)){
            if(errorText.trim().equalsIgnoreCase("")){
                errorText = getString(R.string.label_new_password_confirm_error);
            } else {
                errorText = errorText +"."+getString(R.string.label_new_password_confirm_error);
            }
        }

        if(newPassword.trim().length()<4){
            if(errorText.trim().equalsIgnoreCase("")){
                errorText = getString(R.string.label_new_password_empty_error);
            } else {
                errorText = errorText +"."+getString(R.string.label_new_password_empty_error);
            }
        }

        if(errorText.trim().length()==0){
            submitChangePasssword(oldPassword,newPassword);
        } else {
            Toast.makeText(TaskListActivity.this,errorText,Toast.LENGTH_LONG).show();
        }
    }

    public void submitChangePasssword(final String oldPassword, final String newPassword)  {
        WebService webService = new WebService();
        RequestQueue requestQueue = Volley.newRequestQueue(TaskListActivity.this);
        String authenUrl = webService.WS_URL + webService.WS_CHANGE_PWD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, authenUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("showchangepwd",response);
                        } catch (Exception e) {
                            Log.e("showchangepwd","error>"+e.toString());
//                            Toast.makeText(MainActivity.this, getString(R.string.message_offline), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("authorization", "Bearer " + AppPreference.getAuthenToken(TaskListActivity.this));
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("oldPassword", oldPassword);
                    jsonObject.put("newPassword", newPassword);
                } catch (Exception e) {

                } // put your json
                return jsonObject.toString().getBytes();
            }
        };
        requestQueue.add(stringRequest);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
        builder.setTitle("ยืนยัน")
                .setMessage("คุณต้องการออกจากระบบใช่หรือไม่")
                .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(getString(R.string.label_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).create();
        builder.show();

    }
}