package com.soldev.fieldservice;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.soldev.fieldservice.dataentity.TaskDataConcreteTruck;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskDataExtraFee;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.fieldservice.utilities.BluetoothPrintService;
import com.soldev.fieldservice.utilities.DeviceListActivity;
import com.soldev.prod.mobileservice.R;
import com.woosim.printer.WoosimCmd;
import com.woosim.printer.WoosimImage;
import com.woosim.printer.WoosimService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.List;

public class PrintReportActivity extends AppCompatActivity {
    private static final String TAG = "PrintReportActivity";
    private static final boolean showLog = false;
    private static final boolean testMode = false;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the print services
    private BluetoothPrintService mPrintService = null;
    private WoosimService mWoosim = null;
    private Button btPrintout;
    private TaskDataEntry taskDataEntry;
    private String spaceString = "                                  ";
    private String spaceString15 = "            ";
    private String spaceString20 = "          ";
    private String spaceString55 = "                                                       ";
    private String lineString = "--------------------------------------------------------------------";
    private TextView txtTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_report);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(showLog) Log.i(TAG, "+++ ON CREATE +++");
        // If the adapter is null, then Bluetooth is not supported
        if(!testMode) {
            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "No device", Toast.LENGTH_LONG).show();
                finish();
            }
            getPrinter();
        }
