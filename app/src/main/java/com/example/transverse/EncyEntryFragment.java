package com.example.transverse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class EncyEntryFragment extends Fragment {
    private static final String ENTRY_KEY = "entry_key";
    TextView encyEntryContent;
    private static String stringID;

    public static EncyEntryFragment newInstance(String ID) {

        EncyEntryFragment f = new EncyEntryFragment();
        stringID = ID;
        //Bundle b = new Bundle();
        // b.putString("currentEntry", currentEntry.toString());
        //b.putSerializable(ENTRY_KEY, (Serializable) currentString);
        //f.setArguments(b);
        //thisEntry = currentEntry;

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //thisEntry = (UserEntry) getArguments().getSerializable(
               // ENTRY_KEY);
        View v =  inflater.inflate(R.layout.fragment_ency_entry, container, false);

        //((TextView) v.findViewById(R.id.user_entry_subtitle)).setText("uwu");


        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Set values of fields to be the value of the user entry

        encyEntryContent = (TextView) getView().findViewById(R.id.textContent);
        //String fullStringID = "R.string." + stringID;
        String packageName = getContext().getPackageName();
        int stringIDInt = getContext().getResources().getIdentifier(stringID, "string", packageName);
        String fullString = getContext().getString(stringIDInt);

        encyEntryContent.setText(fullString);

    }


}
