package com.application.refinary.unlock;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OneSignal;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class Refinary extends Application {

    private static Context context;
    private static Refinary mInstance;

    public Refinary(){
        mInstance=this;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context=getApplicationContext();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId("be4f13bc-5f44-4e44-b3ad-ec9aa485e5a0");
        //String UUID = OneSignal.getDeviceState().getUserId();
        OneSignal.promptForPushNotifications();
       /* OneSignal.initWithContext(this);
        OneSignal.setAppId("be4f13bc-5f44-4e44-b3ad-ec9aa485e5a0");
        OneSignal.addSubscriptionObserver(this);*/
    }

    public static Context getAppContext() {
        return context;
    }
}
