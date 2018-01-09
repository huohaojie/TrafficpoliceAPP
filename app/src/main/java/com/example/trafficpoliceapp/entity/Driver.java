package com.example.trafficpoliceapp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Hello World on 2017/12/21.
 * 描述：车牌号获取信息
 */

public class Driver extends BmobObject{
    private String plateNum;
    private String driver;
    private int num;
    private String loca;
    private String car;

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
