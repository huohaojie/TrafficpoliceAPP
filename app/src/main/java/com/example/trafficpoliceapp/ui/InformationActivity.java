package com.example.trafficpoliceapp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.Infor;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Hello World on 2017/12/16.
 * 描述：违章信息录入
 */

public class InformationActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_date;
    private Button btn_location;
    private int mYear;
    private int mMonth;
    private int mDay;
    private EditText et_date;
    private EditText et_plate_num;
    private EditText et_location;
    private EditText et_kind;
    private EditText et_money;
    private EditText et_score;
    private EditText et_driver;
    private CheckBox promise;
    private Button record_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        initView();
    }

    private void initView() {
        //获取当时的年月日
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date.setOnClickListener(this);

        btn_location = (Button) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(this);

        et_plate_num = (EditText) findViewById(R.id.et_plate_num);
        et_date = (EditText) findViewById(R.id.et_date);
        et_location = (EditText) findViewById(R.id.et_location);
        et_kind = (EditText) findViewById(R.id.et_kind);
        et_money = (EditText) findViewById(R.id.et_money);
        et_score = (EditText) findViewById(R.id.et_score);
        et_driver = (EditText) findViewById(R.id.et_driver);
        promise = (CheckBox) findViewById(R.id.promise);
        record_btn = (Button) findViewById(R.id.record_btn);
        record_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_date:
                //调用时间选择器
                new DatePickerDialog(this,onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.btn_location:
                Intent intent = new Intent(InformationActivity.this, LocationActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.record_btn:

                //获取输入框的值、
                String platenum = et_plate_num.getText().toString().trim();
                String date = et_date.getText().toString().trim();
                String location = et_location.getText().toString().trim();
                String kind = et_kind.getText().toString().trim();
                String money = et_money.getText().toString().trim();
                String score = et_score.getText().toString().trim();
                String driver = et_driver.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(platenum) & !TextUtils.isEmpty(date) & !TextUtils.isEmpty(location) &
                        !TextUtils.isEmpty(kind) & !TextUtils.isEmpty(money) & !TextUtils.isEmpty(score) &
                        !TextUtils.isEmpty(driver) ){

                    //判断score 是否超过12
                    if ( Integer.parseInt(score) < 13){

                        //是否已经点击CheckBox
                        if (promise.isChecked()){

                            //录入
                            Infor infor = new Infor();
                            infor.setPlateNum(platenum);
                            infor.setDate(date);
                            infor.setLocation(location);
                            infor.setKind(kind);
                            infor.setMoney(Integer.parseInt(money));
                            infor.setScore(Integer.parseInt(score));
                            infor.setDriver(driver);

                            infor.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(InformationActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(InformationActivity.this, "录入失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(this, "请确认是否已经点击我承诺声明", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "扣分不能超过12分", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            et_date.setText(days);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 &&resultCode == RESULT_OK) {
            et_location.setText(data.getStringExtra("location"));
        }

    }
}
