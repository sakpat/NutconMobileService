#include <jni.h>
#include <string>
#include <string.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_soldev_fieldservice_utilities_WebService_geturl(
        JNIEnv *env,
        jobject /* this */,jstring getType) {
    std::string returnString;
    const char *nativeString = env->GetStringUTFChars(getType,0);



    // Change parameter here
    //------------------------------------
    int typeApp =0; //0 is development else 1 is production
    //------------------------------------
    // http://203.151.33.152/NutConWebApi/swagger/index.html

    if(typeApp==0) {
        if (strcmp(nativeString, "webapi") == 0) {
//            returnString = "https://www.soldev.co.th/nutconwebapi/";
            returnString = "http://203.151.33.152/nutconwebapi/";
//            returnString = "http://192.168.0.100/tirotapi/";
        } else if (strcmp(nativeString, "authen") == 0) {
            returnString = "Account/UserLogin";
        } else if (strcmp(nativeString, "server") == 0) {
            returnString = "https://www.soldev.co.th";
        } else if (strcmp(nativeString, "docupload") == 0) {
            returnString = "Document/UploadDocument";
        }  else if (strcmp(nativeString, "getdocbyno") == 0) {
            returnString = "Document/GetDocument/";
        } else if (strcmp(nativeString, "userrole") == 0) {
            returnString = "Account/GetCurrentUserRole/";
        } else if (strcmp(nativeString, "userinfo") == 0) {
            returnString = "Account/GetCurrentUser/";
        } else if (strcmp(nativeString, "userinfowithname") == 0) {
            returnString = "Account/GetUserByIdentity/";
        }  else if (strcmp(nativeString, "changepwd") == 0) {
            returnString = "Account/UserChangePassword/";
        } else if (strcmp(nativeString, "androidversion") == 0) {
            returnString = "/Version/GetAndroidVersion/";
        } else if (strcmp(nativeString, "getalluser") == 0) {
            returnString = "/Account/GetAllUser/";
        }  else if (strcmp(nativeString, "sysparam") == 0) {
            returnString = "/SysParameter/GetSysParameter";
        } else if (strcmp(nativeString, "taskbackup") == 0) {
            returnString = "/Task/SaveTask";
        } else  {
            returnString = "";
        }
    }



    return env->NewStringUTF(returnString.c_str());
}