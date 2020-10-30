package com.soldev.fieldservice.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperUtility extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "nutcon.db";
    public static final String TB_TASKLIST = "TaskList";
    public static final String PERFORMTASKID = "taskID";
    public static final String PERFORMTASKDATA = "taskData";
    private static final String DATABASE_CREATE = "create table "+TB_TASKLIST+" ( COLUMN_ID integer primary key autoincrement,"+PERFORMTASKID+" text, "+PERFORMTASKDATA+" text );";

    public SQLiteHelperUtility(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
