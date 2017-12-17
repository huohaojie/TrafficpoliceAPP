package com.example.trafficpoliceapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Hello World on 2017/12/4.
 * 描述：activity 基类
 */

public class BaseActivity extends AppCompatActivity{
    /**
     * 主要做的事情
     * 1、统一的属性
     * 2、统一的接口
     * 3、统一的方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示返回键
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }
    //菜单栏操作

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
