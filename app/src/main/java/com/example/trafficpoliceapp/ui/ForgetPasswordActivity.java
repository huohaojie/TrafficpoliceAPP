package com.example.trafficpoliceapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Hello World on 2017/12/6.
 * 描述：忘记、重置密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_old;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        et_email = (EditText) findViewById(R.id.et_email);
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_old = (EditText) findViewById(R.id.et_old);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
                //1、获取输入框的邮箱
                final String email = et_email.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(email)){
                    //3、发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已发送至" + email, Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                //1、获取输入框的值
                String old = et_old.getText().toString().trim();
                String news = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();

                //2、判断是否为空
                if (!TextUtils.isEmpty(old) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(new_password)){
                    //判断两次密码是否一致
                    if (news.equals(new_password)){
                        //重置密码
                        MyUser.updateCurrentUserPassword(old, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
