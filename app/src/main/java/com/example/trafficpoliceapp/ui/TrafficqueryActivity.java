package com.example.trafficpoliceapp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.Infor;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Hello World on 2017/12/18.
 * 描述：违章信息查询
 */

public class TrafficqueryActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_date;
    private EditText et_plate_num;
    private EditText et_location;
    private EditText et_kind;
    private EditText et_money;
    private EditText et_score;
    private EditText et_driver;
    private EditText query_traffic;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private Button btn_clear;
    private Button btn_update_edit;
    private Button btn_date;
    private Button btn_location;
    private int mYear;
    private int mMonth;
    private int mDay;
    private LinearLayout linearLayout_infor;
    private String obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);

        initView();
    }

    private void initView() {
        //获取当时的年月日
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        query_traffic = (EditText) findViewById(R.id.query_traffic);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_update_edit = (Button) findViewById(R.id.btn_update_edit);
        btn_update_edit.setOnClickListener(this);
        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date.setOnClickListener(this);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_location.setOnClickListener(this);

        linearLayout_infor = (LinearLayout) findViewById(R.id.linearLayout_infor);


        et_plate_num = (EditText) findViewById(R.id.et_plate_num);
        et_date = (EditText) findViewById(R.id.et_date);
        et_location = (EditText) findViewById(R.id.et_location);
        et_kind = (EditText) findViewById(R.id.et_kind);
        et_money = (EditText) findViewById(R.id.et_money);
        et_score = (EditText) findViewById(R.id.et_score);
        et_driver = (EditText) findViewById(R.id.et_driver);

        //默认是不可点击的/不可输入
        setEnabled(false);
    }

    private void setEnabled(boolean is) {
        et_plate_num.setEnabled(is);
        et_date.setEnabled(is);
        et_location .setEnabled(is);
        et_kind.setEnabled(is);
        et_money.setEnabled(is);
        et_score.setEnabled(is);
        et_driver.setEnabled(is);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                //1、拿到输入框的值
                String traffic = query_traffic.getText().toString();
                //2、判断是否为空
                if(!TextUtils.isEmpty(traffic)){
                    //3、查询
                    BmobQuery<Infor> query = new BmobQuery<Infor>();
                    //查询车牌号为traffic
                    query.addWhereEqualTo("plateNum", traffic);

                    query.findObjects(new FindListener<Infor>() {
                        @Override
                        public void done(List<Infor> object, BmobException e) {
                            if ( e==null){
//                              Toast.makeText(TrafficqueryActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                                 obj = object.get(0).getObjectId();

                                //设置具体的值
                                et_plate_num.setText(object.get(0).getPlateNum());
                                et_date.setText(object.get(0).getDate());
                                et_location.setText(object.get(0).getLocation());
                                et_kind.setText(object.get(0).getKind());
                                et_money.setText(object.get(0).getMoney()+"");
                                et_score.setText(object.get(0).getScore()+"");
                                et_driver.setText(object.get(0).getDriver());
                                if (et_plate_num != null){
                                    Toast.makeText(TrafficqueryActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                                    linearLayout_infor.setVisibility(View.VISIBLE);

                                }
                            }else{
                                Toast.makeText(TrafficqueryActivity.this, "不存在此条记录，请确认车牌号是否正确", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_edit:
                //更新数据
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


                            //更新数据
                            Infor infor = new Infor();
                            infor.setPlateNum(platenum);
                            infor.setDate(date);
                            infor.setLocation(location);
                            infor.setKind(kind);
                            infor.setMoney(Integer.parseInt(money));
                            infor.setScore(Integer.parseInt(score));
                            infor.setDriver(driver);

                            infor.update(obj, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if ( e == null ){
                                        setEnabled(false);
                                        btn_update_edit.setVisibility(View.GONE);
                                        btn_date.setVisibility(View.GONE);
                                        btn_location.setVisibility(View.GONE);
                                        Toast.makeText(TrafficqueryActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(TrafficqueryActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                    }else {
                        Toast.makeText(this, "扣分不能超过12分", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_update:
                setEnabled(true);
                btn_update_edit.setVisibility(View.VISIBLE);
                btn_date.setVisibility(View.VISIBLE);
                btn_location.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_date:
                //调用时间选择器
                new DatePickerDialog(this,onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.btn_location:
                Intent intent = new Intent(TrafficqueryActivity.this, LocationActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_delete:

                Infor infor = new Infor();
                infor.setObjectId(obj);
                infor.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if ( e == null ){

                            Toast.makeText(TrafficqueryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            linearLayout_infor.setVisibility(View.GONE);

                        }else {
                            Toast.makeText(TrafficqueryActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_clear:
                query_traffic.setText("");
                linearLayout_infor.setVisibility(View.GONE);
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
