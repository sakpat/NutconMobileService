package com.soldev.fieldservice;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MobileServiceApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
    }
}