//        if(connectDevice(AppPreference.getPrefMacAddressBluetooth(PrintReportActivity.this),false)) {
//            getPrinter();
//        }
        setBinding();
        setEvent();
        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        taskDataEntry = gson.fromJson(bundle.getString("DATA"), TaskDataEntry.class);
    }

    private void setBinding(){
        btPrintout = findViewById(R.id.btPrintOut);
        txtTest = findViewById(R.id.txtTest);
    }

    private void setEvent(){
        btPrintout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(taskDataEntry.getTaskGroupCode().equalsIgnoreCase("1")){
                    printPump();
                } else {
                    printOther();
                }

            }
        });
    }

    private void printPump(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.newlogobw, options);
        if(!testMode) {
            printImage(bmp, 80, 0);
        }
        printText("",1);
        printText("          "+getString(R.string.label_delivery_note_title),2);
        printText("",1);
        String text = getString(R.string.label_task_number)+": "+taskDataEntry.getTaskNo()+
                spaceString.substring(0,spaceString.length()-(taskDataEntry.getTaskNo().length()+getString(R.string.label_task_number).length()))
                +getString(R.string.label_task_date)+":"+
                taskDataEntry.getTaskDate();
        printText(text,1);
        String taskDesc = taskDataEntry.getTaskType();
        if(taskDataEntry.getCreateRemark()!=null&taskDataEntry.getCreateRemark().length()>0){
            taskDesc = taskDesc +" "+taskDataEntry.getCreateRemark();
        }
        text = getString(R.string.label_task_type)+": "+taskDesc;
        printText(text,1);
        text = getString(R.string.label_customer_name)+": "+ AppUitility.isStringNull(taskDataEntry.getCustomerName(),"");
        printText(text,1);
        text = getString(R.string.label_location_detail)+": "+ AppUitility.isStringNull(taskDataEntry.getLocation(),"");
        printText(text,1);
        text = getString(R.string.label_project_name)+": "+ AppUitility.isStringNull(taskDataEntry.getProjectName(),"");
        printText(text,1);
        text = getString(R.string.label_used_concrete)+": "+ AppUitility.isStringNull(taskDataEntry.getConcreteType(),"");
        printText(text,1);
        printText("",1);
        printText(getString(R.string.label_pump_fee),1);
        printText(lineString,1);
        text = getString(R.string.label_item)+spaceString.substring(0,spaceString.length()-getString(R.string.label_item).length())
                +getString(R.string.label_sale_cubic);
        printText(text,1);

        //fee
        printText(lineString,1);
        String text1 = getString(R.string.label_pump_minimum)+" ("
                + AppUitility.convertDoubleToString(taskDataEntry.getMinimumCementVolumn(), "#0.00")
                +")";
        double displayMimimum;
        if(taskDataEntry.getMinimumCementVolumn()>taskDataEntry.getTotalCementVolumn()){
            displayMimimum = taskDataEntry.getMinimumCementVolumn();
        } else {
            displayMimimum = taskDataEntry.getMinimumCementVolumn();
        }
        text = text1+spaceString.substring(0,spaceString.length()+4-text1.length())
                + AppUitility.convertDoubleToString(displayMimimum, "#0.00");
        printText(text,1);
        double exceedAmount;
        if(taskDataEntry.getMinimumCementVolumn()>taskDataEntry.getTotalCementVolumn()){
            exceedAmount = 0;
        } else {
            exceedAmount = taskDataEntry.getTotalCementVolumn()-taskDataEntry.getMinimumCementVolumn();
        }
        text = getString(R.string.label_pump_exceed)
                +spaceString.substring(0,spaceString.length()+4-getString(R.string.label_pump_exceed).length())
                + AppUitility.convertDoubleToString(exceedAmount, "#0.00");
        printText(text,1);
        double netVolumn;
        if(taskDataEntry.getMinimumCementVolumn()>taskDataEntry.getTotalCementVolumn()){
            netVolumn = taskDataEntry.getMinimumCementVolumn();
        } else {
            netVolumn = taskDataEntry.getTotalCementVolumn();
        }
        text = getString(R.string.label_pump_total)
                +spaceString.substring(0,spaceString.length()-getString(R.string.label_pump_total).length())
                + AppUitility.convertDoubleToString(netVolumn, "#0.00");
        if(!taskDataEntry.isWorkPerformed()){
            text = text+" "+getString(R.string.label_noworkperformed);
        }
        printText(text,1);
        printText(lineString,1);
        printText("",1);

        // extra fee
        printText(getString(R.string.label_extra_fee),1);
        List<TaskDataExtraFee> taskDataExtraFees = taskDataEntry.getExtraFeeList();
        printText(lineString,1);
        int countRow=1;
        if(taskDataEntry.getPipeLength()>0){
            String unitString="";
            if(getString(R.string.label_pipe).indexOf("(")>-1){
                String descString = getString(R.string.label_pipe);
                unitString =  descString.substring(getString(R.string.label_pipe).indexOf("(")+1,descString.length()-1);
            }
            String descShow = getString(R.string.label_pipe);
            text = countRow+". "+ descShow.replace("("+unitString+")","")+ " จำนวน " + AppUitility.convertDoubleToString(taskDataEntry.getPipeLength(),"##")+" "+unitString;
            printText(text,1);
        }
        if(taskDataExtraFees!=null){
            for (int i = 0; i < taskDataExtraFees.size() ; i++) {
                if(taskDataExtraFees.get(i).getAmount()>0) {
                    countRow++;
                    String unitString="";
                    if(taskDataExtraFees.get(i).getDesctipion().indexOf("(")>-1){
                        String descString = taskDataExtraFees.get(i).getDesctipion();
                        unitString =  descString.substring(taskDataExtraFees.get(i).getDesctipion().indexOf("(")+1,descString.length()-1);
                    }
                    String descShow = taskDataExtraFees.get(i).getDesctipion();
                    text = countRow+". "+ descShow.replace("("+unitString+")","")+ " จำนวน " + AppUitility.convertDoubleToString(taskDataExtraFees.get(i).getAmount(),"##.00")+" "+unitString;
                    printText(text,1);
                }
            }
        }
        printText(lineString,1);
        printText("",1);
        //detail task
        printText(getString(R.string.label_detail_task),1);
        printText(lineString,1);
        List<TaskDataConcreteTruck> taskDataConcreteTrucks = taskDataEntry.getTaskDataConcreteTrucks();
        if(taskDataConcreteTrucks!=null){
            for (int i = 0; i < taskDataConcreteTrucks.size() ; i++) {
                TaskDataConcreteTruck taskDataConcreteTruck = taskDataConcreteTrucks.get(i);
                text = (i+1)+". "+taskDataConcreteTruck.getLicensePlate()+spaceString20.substring(taskDataConcreteTruck.getLicensePlate().length(),spaceString20.length())
                        +"  เวลา "+taskDataConcreteTruck.getTimeStart().substring(10).trim()+"-"+taskDataConcreteTruck.getTimeEnd().substring(10).trim()
                        +"  จำนวน "+taskDataConcreteTruck.getAmount()+" คิว";
                printText(text,1);
            }
        }
        printText(lineString,1);
        printText("",1);
        printText(getString(R.string.label_pipe_drawing),1);
        Bitmap drawingBMP = getImage(AppConstant.DB_FIELD_FILE_NAME_PIPE_DRAWING + ".jpg",317,680);
        if(!testMode&&drawingBMP!=null){
            printImage(drawingBMP,0,0);
        }
        printText("",1);
        printText(getString(R.string.label_remark)+": "+taskDataEntry.getTaskRemark(),1);
        printText("",1);
        printText(getString(R.string.label_delivery_notice1),1);
        printText(getString(R.string.label_delivery_notice2),1);
        printText(System.lineSeparator(),1);
        printText(getString(R.string.label_deliver_signature)+": "+ AppUitility.isStringNull(AppPreference.getUserFullName(PrintReportActivity.this),""),1);
