package com.example.trafficpoliceapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.Driver;
import com.example.trafficpoliceapp.entity.RecognizeResult;
import com.example.trafficpoliceapp.utils.YiYuanApiUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Project     :     TrafficpoliceAPP
 * Date        :     17/12/17
 */

public class ChePaiActivity extends BaseActivity {
  public static final int REQUEST_CODE = 100;

  private List<Uri> mChosen;
  private ImageView mPreview;
  private TextView mResult;
  private String number;


  private EditText et_platenum;
  private EditText et_car_name;
  private EditText et_num;
  private EditText et_loca;
  private EditText et_car;




  private LinearLayout linearLayout_information;

  private View view1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chepai);
    getSupportActionBar().setTitle("车牌识别");

    mPreview = (ImageView) findViewById(R.id.preview);
    mResult = (TextView) findViewById(R.id.recognize_result);

    linearLayout_information = (LinearLayout) findViewById(R.id.linearLayout_information);


    et_platenum = (EditText) findViewById(R.id.et_platenum);
    et_car_name = (EditText) findViewById(R.id.et_car_name);
    et_num = (EditText) findViewById(R.id.et_num);
    et_loca = (EditText) findViewById(R.id.et_loca);
    et_car = (EditText) findViewById(R.id.et_car);

    //默认是不可点击的/不可输入
    setEnabled(false);

    findViewById(R.id.btn_iknow).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        linearLayout_information.setVisibility(View.GONE);
      }
    });

    findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityCompat.requestPermissions(ChePaiActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
      }
    });

    findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mChosen == null || mChosen.isEmpty()) {
          Toast.makeText(ChePaiActivity.this, "还没有选择需要识别的图片", Toast.LENGTH_SHORT).show();
          return;
        }
        doRecognize(mChosen.get(0));
//        getInformation();
      }
    });

  }

  private void getInformation() {
    if (!TextUtils.isEmpty(number)){
      //3、查询
      BmobQuery<Driver> query = new BmobQuery<Driver>();
      //查询
      query.addWhereEqualTo("plateNum",number);

      query.findObjects(new FindListener<Driver>() {
        @Override
        public void done(List<Driver> object, BmobException e) {
          if ( e==null){
//
            //设置具体的值
            et_platenum.setText(object.get(0).getPlateNum());
            et_car_name.setText(object.get(0).getDriver());
            et_num.setText(object.get(0).getNum()+"");
            et_loca.setText(object.get(0).getLoca());
            et_car.setText(object.get(0).getCar());
            if (et_platenum != null){
              Toast.makeText(ChePaiActivity.this, "查询成功", Toast.LENGTH_SHORT).show();

              linearLayout_information.setVisibility(View.VISIBLE);

            }
          }else{
            Toast.makeText(ChePaiActivity.this, "不存在此信息", Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  private void setEnabled(boolean is) {
    et_platenum.setEnabled(is);
    et_car_name.setEnabled(is);
    et_num.setEnabled(is);
    et_loca.setEnabled(is);
    et_car.setEnabled(is);

  }

  private void doRecognize(Uri uri) {
    File file = new File(getRealPathFromURI(this, uri));
    OkHttpClient client = YiYuanApiUtils.CLIENT;
    Call call = client.newCall(YiYuanApiUtils.buildChepaiRequest(file));
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(ChePaiActivity.this, "识别出错", Toast.LENGTH_SHORT).show();
          }
        });
      }

      @Override
      public void onResponse(Call call, final Response response) throws IOException {
        String text = response.body().string();
        try {
          final RecognizeResult result = new Gson().fromJson(text, RecognizeResult.class);
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              if (result != null && result.getBody() != null && !TextUtils.isEmpty(result.getBody().getNum())) {
                number = result.getBody().getNum();
                mResult.setText("识别结果： " + number);
                getInformation();
                Toast.makeText(ChePaiActivity.this, "识别完成", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(ChePaiActivity.this, "识别出错", Toast.LENGTH_SHORT).show();
              }
            }
          });
        } catch (Exception e) {
          //ignore
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
      mChosen = Matisse.obtainResult(data);
      if (mChosen != null && !mChosen.isEmpty()) {
        Picasso.with(this)
                .load(mChosen.get(0))
                .into(mPreview);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE) {

      if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
        Matisse.from(ChePaiActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(false)
                .captureStrategy(new CaptureStrategy(true, "com.example.trafficpoliceapp.fileprovider"))
                .maxSelectable(1)
                .imageEngine(new PicassoEngine())
                .capture(true)
                .forResult(REQUEST_CODE);
      }
    }
  }

  public static void start(Context context) {
    Intent starter = new Intent(context, ChePaiActivity.class);
    context.startActivity(starter);
  }


  public static String getRealPathFromURI(Context context, Uri contentURI) {
    String result;
    Cursor cursor = context.getContentResolver().query(contentURI,
            new String[]{MediaStore.Images.ImageColumns.DATA},//
            null, null, null);
    if (cursor == null) result = contentURI.getPath();
    else {
      cursor.moveToFirst();
      int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
      result = cursor.getString(index);
      cursor.close();
    }
    return result;
  }

}
