package com.example.trafficpoliceapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.MyUser;
import com.example.trafficpoliceapp.utils.UtilTools;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import cn.bmob.v3.BmobUser;

/**
 * Created by Hello World on 2017/12/9.
 */

public class QrCodeActivity extends BaseActivity{
    private ImageView iv_qr_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
    }

    private void initView() {
        iv_qr_code= (ImageView) findViewById(R.id.iv_qr_code);
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        String tvname =userInfo.getUsername();
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(tvname, width / 2, width / 2,
              BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