//        printText(taskDataEntry.getDeliverSignature());
        Bitmap deliveryBMP = getImage(taskDataEntry.getDeliverSignature(),187,400);
        if(!testMode&&deliveryBMP!=null){
            printImage(deliveryBMP,0,0);
        }
        printText(getString(R.string.label_receive_signature)+": "+ AppUitility.isStringNull(taskDataEntry.getTaskReceiver(),""),1);
//        printText(taskDataEntry.getReceiveSignature());
        deliveryBMP = getImage(taskDataEntry.getReceiveSignature(),187,400);
        if(!testMode&&deliveryBMP!=null){
            printImage(deliveryBMP,0,0);
        }
        printText(System.lineSeparator(),1);
        printText(System.lineSeparator(),1);
    }

    private void printOther(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.newlogobw, options);
        if(!testMode) {
            printImage(bmp, 80, 0);
        }
        printText("",1);
        printText("          "+getString(R.string.label_delivery_note_title),2);
        printText("",1);
        String text = getString(R.string.label_task_number)+": "+taskDataEntry.getTaskNo()+
                spaceString.substring(0,spaceString.length()-(taskDataEntry.getTaskNo().length()+getString(R.string.label_task_number).length()))
                +getString(R.string.label_task_date)+":"+
                taskDataEntry.getTaskDate();
        printText(text,1);
        String taskDesc = taskDataEntry.getTaskType();
        if(taskDataEntry.getCreateRemark()!=null&taskDataEntry.getCreateRemark().length()>0){
            taskDesc = taskDesc +" "+taskDataEntry.getCreateRemark();
        }
        text = getString(R.string.label_task_type)+": "+taskDesc;
        printText(text,1);
        text = getString(R.string.label_customer_name)+": "+ AppUitility.isStringNull(taskDataEntry.getCustomerName(),"");
        printText(text,1);
        text = getString(R.string.label_location_detail)+": "+ AppUitility.isStringNull(taskDataEntry.getLocation(),"");
        printText(text,1);
        text = getString(R.string.label_project_name)+": "+ AppUitility.isStringNull(taskDataEntry.getProjectName(),"");
        printText(text,1);
        text = getString(R.string.label_used_product)+": "+ AppUitility.isStringNull(taskDataEntry.getProductType(),"");
        printText(text,1);
        text = getString(R.string.label_polished_actual_area_desc)+": "+taskDataEntry.getAreaPerform();
        if(!taskDataEntry.isWorkPerformed()){
            text = text+" "+getString(R.string.label_noworkperformed);
        }
        printText(text,1);

        printText("",1);

        Bitmap drawingBMP = getImage(taskDataEntry.getDrawingImage(),317,680);
        if(!testMode&&drawingBMP!=null){
            printImage(drawingBMP,0,0);
        }

        printText(getString(R.string.label_remark)+": "+taskDataEntry.getTaskRemark(),1);
        printText("",1);




        printText(getString(R.string.label_delivery_nonpump_notice1),1);
        printText(getString(R.string.label_delivery_nonpump_notice2),1);
        printText(getString(R.string.label_delivery_nonpump_notice3),1);
        printText(getString(R.string.label_delivery_nonpump_notice4),1);
        printText(System.lineSeparator(),1);
        printText(getString(R.string.label_deliver_signature)+": "+ AppUitility.isStringNull(AppPreference.getUserFullName(PrintReportActivity.this),""),1);
