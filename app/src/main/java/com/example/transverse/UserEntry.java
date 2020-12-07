package com.example.transverse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserEntry implements Serializable {
    //Date and time
    private long timeAndDate;

    //Dysphoria vals
    Dysphoria dysphoria;

    //Mood val
    Mood mood;

    public UserEntry() {
        timeAndDate = 0;
        dysphoria = null;
        mood = null;
    }

    //Constructors


    //Getters
    public long getTimeAndDate() {return timeAndDate;}

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

    public void setTimeAndDate(long tAndD) {timeAndDate = tAndD;}

    public void setDysphoria (Dysphoria dysphoria) {
        this.dysphoria = dysphoria;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");// HH:mm");
        Date resultdate = new Date(timeAndDate);
        //System.out.println(sdf.format(resultdate));

        //DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
        //return mFormat.format(value) + " $";
        return sdf.format(resultdate);
    }

    public String toString() {
        String finalString = "Entry\n";
        //finalString += "Time: " + time + "\n";
        //finalString += "Date: " + date + "\n";
        finalString += "Unformatted time and date: " + timeAndDate + "\n";
        if (dysphoria != null) {
            finalString += dysphoria.toString() + "\n";
        }
        if (mood != null) {
            finalString += mood.toString() + "\n";
        }
        return finalString;
    }


}
