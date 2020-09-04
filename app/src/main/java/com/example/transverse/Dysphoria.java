package com.example.transverse;

public class Dysphoria {

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

    private int type;
    private int intensity;

    public Dysphoria(int type, int intensity){
        this.type = type;
        this.intensity = intensity;
    }
}
