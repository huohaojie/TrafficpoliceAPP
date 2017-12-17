package com.example.trafficpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.MyUser;
import com.example.trafficpoliceapp.utils.ShareUtils;
import com.example.trafficpoliceapp.utils.UtilTools;
import com.example.trafficpoliceapp.view.CustomDialog;
import com.example.trafficpoliceapp.view.MainActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hello World on 2017/12/5.
 * 描述：登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_registered;
    private Button btn_login;
    private EditText et_name;
    private EditText login_et_password;
    private CheckBox keep_password;
    private TextView tv_forget;

    private CustomDialog dialog;

    //圆形头像
    private CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_name = (EditText) findViewById(R.id.et_name);
        login_et_password = (EditText) findViewById(R.id.login_et_password);

        btn_login.setOnClickListener(this);

        profile_image = (CircleImageView)findViewById(R.id.profile_image);
        UtilTools.getImageToShare(this,profile_image);

        keep_password = (CheckBox) findViewById(R.id.keep_password);
        //初始化dialog
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
      dialog.setCancelable(true);
        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            login_et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registered:
                    startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                    startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                //1、获取输入矿的值
                String name = et_name.getText().toString().trim();
                String password = login_et_password.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e==null){
                                //判断邮箱是否验证
                                if (user.getEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "登录失败"+ e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    //假设我输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this, "keeppass" ,keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()){

            //记住用户名和密码
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",login_et_password.getText().toString().trim());

        }else{
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"password");
        }

    }
}
