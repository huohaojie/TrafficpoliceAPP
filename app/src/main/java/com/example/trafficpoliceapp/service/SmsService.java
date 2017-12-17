package com.example.trafficpoliceapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;

/**
 * Created by Hello World on 2017/12/13.
 */

public class SmsService extends Service {


    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutparams;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }





    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutparams = new WindowManager.LayoutParams();
        //定义宽高
        layoutparams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutparams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutparams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutparams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutparams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局

    }









}
