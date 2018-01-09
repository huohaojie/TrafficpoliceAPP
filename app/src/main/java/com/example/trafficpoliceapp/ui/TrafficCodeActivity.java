package com.example.trafficpoliceapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.Traffic;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Hello World on 2017/12/19.
 */

public class TrafficCodeActivity extends BaseActivity implements View.OnClickListener {
    private EditText query_code;
    private EditText et_code;
    private EditText et_code_score;
    private EditText et_done;
    private EditText et_violation;
    private EditText et_law;

    private Button btn_code_query;
    private Button btn_know;

    private LinearLayout linearLayout_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        initView();
    }

    private void initView() {
        linearLayout_code = (LinearLayout) findViewById(R.id.linearLayout_code);

        btn_code_query = (Button) findViewById(R.id.btn_code_query);
        btn_code_query.setOnClickListener(this);

        btn_know = (Button) findViewById(R.id.btn_know);
        btn_know.setOnClickListener(this);

        query_code = (EditText) findViewById(R.id.query_code);
        et_code = (EditText) findViewById(R.id.et_code);
        et_code_score = (EditText) findViewById(R.id.et_code_score);
        et_done = (EditText) findViewById(R.id.et_done);
        et_violation = (EditText) findViewById(R.id.et_violation);
        et_law = (EditText) findViewById(R.id.et_law);

        //默认是不可点击的/不可输入
        setEnabled(false);
    }
    private void setEnabled(boolean is) {
        et_code.setEnabled(is);
        et_code_score.setEnabled(is);
        et_done.setEnabled(is);
        et_violation.setEnabled(is);
        et_law.setEnabled(is);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_code_query:
                //1、拿到输入框的值
                String code = query_code.getText().toString();
                //2、判断是否为空
                if(!TextUtils.isEmpty(code)){
                    //3、查询
                    BmobQuery<Traffic> query = new BmobQuery<Traffic>();
                    //查询
                    query.addWhereEqualTo("code", code);

                    query.findObjects(new FindListener<Traffic>() {
                        @Override
                        public void done(List<Traffic> object, BmobException e) {
                            if ( e==null){
//
                                //设置具体的值
                                et_code.setText(object.get(0).getCode());
                                et_code_score.setText(object.get(0).getScore()+"");
                                et_done.setText(object.get(0).getDone());
                                et_violation.setText(object.get(0).getViolation());
                                et_law.setText(object.get(0).getLaw());
                                if (et_code != null){
                                    Toast.makeText(TrafficCodeActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                                    linearLayout_code.setVisibility(View.VISIBLE);

                                }
                            }else{
                                Toast.makeText(TrafficCodeActivity.this, "不存在此代码，请确认代码是否正确", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_know:
                query_code.setText("");
                linearLayout_code.setVisibility(View.GONE);
                break;
        }
    }
}