//        printText(taskDataEntry.getDeliverSignature());
        Bitmap deliveryBMP = getImage(taskDataEntry.getDeliverSignature(),187,400);
        if(!testMode&&deliveryBMP!=null){
            printImage(deliveryBMP,0,0);
        }
        printText(getString(R.string.label_receive_signature)+": "+ AppUitility.isStringNull(taskDataEntry.getTaskReceiver(),""),1);
//        printText(taskDataEntry.getReceiveSignature());
        deliveryBMP = getImage(taskDataEntry.getReceiveSignature(),187,400);
        if(!testMode&&deliveryBMP!=null){
            printImage(deliveryBMP,0,0);
        }
        printText(System.lineSeparator(),1);
        printText(System.lineSeparator(),1);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(showLog) Log.i(TAG, "++ ON START ++");
//        if(D) Log.i(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupPrint() will then be called during onActivityResult
        if(!testMode) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, AppConstant.REQUEST_ENABLE_BT);
                // Otherwise, setup the chat session
            } else {
                if (mPrintService == null) setupPrint();
            }
        }
    }
    private void setupPrint() {
        mPrintService = new BluetoothPrintService(mHandler);
        mWoosim = new WoosimService(mHandler);
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<PrintReportActivity> mActivity;

        MyHandler(PrintReportActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PrintReportActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case AppConstant.MESSAGE_DEVICE_NAME:
                // save the connected device's name
                String mConnectedDeviceName = msg.getData().getString(AppConstant.DEVICE_NAME);

                Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                btPrintout.setClickable(true);
                btPrintout.setText("พิมพ์งาน");
//                redrawMenu();
                break;
            case AppConstant.MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getInt(AppConstant.TOAST), Toast.LENGTH_SHORT).show();
                break;
            case AppConstant.MESSAGE_READ:
                mWoosim.processRcvData((byte[])msg.obj, msg.arg1);
                break;
            case WoosimService.MESSAGE_PRINTER:
                switch (msg.arg1) {
                    case WoosimService.MSR:
                        if (msg.arg2 == 0) {
                            Toast.makeText(getApplicationContext(), "MSR reading failure", Toast.LENGTH_SHORT).show();
                        } else {
                            byte[][] track = (byte[][])msg.obj;
                            if (track[0] != null) {
                                String str = new String(track[0]);
                                Log.e("WoosimService.MSR","str0:"+str);
                            }
                            if (track[1] != null) {
                                String str = new String(track[1]);
                                Log.e("WoosimService.MSR","str1:"+str);
                            }
                            if (track[2] != null) {
                                String str = new String(track[2]);
                                Log.e("WoosimService.MSR","str2:"+str);

                            }
                        }
                        break;
                }
                break;
        }
    }

    private void getPrinter(){
        Intent serverIntent;
        int permissionCheck;
        permissionCheck = ContextCompat.checkSelfPermission(PrintReportActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(PrintReportActivity.this, DeviceListActivity.class);
            startActivityForResult(serverIntent, AppConstant.REQUEST_CONNECT_DEVICE_INSECURE);
        } else {
            ActivityCompat.requestPermissions(PrintReportActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstant.PERMISSION_DEVICE_SCAN_INSECURE);
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mPrintService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mPrintService.getState() == BluetoothPrintService.STATE_NONE) {
                // Start the Bluetooth print services
                mPrintService.start();
            }
        }
    }
    @Override
    public synchronized void onPause() {
        super.onPause();
        if(showLog) Log.i(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(showLog) Log.i(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        if(showLog) Log.i(TAG, "--- ON DESTROY ---");
        // Stop the Bluetooth print services
        if (mPrintService != null) mPrintService.stop();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstant.PERMISSION_DEVICE_SCAN_SECURE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(intent, AppConstant.REQUEST_CONNECT_DEVICE_SECURE);
                }
                break;
            case AppConstant.PERMISSION_DEVICE_SCAN_INSECURE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(intent, AppConstant.REQUEST_CONNECT_DEVICE_INSECURE);
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(showLog) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case AppConstant.REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    String macAddress = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    AppPreference.setPrefMacAddressBluetooth(PrintReportActivity.this,macAddress);
                    connectDevice(macAddress, true);
                }
                break;
            case AppConstant.REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    String macAddress = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    AppPreference.setPrefMacAddressBluetooth(PrintReportActivity.this,macAddress);
                    connectDevice(macAddress, false);
                }
                break;
            case AppConstant.REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a print
                    setupPrint();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    if(showLog) Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private boolean connectDevice(String macAddress, boolean secure) {
        try {
            String address = null;
            // Get the device MAC address
            if (macAddress != null)
                address = macAddress;
            // Get the BLuetoothDevice object
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            // Attempt to connect to the device
            mPrintService.connect(device, secure);
            return true;
        } catch (Exception e){
            return  false;
        }
    }


    private void sendData(byte[] data) {
        // Check that we're actually connected before trying printing
        if (mPrintService.getState() != BluetoothPrintService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        Log.e("show data length",data.length+"");
        if (data.length > 0)
            mPrintService.write(data);
//        mPrintService.
    }

    public void printText(String textToPrint,int mCharsize)  {
        if(testMode) {
            printTest(textToPrint);
        } else {
            printToPrinter(textToPrint,mCharsize);
        }
    }

    public void printToPrinter(String textToPrint,int mCharsize ){
        try {
            String string = textToPrint;
            byte[] text = null;

            if (string == null)
                return;
            else {
                try {
                    text = string.getBytes("TIS-620");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }


            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            boolean mEmphasis = false, mUnderline = false;
            int mJustification = WoosimCmd.ALIGN_LEFT;
            byteStream.write(WoosimCmd.setTextStyle(mEmphasis, mUnderline, false, mCharsize, mCharsize));
            byteStream.write(WoosimCmd.setTextAlign(mJustification));
            if (text != null) byteStream.write(text);

            Log.e("beforesenddata","start");
            byteStream.write(WoosimCmd.printData());
            sendData(WoosimCmd.initPrinter());
            sendData(byteStream.toByteArray());
        } catch (Exception e){
            Log.e("exception",e.toString());
        }
    }

    private void printTest(String stringTest){
        String oldString = txtTest.getText().toString();
        txtTest.setText(oldString+"\n"+stringTest);
    }


    public void printImage(Bitmap imageToPrint,int x,int y) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false;
//        Bitmap bmp = takeScreenshot();
        if (imageToPrint == null) {
            Log.e(TAG, "resource decoding is failed");
            return;
        }
        int width = imageToPrint.getWidth();
        int height = imageToPrint.getHeight();
        byte[] data = WoosimImage.printBitmap(x, y, width, height, imageToPrint);
        imageToPrint.recycle();

        sendData(WoosimCmd.setPageMode());
        sendData(data);
        sendData(WoosimCmd.PM_setStdMode());
    }

    public Bitmap getImage(String fileName,int newHeight,int newWidth){
        File directory = getExternalFilesDir(taskDataEntry.getTaskID());
        File imgFile = new File(directory, fileName);
        Log.e("showimagename",imgFile.toString());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Log.e("showimagename",
                    "size width>"+myBitmap.getWidth()+"-"+
                            "height"+myBitmap.getHeight());
            return getResizedBitmap(myBitmap,newHeight,newWidth);
        } else {
            return null;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation

        Matrix matrix = new Matrix();

// resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        Log.e("showimagename",
                "resize width>"+resizedBitmap.getWidth()+"-"+
                        "height"+resizedBitmap.getHeight());
        return resizedBitmap;

    }
}