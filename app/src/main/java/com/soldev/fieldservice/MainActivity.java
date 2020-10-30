package com.soldev.fieldservice;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.WebService;
import com.soldev.prod.mobileservice.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin,btnUnregister;
    private CheckBox chkRememberMe;
    private TextView txtVersion;
    private com.google.android.material.textfield.TextInputEditText edtUserName,edtPassword;
    private String versionNumber;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = pInfo.versionName;
            ((TextView) findViewById(R.id.txtAppVersion)).setText(getString(R.string.label_version)+versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setBinding();
//        new GenerateData().generateEquipmentMasterTemplate();
//        new GenerateData().createControlDate();
//        new GenerateData().clearAllTask();
    }

    private void setBinding(){
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        btnUnregister = findViewById(R.id.btnUnregister);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        chkRememberMe  = findViewById(R.id.chkRememberMe);




        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.isRemberMe(MainActivity.this)) {
            edtUserName.setText(AppPreference.getUserName(MainActivity.this));
            edtPassword.setText(AppPreference.getUserPAssword(MainActivity.this));
            chkRememberMe.setChecked(true);
            edtUserName.setEnabled(false);
            btnUnregister.setVisibility(View.VISIBLE);
        } else if(AppPreference.getUserName(MainActivity.this).equalsIgnoreCase("")){
            edtUserName.setText("");
            edtPassword.setText("");
            chkRememberMe.setChecked(false);
            btnUnregister.setVisibility(View.GONE);
        } else {
            edtUserName.setText(AppPreference.getUserName(MainActivity.this));
            edtUserName.setEnabled(false);
            edtUserName.setText(AppPreference.getUserName(MainActivity.this));
            edtPassword.setText("");
            chkRememberMe.setChecked(false);
            btnUnregister.setVisibility(View.VISIBLE);
        }
    }

    private void setEvent(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUserName.getText().toString().trim().equalsIgnoreCase("") ||
                        edtPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Login is not correct, please try again", Toast.LENGTH_LONG).show();
                } else {
                    WebService webService = new WebService();
                    checkAuthentication(edtUserName.getText().toString(), edtPassword.getText().toString());
//                    authenFortest();
                    btnLogin.setEnabled(false);
                    btnLogin.setText("Processing....");
                }
            }
        });
        btnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("ยืนยันออกจากระบบ")
                        .setMessage("แอพจะทำการลบข้อมูลบนเครื่องมือถือนี้ทั้งหมด")
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtUserName.setEnabled(true);
                        edtUserName.setText("");
                        edtPassword.setText("");
                        chkRememberMe.setChecked(false);
                        AppPreference.saveUserName(MainActivity.this,"");
                        AppPreference.saveUserPassword(MainActivity.this,"");
                        AppPreference.setRememberMe(MainActivity.this,false);
                        btnUnregister.setVisibility(View.GONE);
                    }
                }).show();

            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */



    @Override
    protected void onStart() {
        super.onStart();
        if ( Build.VERSION.SDK_INT >= 23)
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstant.REQUEST_CODE_READ_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, AppConstant.REQUEST_CODE_CAMERA);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstant.REQUEST_FINE_LOCATION);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstant.REQUEST_COARSE_LOCATION);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, AppConstant.REQUEST_COARSE_LOCATION);
            }
        currentUser = mAuth.getCurrentUser();
        Log.e("signInAnonymously","strart");
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void checkAuthentication(final String user, final String password){
        WebService webService = new WebService();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String authenUrl = webService.WS_URL + webService.WS_AUTHEN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, authenUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
//                            Log.e("response",response);
                            AppPreference.saveAuthenToken(MainActivity.this, responseJson.getString("data"));
                            if(chkRememberMe.isChecked()){
                                AppPreference.setRememberMe(MainActivity.this,true);
                                AppPreference.saveUserName(MainActivity.this, user);
                                AppPreference.saveUserPassword(MainActivity.this, password);
                            } else {
                                AppPreference.setRememberMe(MainActivity.this,false);
                                AppPreference.saveUserName(MainActivity.this, user);
                            }
                            if (responseJson.getBoolean("success")) {
                                getUserInformation();
                                getSysparam();
                            }  else {
                                Toast.makeText(MainActivity.this, getString(R.string.message_incorrect_login), Toast.LENGTH_LONG).show();

                                btnLogin.setEnabled(true);
                                btnLogin.setText("Login");
                            }
                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, getString(R.string.message_offline), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(edtUserName.getText().toString().equals(AppPreference.getUserName(MainActivity.this)) &&
                                edtPassword.getText().toString().equals(AppPreference.getUserPAssword(MainActivity.this)) &&
                                AppPreference.getAuthenToken(MainActivity.this).length()>10){
                            if(chkRememberMe.isChecked()){
                                AppPreference.setRememberMe(MainActivity.this,true);
                                AppPreference.saveUserName(MainActivity.this, user);
                                AppPreference.saveUserPassword(MainActivity.this, password);
                            } else {
                                AppPreference.setRememberMe(MainActivity.this,false);
                                AppPreference.saveUserPassword(MainActivity.this, "");
                            }
//                            btnLogin.setEnabled(true);
//                            btnLogin.setText("Login");
//                            Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
//                            startActivity(intent);
                            getAndroidVersion();
//                            Toast.makeText(MainActivity.this,getString(R.string.message_offline),Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.message_incorrect_login), Toast.LENGTH_LONG).show();
                            btnLogin.setEnabled(true);
                            btnLogin.setText("Login");
                        }
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userName", user);
                    jsonObject.put("password", password);
                } catch (Exception e) {

                } // put your json
                return jsonObject.toString().getBytes();
            }
        };
        requestQueue.add(stringRequest);
