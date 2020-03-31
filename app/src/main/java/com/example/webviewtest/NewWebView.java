package com.example.webviewtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

public class NewWebView extends WebView {

    private Paint paint;
    private static boolean isSmallWebViewDisplayed = true;
    private FrameLayout normalView;
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Logger.e("load"+url);
            return true;
        }

    };
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        Logger.e("loadurl"+url);
        return true;
    }
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }
    private WebChromeClient chromeClient = new WebChromeClient() {
        /**
         * webview 的窗口转移
         */
        @Override
        public boolean onCreateWindow(WebView arg0, boolean arg1, boolean arg2, Message msg) {
            if (NewWebView.isSmallWebViewDisplayed == true) {

                WebViewTransport webViewTransport = (WebViewTransport) msg.obj;
                WebView webView = new WebView(NewWebView.this.getContext()) {

                    protected void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        paint.setColor(Color.GREEN);
                        paint.setTextSize(15);
                        canvas.drawText("新建窗口", 10, 10, paint);
                        Logger.e("新建窗口");
                    }
                };
                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
                        arg0.loadUrl(arg1);
                        return true;
                    }
                });
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(400, 600);
                lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
                NewWebView.this.addView(webView, lp);
                webViewTransport.setWebView(webView);
                msg.sendToTarget();
            }
            return true;
        }
    };

    /**
     * 初始化
     *
     * @param view
     */
    public void initView(FrameLayout view) {
        normalView = view;
        this.setWebViewClient(client);
        this.setWebChromeClient(chromeClient);
    }

    public NewWebView(Context context) {
        super(context);
    }



    public NewWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @SuppressLint("SetJavaScriptEnabled")
    public NewWebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        paint = new Paint();
        initWebViewSettings();

    }
}
