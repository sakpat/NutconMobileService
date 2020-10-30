package com.soldev.fieldservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.BrushSizeChooserFragment;
import com.soldev.fieldservice.utilities.CustomView;
import com.soldev.fieldservice.utilities.ImageSaver;
import com.soldev.fieldservice.utilities.OnNewBrushSizeSelectedListener;
import com.soldev.prod.mobileservice.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class TaskFormCanvasActivity extends AppCompatActivity {
    //    private Toolbar mToolbar_top;
    private Toolbar mToolbar_bottom;
    private static final String LOG_CAT = MainActivity.class.getSimpleName();
    private RelativeLayout rlCanvas;
    private String taskID,fileName;
    private CustomView mCustomView;
    private ImageView imgBackGround;
    private TextView txtDateTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form_canvas);
        Bundle bundle = getIntent().getExtras();
        setRequestedOrientation(bundle.getInt(AppConstant.PARAM_SCREEN_ORIENTATION));
        taskID = bundle.getString(AppConstant.DB_FIELD_TASK_ID);
        fileName = bundle.getString(AppConstant.DB_FIELD_FILE_NAME);
        mCustomView = findViewById(R.id.custom_view);
        rlCanvas = findViewById(R.id.rlCanvas);
        txtDateTime = findViewById(R.id.txtDateTime);
        Date dateToShow = Calendar.getInstance().getTime();
        txtDateTime.setText(AppUitility.convertDateObjToString(AppUitility.formatddMMyyhhmmss,dateToShow));
        imgBackGround = findViewById(R.id.imgBackGround);
        if(fileName.equalsIgnoreCase(AppConstant.DB_FIELD_FILE_NAME_DRAWING)){
            if(!loadImageToView(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE,imgBackGround)){
                loadImageToView(fileName, imgBackGround);
            }
        } else {
            loadImageToView(fileName, imgBackGround);
        }
        mToolbar_bottom = findViewById(R.id.toolbar_bottom);
        mToolbar_bottom.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_dots_vertical_white_24dp));

        mToolbar_bottom.inflateMenu(R.menu.menu_drawing);
        mToolbar_bottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleDrawingIconTouched(item.getItemId());
                return false;
            }
        });
    }
    private void handleDrawingIconTouched(int itemId) {
        switch (itemId){
            case R.id.action_delete:
                deleteDialog();
                break;
            case R.id.action_undo:
                mCustomView.onClickUndo();
                break;
            case R.id.action_redo:
                mCustomView.onClickRedo();
                break;
            case R.id.action_share:
                saveDrawingDialog();
                break;
            case R.id.action_cancel:
                onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void deleteDialog(){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(getString(R.string.delete_drawing));
        deleteDialog.setMessage(getString(R.string.new_drawing_warning));
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                imgBackGround.setImageDrawable(null);
                mCustomView.eraseAll();
                dialog.dismiss();
            }
        });
        deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        deleteDialog.show();
    }

    public void saveDrawingDialog(){
        //save drawing attach to Notification Bar and let User Open Image to share.
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                saveThisDrawing();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public void saveThisDrawing()
    {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            boolean saveSucceed=false;
            String imageFullPath = "";
//            String path = Environment.getExternalStorageDirectory().toString();
//            path = path  +"/"+ getString(R.string.app_name);
//            File dir = new File(path);
            //save drawing
//            mCustomView.setDrawingCacheEnabled(true);

            rlCanvas.setDrawingCacheEnabled(true);

            //attempt to save
//            String imTitle = "Drawing" + "_" + System.currentTimeMillis()+".png";
//            String imgSaved = MediaStore.Images.Media.insertImage(
//                    getContentResolver(), mCustomView.getDrawingCache(),
//                    imTitle, "a drawing");
//            String imgSaved = MediaStore.Images.Media.insertImage(
//                    getContentResolver(), rlCanvas.getDrawingCache(),
//                    imTitle, "a drawing");

            try {
//                if (!dir.isDirectory()|| !dir.exists()) {
//                    dir.mkdirs();
//                }
//                mCustomView.setDrawingCacheEnabled(true);
                rlCanvas.setDrawingCacheEnabled(true);
//                File file = new File(dir, imTitle);
//                FileOutputStream fOut = new FileOutputStream(file);
//                Bitmap bm =  mCustomView.getDrawingCache();
                Bitmap bm =  rlCanvas.getDrawingCache();
                ImageSaver imageSaver = new ImageSaver(TaskFormCanvasActivity.this);
                if(fileName.equalsIgnoreCase(AppConstant.DB_FIELD_FILE_NAME_DRAWING)){
                    imageSaver.setFileName(AppConstant.DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE + ".jpg")
                            .setExternal(false)
                            .setDirectory(taskID);
                } else {
                    imageSaver.setFileName(fileName + ".jpg")
                            .setExternal(false)
                            .setDirectory(taskID);
                }
                saveSucceed = imageSaver.save(bm,50);
                imageFullPath = imageSaver.getFullFilePath();
                Log.e("Showpath",imageSaver.getFullFilePath());
//                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);


            } catch (Exception e) {
                e.printStackTrace();
                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Oops! Image could not be saved. Do you have enough space in your device?1", Toast.LENGTH_SHORT);
                unsavedToast.show();
                Log.e("Error","FileNotFoundException:"+e.toString());

            } finally {
                rlCanvas.destroyDrawingCache();
            }


            if(saveSucceed){
                Toast savedToast = Toast.makeText(getApplicationContext(),
                        "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                savedToast.show();
                Intent resultIntent = new Intent();
                Log.e("showsavedpath",imageFullPath);
                resultIntent.putExtra(AppConstant.PARAM_IMAGE_PATH, imageFullPath);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

//            mCustomView.destroyDrawingCache();


        } else {
            Toast.makeText(TaskFormCanvasActivity.this,"Writing file is denied , please setting in Android setting",Toast.LENGTH_LONG).show();
        }

    }


    private void brushSizePicker(){
        //Implement get/set brush size
        BrushSizeChooserFragment brushDialog = BrushSizeChooserFragment.NewInstance((int) mCustomView.getLastBrushSize());
        brushDialog.setOnNewBrushSizeSelectedListener(new OnNewBrushSizeSelectedListener() {
            @Override
            public void OnNewBrushSizeSelected(float newBrushSize) {
                mCustomView.setBrushSize(newBrushSize);
                mCustomView.setLastBrushSize(newBrushSize);
            }
        });
        brushDialog.show(getSupportFragmentManager(), "Dialog");
    }
    public boolean loadImageToView(String fileName, ImageView imageView){
        File directory = getExternalFilesDir(taskID);
        File imgFile = new File(directory, fileName+".jpg");
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
