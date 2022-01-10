package com.ttraafffic.jjamm.mmew.web;

import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

public class WebSettings {

    public static void webSettings( WebView webFullApp){

        CookieManager.getInstance().setAcceptThirdPartyCookies(webFullApp, true);
        CookieManager.getInstance().setAcceptCookie(true);
        webFullApp.setVisibility(View.VISIBLE);
        webFullApp.getSettings().setAllowFileAccessFromFileURLs(true);
        webFullApp.getSettings().setSavePassword(true);
        webFullApp.getSettings().setDatabaseEnabled(true);
        webFullApp.getSettings().setRenderPriority(android.webkit.WebSettings.RenderPriority.HIGH);
        webFullApp.getSettings().setCacheMode(android.webkit.WebSettings.LOAD_DEFAULT);
        webFullApp.getSettings().setAppCacheEnabled(true);
        webFullApp.getSettings().setLoadsImagesAutomatically(true);
        webFullApp.setSaveEnabled(true);
        webFullApp.getSettings().setMixedContentMode(0);
        webFullApp.setFocusable(true);
        webFullApp.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webFullApp.getSettings().setJavaScriptEnabled(true);
        webFullApp.getSettings().setAllowContentAccess(true);
        webFullApp.getSettings().setLoadWithOverviewMode(true);
        webFullApp.getSettings().setEnableSmoothTransition(true);
        webFullApp.getSettings().setUseWideViewPort(true);
        webFullApp.getSettings().setSaveFormData(true);
        webFullApp.getSettings().setAllowFileAccess(true);
        webFullApp.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webFullApp.getSettings().setDomStorageEnabled(true);
        webFullApp.setFocusableInTouchMode(true);
    }
}
