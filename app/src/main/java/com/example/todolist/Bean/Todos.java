package com.example.todolist.Bean;

import cn.bmob.v3.BmobObject;

public class Todos extends BmobObject {

    private String title;
    private String desc;
    private String date;
    private String time;
    private int id,isAlerted,isRepeat,imgId;
    private long remindTime,remindTimeNoDay;

    public Todos() {

    }

    public long getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(long remindTime) {
        this.remindTime = remindTime;
    }

    public long getRemindTimeNoDay() {
        return remindTimeNoDay;
    }

    public void setRemindTimeNoDay(long remindTimeNoDay) {
        this.remindTimeNoDay = remindTimeNoDay;
    }

    public Todos(String title,String desc,String date,String time,int imgId,int isRepeat){
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.imgId = imgId;
        this.isRepeat = isRepeat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAlerted() {
        return isAlerted;
    }

    public void setIsAlerted(int isAlerted) {
        this.isAlerted = isAlerted;
    }

    public int getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(int isRepeat) {
        this.isRepeat = isRepeat;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

}
