package com.ttraafffic.jjamm.mmew.web;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class Decod {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decod(String str){

        Base64.Decoder dec = Base64.getDecoder();
        String decoded = new String(dec.decode(str));

        return decoded;
    }
}