//        requestQueue.start();
    }

    private void authenFortest(){
        AppPreference.saveUserName(MainActivity.this, "tuan");
        AppPreference.setUserRole(MainActivity.this, AppConstant.USER_MOBILE_ROLE);
//        Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
//        startActivity(intent);
        getAndroidVersion();
    }

    public void getUserInformation()  {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        WebService webService = new WebService();
        String url =webService.getUserInformationURL();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
//                                getRole();
                                getUserFullInfor(jsonObject.getString("id"));
                            } catch (Exception e) {
                                Log.e("savedata","error>"+e.toString());
                                btnLogin.setEnabled(true);
                                btnLogin.setText("Login");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Login");
//                recordWorkOrder();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(MainActivity.this));
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }

    }

    public void getUserFullInfor(String userID)  {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        WebService webService = new WebService();
        String url =webService.getUserInformationWithNameURL()+userID;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String userName = jsonObject.getString("prefix")+" "+
                                        jsonObject.getString("firstName")+" "+
                                        jsonObject.getString("lastName");
                                if(!chkRememberMe.isChecked()) {
                                    AppPreference.setRememberMe(MainActivity.this,false);
                                }
                                AppPreference.setUserFullName(MainActivity.this,userName);
                                AppPreference.setUserRole(MainActivity.this,jsonObject.getString("userRole"));
//                                btnLogin.setEnabled(true);
//                                btnLogin.setText("Login");
//                                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
//                                startActivity(intent);
                                getAndroidVersion();
                            } catch (Exception e) {
                                Log.e("savedata","error>"+e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(MainActivity.this));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }
    }

    private void showTaskList(boolean isVerified){
        if(isVerified) {
            btnLogin.setEnabled(true);
            btnLogin.setText("Login");
            Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("อัพเดทแอพ")
                    .setMessage(getString(R.string.message_update_app))
                    .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });
            builder.show();
        }
    }

    private void showTaskListOffline(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ไม่สามารถเตรวจสอบเวอร์ชั่น")
                .setMessage(getString(R.string.message_offline))
                .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showTaskList(true);
                    }
                }).setNegativeButton(getString(R.string.label_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public void getAndroidVersion()  {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        WebService webService = new WebService();
        String url =webService.getAndroidVersionURL();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getBoolean("success")) {
                                    double newVersionNumber = Double.parseDouble(jsonObject.getString("versionNumber"));
                                    double thisAppVersionNumber = Double.parseDouble(versionNumber);
                                    if(thisAppVersionNumber<newVersionNumber){
                                        showTaskList(false);
                                    } else {
                                        showTaskList(true);
                                    }
                                } else {
                                    Log.e("showverion", "errod:"+response);
                                }
                            } catch (Exception e) {
                                Log.e("showverion", "exception error:"+e.toString());
                                showTaskList(false);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Login");
                    showTaskListOffline();
//                recordWorkOrder();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(MainActivity.this));
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }

    }
    public void getSysparam()  {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        WebService webService = new WebService();
        String url =webService.getSysParam();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getBoolean("success")) {
                                    Log.e("showparam", "success:"+response);
                                    String systemParam = jsonObject.getString("systemParameter");
                                    String[] paremeters = systemParam.split(",");
                                    if(paremeters[0].equalsIgnoreCase("Y")){
                                        AppPreference.setPrefLockWorkDate(MainActivity.this,true);
                                    } else {
                                        AppPreference.setPrefLockWorkDate(MainActivity.this,false);
                                    }
                                    boolean paramLockEndDay = false;
                                    if(paremeters.length==1){
                                        paramLockEndDay = AppPreference.isLockWorkDate(MainActivity.this);
                                    } else {
                                        if (paremeters[1].equalsIgnoreCase("Y")) {
                                            paramLockEndDay = true;
                                        } else {
                                            paramLockEndDay = false;
                                        }
                                    }
                                    AppPreference.setPrefLockWorkEndDate(MainActivity.this,paramLockEndDay);

                                } else {
                                    Log.e("showparam", "errod:"+response);
                                }
                            } catch (Exception e) {
                                Log.e("showparam", "exception error:"+e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(MainActivity.this));
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }

    }
}