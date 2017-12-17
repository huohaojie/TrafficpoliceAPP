package com.example.trafficpoliceapp.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Project     :     TrafficpoliceAPP
 * Author      :     Cricin
 * Date        :     17/12/17
 */

public class YiYuanApiUtils {

  public static final String APP_ID = "52301";
  public static final String SECRET_KEY = "63baa2fdf9544c65951fba22a974b100";
  public static final DateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
  public static final OkHttpClient CLIENT = new OkHttpClient.Builder().build();

  //交通限行request
  public static Request buildRestrictionQueryRequest(String cityName, int date/*1是当天数据，2是明天，以此类推*/) {
    FormBody body = new FormBody.Builder()
            .add("showapi_appid", APP_ID)
            .add("showapi_sign", SECRET_KEY)
            .add("showapi_timestamp", FORMAT.format(new Date()))
            .add("showapi_res_gzip", String.valueOf(0))
            .add("city", cityName)
            .add("dayNum", String.valueOf(date))
            .build();
    Request out = new Request.Builder()
            .post(body)
            .url("http://route.showapi.com/841-1")
            .build();
    return out;
  }

  //违章request
  public static Request buildBreakLowQueryRequest(String cityName, int date/*1是当天数据，2是明天，以此类推*/) {
    FormBody body = new FormBody.Builder()
            .add("showapi_appid", APP_ID)
            .add("showapi_sign", SECRET_KEY)
            .add("showapi_timestamp", FORMAT.format(new Date()))
            .add("showapi_res_gzip", String.valueOf(0))
            .add("cityName", cityName)
            .add("dayNum", String.valueOf(date))
            .build();
    Request out = new Request.Builder()
            .post(body)
            .url("http://route.showapi.com/1399-3")
            .build();
    return out;
  }


  public static Request buildChepaiRequest(File img) {
    MultipartBody body = new MultipartBody.Builder()
            .addFormDataPart("showapi_appid", APP_ID)
            .addFormDataPart("showapi_sign", SECRET_KEY)
            .addFormDataPart("showapi_timestamp", FORMAT.format(new Date()))
            .addFormDataPart("showapi_res_gzip", String.valueOf(0))
            .addFormDataPart("imgData", "a.jpg", RequestBody.create(MediaType.parse(""), img))
            .build();

    Request out = new Request.Builder()
            .post(body)
            .url("http://route.showapi.com/1333-3")
            .build();
    return out;
  }


}
