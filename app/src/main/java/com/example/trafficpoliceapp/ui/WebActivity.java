package com.example.trafficpoliceapp.ui;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.utils.L;

/**
 * Created by Hello World on 2017/12/9.
 */

public class WebActivity extends BaseActivity{

    //进度
    private ProgressBar mProgressBar;
    //网页
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        initView();

    }

    private void initView() {

        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        mWebView = (WebView) findViewById(R.id.mWebView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        L.i("url:" + url);

        //设置标题
        getSupportActionBar().setTitle(title);

        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);



        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //判断是否是Android 5.0及其以上的系统
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mWebView.loadUrl(request.getUrl().toString());
                }else{
                    mWebView.loadUrl(request.toString());
                }

                //我接受这个事件
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                super.onReceivedSslError(view, handler, error);
            }
        });

    }

    /**
     *
     */
    public class WebViewClient extends WebChromeClient {


        //进度变化的监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }


}
