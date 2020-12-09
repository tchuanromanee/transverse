package com.example.transverse;

import java.io.Serializable;
import java.util.Arrays;

public class Dysphoria implements Serializable {
    //private int type;
    private boolean isPhysical;
    private boolean isMental;
    private boolean isSocial;
    private int intensity;

    public Dysphoria() {
        isPhysical = false;
        isMental = false;
        isSocial = false;
        intensity = -1;
    }
    public Dysphoria(boolean isPhysical, boolean isMental, boolean isSocial, int intensity){
        this.isPhysical = isPhysical;
        this.isMental = isMental;
        this.isSocial = isSocial;
        this.intensity = intensity;
    }

    public String toString() {
        String printString = "";

        printString += "Type: " + "\n";
        printString += "Intensity: " + intensity + "\n";

        return printString;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public void setPhysical(boolean isPhysical) {
        this.isPhysical = isPhysical;
    }


    public boolean isMental() {
        return isMental;
    }

    public void setMental(boolean isMental) {
        this.isMental = isMental;
    }


    public boolean isSocial() {
        return isSocial;
    }

    public void setSocial(boolean isSocial) {
        this.isSocial = isSocial;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
