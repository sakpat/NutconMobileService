package com.soldev.fieldservice.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.soldev.fieldservice.MainActivity;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskMessage;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.prod.mobileservice.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class MessageReceiver extends Service {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public MessageReceiver() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
//        onTaskRemoved(intent);
        showNotificationBar(startId);
        return START_STICKY;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
        Log.e("servicemessage","is removed");
    }



    private void showNotificationBar(int startId){
        Log.e("servicemessage","is running");

        NotificationChannel notificationChannel ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(AppConstant.NOTIFICATION_CHANNEL_ID,AppConstant.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(notificationChannel);


            Notification notification = new NotificationCompat.Builder(MessageReceiver.this,AppConstant.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("App running")
                    .setContentText("Listening to message")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Start Listening")
                    .build();
            startForeground(startId,notification);
        } else {
            Notification notification = new NotificationCompat.Builder(MessageReceiver.this,"")
                    .setContentTitle("App running")
                    .setContentText("Listening to message")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Start Listening")
                    .build();
            startForeground(startId,notification);
        }
//        listenToMessageNode();

    }
    private void listenToMessageNode(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MESSAGE).child(AppPreference.getUserName(MessageReceiver.this));
        Query queryDataRef = databaseReference.orderByChild("status").equalTo("N");
        queryDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskMessage> dataList = new ArrayList<>();
                for(DataSnapshot dataRow : dataSnapshot.getChildren()){
                    TaskMessage taskMessage = dataRow.getValue(TaskMessage.class);
                    dataList.add(taskMessage);
                }
                if(dataList.size()>0){
                    Log.e("newtask","you have "+dataList.size()+" unread task");
                    showNotification("คุณยังมีงานที่ยังไม่ได้อ่าน "+dataList.size()+" งาน");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listenToMessageNodeTaskList(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST);
        Query queryDataRef;
        if(AppPreference.getUserRole(MessageReceiver.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            queryDataRef = databaseReference.orderByChild("taskDate");
        } else {
            queryDataRef = databaseReference.orderByChild("taskFlowGroup").equalTo(AppConstant.TASK_FLOW_GROUP_MOBILE);
        }
        queryDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskDataEntry> listData = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    TaskDataEntry taskDataEntry = data.getValue(TaskDataEntry.class);
                    if(AppPreference.getUserRole(MessageReceiver.this).equalsIgnoreCase(AppConstant.USER_QC_ROLE)&&
                    taskDataEntry.getQcStatus().equalsIgnoreCase("N")){
                        listData.add(taskDataEntry);
                    } else if(taskDataEntry.getTaskStatus().equalsIgnoreCase("M")&&
                    taskDataEntry.getTaskPerformer().equalsIgnoreCase(AppPreference.getUserName(MessageReceiver.this))){
                        Log.e("task",taskDataEntry.getTaskNo());
                        listData.add(taskDataEntry);
                    }

                }
                if(listData.size()>0){
                    Log.e("newtask","you have "+listData.size()+" unread task");
                    showNotification("คุณยังมีงานที่ยังไม่ได้อ่าน "+listData.size()+" งาน");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showNotification(String message) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String description = message;
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(AppConstant.NOTIFICATION_CHANNEL_ID+"1", AppConstant.NOTIFICATION_CHANNEL_NAME, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(AppPreference.getUserFullName(MessageReceiver.this))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}
