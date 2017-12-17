package com.example.trafficpoliceapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.trafficpoliceapp.R;
import com.example.trafficpoliceapp.adapter.WeChatAdapter;
import com.example.trafficpoliceapp.entity.WeChatData;
import com.example.trafficpoliceapp.utils.L;
import com.example.trafficpoliceapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hello World on 2017/12/12.
 * 描述：微信精选
 */

public class WechatActivity extends AppCompatActivity{
    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        //解析接口http://v.juhe.cn/weixin/query?key=您申请的KEY
        String url="http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;//+ "&ps=100"

        //解析接口
        RxVolley.get(url,new HttpCallback(){

            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                L.i("wechat json:" + t);
                parsingJson(t);
            }

        });
        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:" + position);
                Intent intent = new Intent(WechatActivity.this, WebActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
    }
    //解析json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonresult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();

                String titlr = json.getString("title");
                String url = json.getString("url");

                data.setTitle(titlr);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);

                mListTitle.add(titlr);
                mListUrl.add(url);
            }
            WeChatAdapter adapter = new WeChatAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
