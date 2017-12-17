package com.example.trafficpoliceapp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Hello World on 2017/12/17.
 * 描述：违章信息属性
 */

public class Infor extends BmobObject{
    private String plateNum;
    private String date;
    private String location;
    private String kind;
    private int money;
    private int score;
    private String driver;

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
