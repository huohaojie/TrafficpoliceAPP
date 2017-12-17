package com.example.trafficpoliceapp.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.MyUser;
import com.example.trafficpoliceapp.utils.L;
import com.example.trafficpoliceapp.utils.UtilTools;
import com.example.trafficpoliceapp.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hello World on 2017/12/7.
 * 描述：个人中心管理
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_id;
    private EditText et_desc;
    //更新按钮
    private Button btn_update_ok;

    //圆形头像
    private CircleImageView profile_image;

    //提示框
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //获取摄像头权限
        getCameraPermission();
        initView();
    }

    private void initView() {
        btn_exit_user = (Button) findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);

        edit_user = (TextView) findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        btn_update_ok = (Button) findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = (CircleImageView)findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        UtilTools.getImageToShare(this,profile_image);


        dialog = new CustomDialog(this, 0, 0, R.layout.dialog_photo, R.style.pop_anim_style,Gravity.BOTTOM,0);
        //屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        et_username = (EditText) findViewById(R.id.et_username);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_age = (EditText) findViewById(R.id.et_age);
        et_id = (EditText) findViewById(R.id.et_id);
        et_desc = (EditText) findViewById(R.id.et_desc);

        //默认是不可点击的/不可输入
        setEnabled(false);


        //设置具体值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_age.setText(userInfo.getAge()+"");
        et_id.setText(userInfo.getWorkId()+"");
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_desc.setText(userInfo.getDesc());
    }
    //控制焦点
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_id.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit_user:
                //退出登录
                // 清除缓存用户对象
                MyUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1、拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String id = et_id.getText().toString();
                String desc = et_desc.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(sex) & !TextUtils.isEmpty(id)){
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setWorkId(Integer.parseInt(id));
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    //简介
                    if (!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else {
                        user.setDesc("这个人很懒，什么都没有留下！");
                    }
                    BmobUser bmobUser= BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                //修改成功
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(UserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UserActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //判断内存条是否可用，可用的话进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
//        //获取摄像头权限
//        getCameraPermission();
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }



    public void getCameraPermission() {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},2);
            }else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
            }
        }else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
        }
    }
    //跳转图库
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != this.RESULT_CANCELED){
            switch (requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }

        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(this,profile_image);
    }
}
