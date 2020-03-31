package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private WebView webView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView = (WebView) findViewById(R.id.webView);
        editText = (EditText) findViewById(R.id.editText);
        initWebView();
    }

    // 初始化WebView

    private void initWebView() {

        // 不跳转到其他浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        // 设置支持Js
        settings.setJavaScriptEnabled(true);
        // 加载本地html文件
        webView.loadUrl("file:///android_asset/JsMutualAndroid.html");
        webView.addJavascriptInterface(new JSInterface(), "havorld");
    }

    // 按钮的点击事件
    public void click(View view) {

        // javascript:javaCallJs('内容')
        String str = "javascript:androidCallJs(" + "'"+ editText.getText().toString() + "'" + ")";
        // java调用JS方法
        webView.loadUrl(str);
    }


    private class JSInterface {
        // JS需要调用的方法， 在API 17之前有一些漏洞，所以在API 17以后，需要在JavaScript接口类的方法加上@JavascriptInterface注解
        @JavascriptInterface
        public void jsCallAndroid(String str) {

            Toast.makeText(Main2Activity.this, str, Toast.LENGTH_SHORT).show();
        }
    }

}
