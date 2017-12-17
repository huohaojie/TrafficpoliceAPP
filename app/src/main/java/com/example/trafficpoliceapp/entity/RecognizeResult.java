package com.example.trafficpoliceapp.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Project     :     TrafficpoliceAPP
 * Author      :     Cricin
 * Date        :     17/12/17
 */

public class RecognizeResult {


  @SerializedName("showapi_res_code")
  private int code;
  @SerializedName("showapi_res_error")
  private String errString;
  @SerializedName("showapi_res_body")
  private Body body;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getErrString() {
    return errString;
  }

  public void setErrString(String errString) {
    this.errString = errString;
  }

  public Body getBody() {
    return body;
  }

  public void setBody(Body body) {
    this.body = body;
  }

  public static class Position {

    private String top;
    private String left;
    private String bottom;
    private String right;

    public void setTop(String top) {
      this.top = top;
    }

    public String getTop() {
      return top;
    }

    public void setLeft(String left) {
      this.left = left;
    }

    public String getLeft() {
      return left;
    }

    public void setBottom(String bottom) {
      this.bottom = bottom;
    }

    public String getBottom() {
      return bottom;
    }

    public void setRight(String right) {
      this.right = right;
    }

    public String getRight() {
      return right;
    }

  }

  public static class Body {

    private String msg;
    private boolean flag;
    private String color;
    private String light;
    private String num;
    private String confidence;
    private Position position;
    private String type;
    private int ret_code;
    private String move_direction;

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public String getMsg() {
      return msg;
    }

    public void setFlag(boolean flag) {
      this.flag = flag;
    }

    public boolean getFlag() {
      return flag;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getColor() {
      return color;
    }

    public void setLight(String light) {
      this.light = light;
    }

    public String getLight() {
      return light;
    }

    public void setNum(String num) {
      this.num = num;
    }

    public String getNum() {
      return num;
    }

    public void setConfidence(String confidence) {
      this.confidence = confidence;
    }

    public String getConfidence() {
      return confidence;
    }

    public void setPosition(Position position) {
      this.position = position;
    }

    public Position getPosition() {
      return position;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }

    public void setRet_code(int ret_code) {
      this.ret_code = ret_code;
    }

    public int getRet_code() {
      return ret_code;
    }

    public void setMove_direction(String move_direction) {
      this.move_direction = move_direction;
    }

    public String getMove_direction() {
      return move_direction;
    }

  }


}
