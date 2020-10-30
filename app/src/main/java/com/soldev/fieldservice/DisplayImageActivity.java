package com.soldev.fieldservice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.soldev.prod.mobileservice.R;

import java.io.File;

public class DisplayImageActivity extends AppCompatActivity {
    private String taskID;
    private String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        taskID = getIntent().getExtras().getString("TASKID");
        fileName = getIntent().getExtras().getString("FILENAME");
       loadImageToView(fileName,(com.soldev.fieldservice.utilities.TouchImageView) findViewById(R.id.imgPreview));
//        findViewById(R.id.btnClosePreview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }

    public boolean loadImageToView(String fileName, com.soldev.fieldservice.utilities.TouchImageView imageView){
        Log.e("showimage","file name:"+fileName);
        File directory = getExternalFilesDir(taskID);
        File imgFile = new File(directory, fileName);
        Log.e("showimage","full path:"+imgFile.toString());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            imageView.invalidate();
            return true;
        } else {
            return false;
        }
    }
}