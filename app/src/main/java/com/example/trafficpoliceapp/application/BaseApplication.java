package com.example.trafficpoliceapp.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.baidu.mapapi.SDKInitializer;
import com.example.trafficpoliceapp.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by Hello World on 2017/12/4.
 * 描述：application
 */

public class BaseApplication extends Application {
    //创建
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
//        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //第一：默认初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

//        //Android 7.0相机权限
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

//        SDKInitializer.initialize(this);
//        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
