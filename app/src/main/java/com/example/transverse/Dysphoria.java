package com.example.transverse;

import java.util.Arrays;

public class Dysphoria {
    private int type;
    private int intensity;

    public Dysphoria() {
        type = -1;
        intensity = -1;
    }
    public Dysphoria(int type, int intensity){
        this.type = type;
        this.intensity = intensity;
    }

    public String toString() {
        String printString = "";

        printString += "Type: " + type + "\n";
        printString += "Intensity: " + intensity + "\n";

        return printString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
