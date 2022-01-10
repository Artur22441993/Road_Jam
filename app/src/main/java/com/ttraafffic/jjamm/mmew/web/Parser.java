package com.ttraafffic.jjamm.mmew.web;

import android.util.Log;

import com.onesignal.OneSignal;

public class Parser {

    public static String parser(String campaignStr, String getPackageName, String AID, String appsFlyerUID){

        //naming
        String key;
        String sub6;
        String sub7;
        String sub2;
        String sub3;
        String sub4;
        String sub5;

//Gp88Vp::sub6::sub7::sub2::sub3::sub4::sub5
        String[] str = campaignStr.split("::");
        Log.d("TAG", ""+str);
        try {
            key = str[0];
        }catch (Exception e){
            key = "";
        }
        try {
            sub6 = str[1];
        }catch (Exception e){
            sub6 = "";
        }

        try {
            sub7 = str[2];
        }catch (Exception e){
            sub7 = "";

        }

        try {
            sub2 = str[3];
        }catch (Exception e){
            sub2 = "";
        }

        try {
            sub3 = str[4];
        }catch (Exception e){
            sub3 = "";
        }


        try {
            sub4 = str[5];
        }catch (Exception e){
            sub4 = "";
        }

        try {
            sub5 = str[6];
        }catch (Exception e){
            sub5 = "";
        }

        OneSignal.sendTag("sub_app",sub6);
        //собираем параметры ссылки с конверсией
        //https://timetowins.work/Gp88Vp?sub2={sub2}&sub3={sub3}&sub4={sub4}&sub5={sub5}&sub6={sub6}&sub7={sub6}&apps_id={apps_id}&ad_id={ad_id}&app_id={app_id}&dev_key={dev_key}

        String paramsBuild = "?bundle=" + getPackageName + "&ad_id=" + AID + "&apps_id=" + appsFlyerUID +
                "&sub6=" + sub6 +
                "&sub7=" + sub7 +
                "&sub2=" + sub2 +
                "&sub3=" + sub3 +
                "&sub4=" + sub4 +
                "&sub5=" + sub5;
        Log.d("qqqq",key);
        Log.d("qqqq",paramsBuild);

        return  key + paramsBuild;
    }
}
