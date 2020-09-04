package com.example.transverse;

public class Entry {
    //Date and time
    private String time;
    private String date;

    //Dysphoria vals
    Dysphoria dysphoria;

    //Mood val
    Mood mood;

    public Entry() {
        time = "";
        date = "";
        dysphoria = null;
        mood = null;
    }

    //Constructors
    public Entry(String time, String date, Dysphoria dysphoriaEntry, Mood mood) {
        this.time = time;
        this.date = date;
        dysphoria = dysphoriaEntry;
        this.mood = mood;
    }

    public Entry(String time, String date, Mood mood) { //No dysphoria present at this time
        this.time = time;
        this.date = date;
        dysphoria = null;
        this.mood = mood;
    }

    //Getters
    public String getTime() {
        return time;
    }

    public String getDate () {
        return date;
    }

    public Dysphoria getDysphoria () {
        return dysphoria;
    }

    public Mood setMood() {
        return mood;
    }

    //Setters
    public void setTime(String time) {
        this.time = time;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public void setDysphoria (Dysphoria dysphoria) {
        this.dysphoria = dysphoria;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }


}
