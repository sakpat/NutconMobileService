package com.soldev.fieldservice.utilities;

public class AppConstant {
    public static final String DB_ROOT = "MOBILEWORK";
    public static final String DB_TASK_LIST = "TASKLIST";
    public static final String DB_TASK_QC_LIST = "TASKQCLIST";
    public static final String DB_MASTER_SETTING = "MASTERSETTING";
    public static final String DB_MASTER_TEMPLATE = "MASTERTEMPLATE";
    public static final String DB_TEMPLATE_EQUIPMENT = "TEMPLATEEQUIPMENT";
    public static final String DB_TEMPLATE_CHECK_LIST = "TEMPLATCHECKLIST";
    public static final String DB_TEMPLATE_CHECK_SITE_LIST = "TEMPLATCHECKSITELIST";
    public static final String DB_TEMPLATE_QC_LIST = "TEMPLATQCLIST";
    public static final String DB_MASTER_TASKTYPELIST = "TASKTYPELIST";
    public static final String DB_MESSAGE = "TASKMESSAGE";

    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;
    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 101;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_FINE_LOCATION = 103;
    public static final int REQUEST_COARSE_LOCATION = 104;
    public static final int REQUEST_BACKGROUND_LOCATION = 105;
    public static final int REQUEST_CODE_PICK_IMAGE = 106;
    public static final int REQUEST_CODE_QR_START_TIME = 107;
    public static final int REQUEST_CODE_QR_END_TIME = 108;
    public static final int REQUEST_CODE_QR_EMP_SCANNER = 109;



    public static final int REQUEST_CODE_SIGNATURE = 200;
    public static final int REQUEST_CODE_SIGNATURE_DELIVER = 204;
    public static final int REQUEST_CODE_SIGNATURE_RECEIVE = 205;
    public static final int REQUEST_CODE_DRAWING = 201;
    public static final int REQUEST_CODE_PIPE_DRAWING = 202;
    public static final int REQUEST_CODE_GET_PHOTO = 203;
    //Task Status
    public static final String TASK_STATUS_NEW = "N";
    public static final String TASK_STATUS_MOBILE = "M";
    public static final String TASK_STATUS_READ = "R";
    public static final String TASK_STATUS_PROGRESS = "P";
    public static final String TASK_STATUS_DONE = "D";
    public static final String TASK_STATUS_PENDING = "Z";
    public static final String TASK_STATUS_REVIEW = "V";
    public static final String TASK_STATUS_CLOSE = "C";
    public static final String TASK_STATUS_NOTHING = "X";
//    public static final String TASK_STATUS_QC = "Q";
    public static final String TASK_STATUS_REDELIVER = "Y";
    public static final String TASK_STATUS_REJECT = "J";
    public static final String TASK_STATUS_REASSIGN = "A";


    public static final String TASK_FLOW_GROUP_ASSGINER = "A";
    public static final String TASK_FLOW_GROUP_MOBILE = "M";
    public static final String TASK_FLOW_GROUP_REVIEW = "R";
    public static final String TASK_FLOW_GROUP_CLOSE = "C";

    public static final String PARAM_IMAGE_PATH = "IMAGEPATH";

    public static final String PARAM_SCREEN_ORIENTATION = "SCREENORIENT";

    //Task Type Code

    public static final String TASK_TYPE_PUMP = "1";
    public static final String TASK_TYPE_POLISHING = "2";
    public static final String TASK_TYPE_SMOTH = "3";
    public static final String TASK_TYPE_OTHER = "4";
    public static final String TASK_TYPE_DOCUMENT = "5";

    //Database
    public static final String DB_FIELD_TASK_ID = "TASKID";
    public static final String DB_FIELD_FILE_NAME = "FILENAME";

    public static final String DB_FIELD_LATITUDE = "LATITUDE";
    public static final String DB_FIELD_LONGITUDE = "LONGITUDE";

    public static final String DB_FIELD_FILE_NAME_SIGNATURE = "SIGNATURE";
    public static final String DB_FIELD_FILE_NAME_SIGNATURE_DELIVER = "DELIVERSIGNATURE";
    public static final String DB_FIELD_FILE_NAME_SIGNATURE_RECEIVE = "RECEIVESIGNATURE";
    public static final String DB_FIELD_FILE_NAME_DRAWING = "DRAWING";
    public static final String DB_FIELD_FILE_NAME_DRAWING_ON_DEVICE = "DRAWINGONDEVICE";
    public static final String DB_FIELD_FILE_NAME_PIPE_DRAWING = "PIPEDRAWING";

//    public static final String AUTHORITY_PROVIDER = "com.soldev.dev.mobileservice.provider";
    public static final String AUTHORITY_PROVIDER = "com.soldev.prod.mobileservice.provider";
//    -----------

    public static final String USER_MOBILE_ROLE = "mobile";
    public static final String USER_QC_ROLE = "qc";

    //image category
    public static final String IMAGE_CAT_PERFORM = "IMGPERFORM";
    public static final String IMAGE_CAT_TRUCK = "IMGTRUCK";
    public static final String IMAGE_CAT_GENERAL = "IMGGENERAL";
    public static final String IMAGE_CAT_DELIVER = "IMGDELIVER";
    public static final String IMAGE_CAT_QC = "IMGQC";

    // Message types sent from the BluetoothPrintService Handler
    public static final int MESSAGE_DEVICE_NAME = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_READ = 3;

    // Key names received from the BluetoothPrintService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    public static final int REQUEST_ENABLE_BT = 3;

    public static final int PERMISSION_DEVICE_SCAN_SECURE = 11;
    public static final int PERMISSION_DEVICE_SCAN_INSECURE = 12;

    public static final String NOTIFICATION_CHANNEL_ID = "com.soldev.fieldservice";
    public static final String NOTIFICATION_CHANNEL_NAME = "Nutcon Background Service";
}
