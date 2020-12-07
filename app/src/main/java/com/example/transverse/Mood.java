package com.example.transverse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Mood implements Serializable {
    private int moodLevel;
    private ArrayList<String> tags;
    private String journal;
    private ArrayList<String> triggers;

    public Mood() {
        moodLevel = -1;
        tags = new ArrayList<String>();
        journal = "";
        triggers = new ArrayList<String>();
    }

    public String toString() {
        String printString = "";

        printString += "Mood level: " + moodLevel + "\n";
        printString += "Tags: " + Arrays.toString(tags.toArray()) + "\n";
        printString += "Journal: " + journal + "\n";
        printString += "Triggers: " + Arrays.toString(triggers.toArray()) + "\n";

        return printString;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTriggers() {
        return triggers;
    }

    public void setTriggers(ArrayList<String> triggers) {
        this.triggers = triggers;
    }

    public void addTag(String newTag) {
        tags.add(newTag);
    }

    public void addTrigger(String newTrigger) {
        triggers.add(newTrigger);
    }


    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getMoodLevel() {
        return moodLevel;
    }

    public void setMoodLevel(int moodLevel) {
        this.moodLevel = moodLevel;
    }

    public Mood(int moodLevel, ArrayList<String> tags, String journal, ArrayList<String> triggers) {
        this.moodLevel = moodLevel;
        this.tags = tags;
        this.journal = journal;
        this.triggers = triggers;
    }


}
