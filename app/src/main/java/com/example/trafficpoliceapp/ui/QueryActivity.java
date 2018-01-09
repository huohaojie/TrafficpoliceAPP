package com.example.trafficpoliceapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.entity.XianXing;
import com.example.trafficpoliceapp.utils.YiYuanApiUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Project     :     TrafficpoliceAPP
 * Date        :     17/12/17
 */

public class QueryActivity extends BaseActivity {

  private TextView mQueryResult;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_query);
    getSupportActionBar().setTitle("限行查询");

    mQueryResult = (TextView) findViewById(R.id.query_result);

    final EditText editText = (EditText) findViewById(R.id.query_input);
    editText.setHint("输入要查询的限行城市");
    findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String input = editText.getText().toString();
        if (!TextUtils.isEmpty(input)) {
          doQuery(input);
        }
      }
    });
  }

  private void doQuery(String input) {
    final OkHttpClient client = YiYuanApiUtils.CLIENT;
    Call call = client.newCall(YiYuanApiUtils.buildRestrictionQueryRequest(input, 1));

    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(QueryActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
          }
        });
      }

      @Override
      public void onResponse(Call call, final Response response) throws IOException {
        final String responseText = response.body().string();
        final XianXing xianXing = new Gson().fromJson(responseText, XianXing.class);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (xianXing.getContent() != null) {
              mQueryResult.setText(xianXing.getContent().toString());
            }
            Toast.makeText(QueryActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
//            mQueryResult.setText(responseText);
          }
        });
      }
    });
  }

  public static void start(Context context) {
    Intent starter = new Intent(context, QueryActivity.class);
    context.startActivity(starter);
  }

}
