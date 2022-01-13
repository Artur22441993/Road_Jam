package com.ttraafffic.jjamm.mmew.web;

import android.app.Application;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.onesignal.OneSignal;

import java.io.IOException;
import java.util.Map;

public class ApplicationFull extends Application {


    public static String appsFlyerUID;
    public static String statusAppsFlyer = "";
    public static String AID;



    public static String params;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(Decod.decod(Constants.oneSignalKey));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AID = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext()).getId();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
                Log.e("AID", AID);
            }
        }).start();

        appsFlyerUID = AppsFlyerLib.getInstance().getAppsFlyerUID(this);
        Log.e("appsFlyerUID", appsFlyerUID);


        AppsFlyerLib.getInstance().init(Decod.decod(Constants.appsFId), new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                statusAppsFlyer = map.get(Decod.decod("YWZfc3RhdHVz")).toString();
                if (statusAppsFlyer.equals(Decod.decod("Tm9uLW9yZ2FuaWM="))){
                String str =map.get(Decod.decod("Y2FtcGFpZ24=")).toString();
                params = Parser.parser(str,getPackageName(),AID,appsFlyerUID);
                }


            }

            @Override
            public void onConversionDataFail(String s) {
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
            }

            @Override
            public void onAttributionFailure(String s) {
            }
        }, this);
        AppsFlyerLib.getInstance().start(this);


    }

}
