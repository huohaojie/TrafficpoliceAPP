package com.example.trafficpoliceapp.entity;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Project     :     TrafficpoliceAPP
 * Author      :     Cricin
 * Date        :     17/12/17
 */

public class XianXing {

  @SerializedName("showapi_res_code")
  private int code;

  @SerializedName("showapi_res_error")
  private String errString;

  @SerializedName("showapi_res_body")
  private Content content;

  public void setCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public void setErrString(String errString) {
    this.errString = errString;
  }

  public String getErrString() {
    return errString;
  }

  public void setContent(Content content) {
    this.content = content;
  }

  public Content getContent() {
    return content;
  }

  public static class Details {

    private String time;
    private String place;
    private String info;

    public void setTime(String time) {
      this.time = time;
    }

    public String getTime() {
      return time;
    }

    public void setPlace(String place) {
      this.place = place;
    }

    public String getPlace() {
      return place;
    }

    public void setInfo(String info) {
      this.info = info;
    }

    public String getInfo() {
      return info;
    }


    @Override
    public String toString() {
      return
              "----> 时间：\n" + time +
                      "\n----> 地点：" + place +
                      "\n----> 信息：" + info + "\n";
    }
  }


  public static class Content {

    private Date date;
    private String msg;
    private String cityName;
    private String city;
    private String weekDay;
    private List<Details> details;
    private String ret_code;

    public void setDate(Date date) {
      this.date = date;
    }

    public Date getDate() {
      return date;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public String getMsg() {
      return msg;
    }

    public void setCityName(String cityName) {
      this.cityName = cityName;
    }

    public String getCityName() {
      return cityName;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getCity() {
      return city;
    }

    public void setWeekDay(String weekDay) {
      this.weekDay = weekDay;
    }

    public String getWeekDay() {
      return weekDay;
    }

    public void setDetails(List<Details> details) {
      this.details = details;
    }

    public List<Details> getDetails() {
      return details;
    }

    public void setRet_code(String ret_code) {
      this.ret_code = ret_code;
    }

    public String getRet_code() {
      return ret_code;
    }

    @Override
    public String toString() {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

      StringBuilder builder = new StringBuilder(200);
      builder.append("信息：").append(msg).append('\n');
              if(date != null){
                builder.append("日期：").append(format.format(date)).append('\n');
              }

             builder .append("城市：").append(cityName).append('\n')
              .append("星期：").append(weekDay).append('\n')
              .append("返回码：").append(ret_code).append('\n')
              .append("限行信息：").append('\n');
      if (details != null) {
        for (int i = 0; i < details.size(); i++) {
          Details detail = details.get(i);
          builder.append(i + " \n");
          builder.append(detail);
        }
      }

      return builder.toString();
    }
  }


}
