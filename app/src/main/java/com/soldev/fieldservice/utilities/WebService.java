package com.soldev.fieldservice.utilities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.soldev.fieldservice.dataentity.TaskDataEntry;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class WebService {
    static {
        System.loadLibrary("native-lib");
    }
    public native String  geturl(String getType);
    public static String WS_URL ;
    public static String WS_AUTHEN;
    public static String WS_USER_ROLE;
    public static String WS_SERVER ;
    public static String WS_DOC_UPLOAD;
    public static String WS_DOC_STORE_BY_DOC_NO;
    public static String WS_USER_INFO;
    public static String WS_USER_INFO_WITH_NAME;
    public static String WS_CHANGE_PWD;
    public static String WS_ANDROID_VERSION;
    public static String WS_SYSPARAM;
    public static String WS_GET_ALL_USER;
    public static String WS_TASK_BACKUP;
    public WebService(){
        WS_URL = geturl("webapi");
        WS_AUTHEN = geturl("authen");
        WS_SERVER = geturl("server");
        WS_DOC_UPLOAD = geturl("docupload");
        WS_DOC_STORE_BY_DOC_NO = geturl("getdocbyno");
        WS_USER_ROLE  = geturl("userrole");
        WS_USER_INFO  = geturl("userinfo");
        WS_USER_INFO_WITH_NAME  = geturl("userinfowithname");
        WS_CHANGE_PWD = geturl("changepwd");
        WS_ANDROID_VERSION  = geturl("androidversion");
        WS_SYSPARAM  = geturl("sysparam");
        WS_GET_ALL_USER = geturl("getalluser");
        WS_TASK_BACKUP = geturl("taskbackup");
    }

    public boolean isURLReachable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnected()) {
//            try {
//                URL url = new URL(WS_SERVER);
//                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//                urlc.setConnectTimeout(10 * 1000);          // 10 s.
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
//                    Log.wtf("Connection", "Success !");
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (MalformedURLException e1) {
//                return false;
//            } catch (IOException e) {
//                return false;
//            }
//        }
//        return false;
        return true;
    }
    public String getDocUploadURL(){
        return WS_URL+WS_DOC_UPLOAD;
    }

    public String getDocByNoURL(){
        return WS_URL+WS_DOC_STORE_BY_DOC_NO;
    }

    public String getUserRoleURL(){
        return WS_URL+WS_USER_ROLE;
    }

    public String getUserInformationURL(){
        return WS_URL+WS_USER_INFO;
    }

    public String getAndroidVersionURL(){
        return WS_URL+WS_ANDROID_VERSION;
    }

    public String getSysParam(){
        return WS_URL+WS_SYSPARAM;
    }

    public String getUserList(){
        return WS_URL+WS_GET_ALL_USER;
    }

    public String getUserInformationWithNameURL(){
        return WS_URL+WS_USER_INFO_WITH_NAME;
    }

    public String getChangePWDurl(){
        return WS_URL+WS_CHANGE_PWD;
    }

    public String getTaskBackup(){
        return  WS_URL+WS_TASK_BACKUP;
    }

    private HttpURLConnection setHttpURLConnectionWithTokenPost(URL url,String token) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("authorization", "Bearer " + token);
        return conn;
    }

    public String saveTaskBackup(Context context, TaskDataEntry taskDataEntry) throws Exception{
        URL url = new URL(WS_URL+WS_TASK_BACKUP);

        String authenToken = AppPreference.getAuthenToken(context);
//            Log.i("authenToken" , authenToken);

        HttpURLConnection conn = setHttpURLConnectionWithTokenPost(url,authenToken);
        conn.setReadTimeout(20000);
        conn.setConnectTimeout(20000);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
        Gson gson = new Gson();
//            final String requestBody = new JSONObject(gson.toJson(workOrderRecord)).toString();
        final String taskObject = gson.toJson(taskDataEntry);
        JSONObject dataToSend = new JSONObject();
        String actionID = AppPreference.getUserName(context);
        Date actionDate = Calendar.getInstance().getTime();
        String actionStrDate = AppUitility.convertDateObjToString(AppUitility.formatyyyyMMddHHmm,actionDate);
        dataToSend.put("actionId",actionID+"_"+actionStrDate);
        dataToSend.put("taskNo",taskDataEntry.getTaskNo());
        dataToSend.put("compareDate",taskDataEntry.getCompareTaskDate());
        dataToSend.put("flowGroup",taskDataEntry.getTaskFlowGroup());
        dataToSend.put("taskStatus",taskDataEntry.getTaskStatus());
        dataToSend.put("taskDataObject",taskObject);
        Log.e("saveTaskBackup",dataToSend.toString());
//        writer.write(encodeParams(new JSONObject(requestBody)));
        writer.write(dataToSend.toString());
        writer.flush();
        writer.close();
        os.close();

        int responseCode=conn.getResponseCode(); // To Check for 200
        Log.e("saveTaskBackup",conn.getResponseCode()+"<>"+ HttpsURLConnection.HTTP_OK);
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            BufferedReader in=new BufferedReader( new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        }
        return "1";
    }
}
