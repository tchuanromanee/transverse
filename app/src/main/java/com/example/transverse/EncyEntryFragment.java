package com.example.transverse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

public class EncyEntryFragment {
    private static final String ENTRY_KEY = "entry_key";

    private static String currString;

    public static EncyEntryFragment newInstance(int ID) {

        EncyEntryFragment f = new EncyEntryFragment();
        currString = Integer.toString(ID);
        //Bundle b = new Bundle();
        // b.putString("currentEntry", currentEntry.toString());
        //b.putSerializable(ENTRY_KEY, (Serializable) currentString);
        //f.setArguments(b);
        //thisEntry = currentEntry;

        return f;
    }


}
