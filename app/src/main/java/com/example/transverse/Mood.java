package com.example.transverse;

import java.util.ArrayList;

public class Mood {
    private int moodLevel;
    private ArrayList<String> tags;

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

    private String journal;
    private ArrayList<String> triggers;



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
