package com.example.webviewtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private String URLPATH;
    private WebView webView;
    private LinearLayout layout;
    private Paint paint;

    private boolean isSmallWebViewDisplayed = true;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            Logger.e("沒有上一頁");
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                new AlertDialog.Builder(this)
                        .setTitle("確定要離開嗎?")


                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "bye", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "stay", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        }
        return super.onKeyDown(keyCode, event);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        layout = findViewById(R.id.layout);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Logger.e("visibiable");
            }
        });
        //

        //
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);//允許javascript->基本上都必須開啟,大部分網站操作都會用到


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.e("開始加載");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.e("完成加載");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Logger.e("override");
                return super.shouldOverrideUrlLoading(view, request);


            }


        });
        webView.setWebChromeClient(chromeClient);


        //<a href="https://www.taobao.com/" title="淘宝" target="_blank">
        //https://www.w3school.com.cn/html5/att_a_target.asp
        //https://world.taobao.com/
        webView.loadUrl("https://www.w3school.com.cn/html5/att_a_target.asp");


    }

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Toast.makeText(getApplicationContext(),"create",Toast.LENGTH_SHORT).show();
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);

        }

    };
}
