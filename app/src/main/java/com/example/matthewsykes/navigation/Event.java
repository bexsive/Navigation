package com.example.matthewsykes.navigation;

/**
 * Created by Matthew Sykes on 11/6/2016.
 */

public class Event {

    public Event(int image_id, String userName, String date, String time){
        this.setImage_id(image_id);
        this.setUserName(userName);
        this.setDate(date);
        this.setTime(time);
    }
    private int image_id;
    private String userName, date, time;

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}