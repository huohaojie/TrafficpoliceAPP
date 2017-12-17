package com.example.trafficpoliceapp.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Hello World on 2017/12/5.
 * 描述：用户属性
 */

public class MyUser extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;
    private int workId;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }
}
