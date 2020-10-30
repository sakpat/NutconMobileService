package com.soldev.fieldservice.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaver {
    private String directoryName = "images";
    private String fileName = "image.png";
    private Context context;
    private File storedDirectory;
    private boolean external=false;

    public ImageSaver(Context context) {
        this.context = context;
    }

    public ImageSaver setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ImageSaver setExternal(boolean external) {
        this.external = external;
        return this;
    }

    public ImageSaver setDirectory(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public boolean save(Bitmap bitmapImage,int quality) {
        boolean saveSucceed = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, quality, fileOutputStream);
            saveSucceed = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveSucceed;
    }

    @NonNull
    public File createFile() {
        File directory;
        if (external) {
            directory = getAlbumStorageDir(directoryName);
            if (!directory.exists()){
                directory.mkdir();
            }
        } else {
//            directory = new File(context.getFilesDir()+"/"+directoryName);
            directory = context.getExternalFilesDir(directoryName);
            if (!directory.exists()){
                directory.mkdir();
            }
        }
        storedDirectory = new File(directory, fileName);
        Log.e("showcreatfile",storedDirectory.toString());
        return storedDirectory;
    }

    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("ImageSaver", "Directory not created");
        }
        return file;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deleteFile() {
        File file = createFile();
        return file.delete();
    }

    public String getFullFilePath(){
        return  storedDirectory.toString();
//        +"/"+directoryName+"/"+fileName;
    }
}
