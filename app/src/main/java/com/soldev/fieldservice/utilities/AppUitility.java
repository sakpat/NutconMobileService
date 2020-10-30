package com.soldev.fieldservice.utilities;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUitility {

    public static String formatddMMyyhhmmss = "dd/MM/yyyy HH:mm";
    public static String formatyyyyMMdd = "yyyyMMdd";
    public static String formatyyyyMMddHHmm = "yyyyMMddHHmm";
    public static String formatddMMyy = "dd/MM/yyyy";

    public  static void setText(View view,Integer viewID,String string){
        ((TextView) view.findViewById(viewID)).setText(string);
    }

    public static String isStringNull(String value,String defaultValue){
        if(value==null){
            return defaultValue;
        }
        return value;
    }

    public static String convertDateObjToString(String format,Date date){
        String returnData = "";
        try {
            returnData =  android.text.format.DateFormat.format(format, date).toString();
        } catch (Exception e){
            returnData = e.toString();
        }
        return returnData;
    }

    public static Date convertStringToDateObj(String format,String dateString){
        Date returnData = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            returnData = simpleDateFormat.parse(dateString);
        } catch (Exception e){
            Log.e("showtaskData","error>"+e.toString());
            returnData = Calendar.getInstance().getTime();
        }

        return returnData;
    }
    public static long convertStringToLong(String data){
        if(data.length()==0){
            return 0;
        } else {
            try{
                return Long.parseLong(data);
            } catch (Exception e){
                return 0;
            }
        }
    }

    public static double convertStringToDouble(String data){
        if(data.length()==0){
            return 0;
        } else {
            try{
                return Double.parseDouble(data);
            } catch (Exception e){
                return 0;
            }
        }
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    public static String convertDoubleToString(double number, String format){
        NumberFormat numberFormat  = new DecimalFormat(format);
        String str = numberFormat.format(number);
        return str;
    }
}
