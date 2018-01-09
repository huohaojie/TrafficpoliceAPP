package com.example.trafficpoliceapp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Hello World on 2017/12/19.
 * 描述：代码查询库
 */

public class Traffic extends BmobObject {
    private String code;
    private int score;
    private String done;
    private String violation;
    private String law;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }
}


