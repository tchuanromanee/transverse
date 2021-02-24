package com.transverse.transverse;

import android.graphics.drawable.Drawable;
import android.text.Spanned;

import java.io.Serializable;

public class SelfHelp implements Serializable {
    String id;
    String name;
    Spanned description;
    Drawable icon;
    //TODO: For later
    //int rating;
    //boolean isFavorite = false;
    //String notes;

    public SelfHelp() {
        id = "";
        name = "";
        description = null;
        icon = null;
    }

    public SelfHelp(String newName, String newId, Spanned newDesc) {
        id = newId;
        name = newName;
        description = newDesc;
    }

    public SelfHelp(String newName, String newId, Spanned newDesc, Drawable newIcon) {
        name = newName;
        id = newId;
        description = newDesc;
        icon = newIcon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public Spanned getDescription() {
        return description;
    }

}
