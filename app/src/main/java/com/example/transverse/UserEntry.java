package com.example.transverse;

public class UserEntry {
    //Date and time
    private String time;
    private String date;

    //Dysphoria vals
    Dysphoria dysphoria;

    //Mood val
    Mood mood;

    public UserEntry() {
        time = "";
        date = "";
        dysphoria = null;
        mood = null;
    }

    //Constructors
    public UserEntry(String time, String date, Dysphoria dysphoriaEntry, Mood mood) {
        this.time = time;
        this.date = date;
        dysphoria = dysphoriaEntry;
        this.mood = mood;
    }

    public UserEntry(String time, String date, Mood mood) { //No dysphoria present at this time
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

    public int getDateInt () {
        return 0;
    }

    public Dysphoria getDysphoria () {
        return dysphoria;
    }

    public Mood getMood() {
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

    public String toString() {
        String finalString = "Entry\n";
        finalString += "Time: " + time + "\n";
        finalString += "Date: " + date + "\n";
        if (dysphoria != null) {
            finalString += dysphoria.toString() + "\n";
        }
        if (mood != null) {
            finalString += mood.toString() + "\n";
        }
        return finalString;
    }


}
