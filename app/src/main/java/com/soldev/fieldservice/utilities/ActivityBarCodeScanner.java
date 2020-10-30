package com.soldev.fieldservice.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;
import com.soldev.prod.mobileservice.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Sakpat on 11-Oct-16.
 */
public class ActivityBarCodeScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

    }

    public void QrScanner(){

        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, AppConstant.REQUEST_CODE_CAMERA);
        } else {
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);

            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();         // Start camera
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(mScannerView!=null) {
            mScannerView.stopCamera();           // Stop camera on pause
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
//Toast.makeText(ActivityBarCodeScanner.this,rawResult.getText(),Toast.LENGTH_LONG).show();
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("BARCODE",rawResult.getText());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        QrScanner();
    }
}
