package com.example.transverse;

import android.graphics.drawable.Drawable;
import android.text.Spanned;

import java.io.Serializable;

public class SelfHelp implements Serializable {

    String name;
    Spanned description;
    Drawable icon;
    //TODO: For later
    //int rating;
    //boolean isFavorite = false;
    //String notes;

    public SelfHelp() {
        name = "";
        description = null;
        icon = null;
    }

    public SelfHelp(String newName, Spanned newDesc) {
        name = newName;
        description = newDesc;
    }

    public SelfHelp(String newName, Spanned newDesc, Drawable newIcon) {
        name = newName;
        description = newDesc;
        icon = newIcon;
    }

    public String getName() {
        return name;
    }
}
