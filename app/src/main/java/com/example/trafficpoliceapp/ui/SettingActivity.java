package com.example.trafficpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.utils.L;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * Created by Hello World on 2017/12/9.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    //个人中心
    private LinearLayout ll_personal;
    //扫一扫
    private LinearLayout ll_scan;
    //扫描的结果
    private TextView tv_scan_result;
    //生成二维码
    private LinearLayout ll_qr_code;
    //我的位置
    private LinearLayout ll_my_location;
    //关于软件
    private LinearLayout ll_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        ll_personal = (LinearLayout) findViewById(R.id.ll_personal);
        ll_personal.setOnClickListener(this);

        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);

        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
        ll_qr_code.setOnClickListener(this);

        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
        ll_my_location.setOnClickListener(this);

        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_personal:
                startActivity(new Intent(this,UserActivity.class));
                break;
            case R.id.ll_scan:
                L.i("ll_scan");
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(this,CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.ll_qr_code:
                startActivity(new Intent(this, QrCodeActivity.class));
                break;
            case R.id.ll_my_location:
                startActivity(new Intent(this,LocationActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(this, ""+scanResult.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
