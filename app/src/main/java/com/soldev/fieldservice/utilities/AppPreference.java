package com.soldev.fieldservice.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static String PREF_NAME = "apppref";
    private static String PREF_LANGUAGE = "displaylanguage";
    private static String PREF_USER_NAME = "username";
    private static String PREF_USER_PASSWORD = "userpassword";
    private static String PREF_USER_ROLE = "userrole";
    private static String PREF_AUTHEN_ALWAYS_REMEMBER = "rememberme";
    private static String PREF_AUTHEN_TOKEN = "authentoken";
    private static String PREF_MAC_ADDRESS_BLUETOOTH = "bluetooth";
    private static String PREF_USER_FULLNAME = "fullname";
    private static String PREF_LOCK_WORK_DATE = "LOCKWORKDATE";
    private static String PREF_LOCK_WORK_END_DATE = "LOCKWORKENDDATE";
    private static String PREF_APP_DEV_MODE = "DEVMODE";

    private static SharedPreferences getPrefs(Context context)
    {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    public static void saveUserName(Context context, String username){
        getPrefs(context).edit().putString(PREF_USER_NAME, username).commit();
    }


    public static String getUserName(Context context){
        return getPrefs(context).getString(PREF_USER_NAME,"");
    }

    public static void saveUserPassword(Context context, String password){
        getPrefs(context).edit().putString(PREF_USER_PASSWORD, password).commit();
    }

    public static String getUserPAssword(Context context){
        return getPrefs(context).getString(PREF_USER_PASSWORD,"");
    }

    public static void saveAuthenToken(Context context, String token){
        getPrefs(context).edit().putString(PREF_AUTHEN_TOKEN, token).commit();
    }

    public static String getAuthenToken(Context context){
        return getPrefs(context).getString(PREF_AUTHEN_TOKEN,"");
    }

    public static void setRememberMe(Context context, Boolean remember){
        getPrefs(context).edit().putBoolean(PREF_AUTHEN_ALWAYS_REMEMBER, remember).commit();
    }

    public static Boolean isRemberMe(Context context){
        return getPrefs(context).getBoolean(PREF_AUTHEN_ALWAYS_REMEMBER,false);
    }

    public static void setUserRole(Context context, String userRole){
        getPrefs(context).edit().putString(PREF_USER_ROLE, userRole).commit();
    }

    public static String getUserRole(Context context){
        return getPrefs(context).getString(PREF_USER_ROLE,"");
    }

    public static String getPrefMacAddressBluetooth(Context context) {
        return getPrefs(context).getString(PREF_MAC_ADDRESS_BLUETOOTH,"");
    }

    public static void setPrefMacAddressBluetooth(Context context, String macAddress) {
        getPrefs(context).edit().putString(PREF_MAC_ADDRESS_BLUETOOTH, macAddress).commit();
    }

    public static String getUserFullName(Context context) {
        return getPrefs(context).getString(PREF_USER_FULLNAME,"");
    }

    public static void setUserFullName(Context context, String userFullname) {
        getPrefs(context).edit().putString(PREF_USER_FULLNAME, userFullname).commit();
    }

    public static boolean isLockWorkDate(Context context) {
        return getPrefs(context).getBoolean(PREF_LOCK_WORK_DATE,false);
    }

    public static void setPrefLockWorkDate(Context context, boolean lockDate) {
        getPrefs(context).edit().putBoolean(PREF_LOCK_WORK_DATE, lockDate).commit();
    }
    public static boolean isLockWorkEndDate(Context context) {
        return getPrefs(context).getBoolean(PREF_LOCK_WORK_END_DATE,false); // if no data use as workdate
    }

    public static void setPrefLockWorkEndDate(Context context, boolean lockDate) {
        getPrefs(context).edit().putBoolean(PREF_LOCK_WORK_END_DATE, lockDate).commit();
    }

    public static boolean isDevMode(Context context) {
        return getPrefs(context).getBoolean(PREF_APP_DEV_MODE,false); // if no data use as workdate
    }

    public static void setDevMode(Context context, boolean lockDate) {
        getPrefs(context).edit().putBoolean(PREF_APP_DEV_MODE, lockDate).commit();
    }
}
