package com.example.matthewsykes.navigation;

/**
 * Created by Matthew Sykes on 11/6/2016.
 */

public class Event {

    private String userName, date, time;

    public Event(String username, String date, String time){
        this.setUserName(username);
        this.setDate(date);
        this.setTime(time);
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
