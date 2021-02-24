package com.transverse.transverse;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EncyEntryFragment extends Fragment {
    private static final String ENTRY_KEY = "entry_key";
    TextView encyEntryContent, entryTitle;
    private static String stringID;
    private static String title;

    public static EncyEntryFragment newInstance(String ID, String newTitle) {

        EncyEntryFragment f = new EncyEntryFragment();
        stringID = ID;
        title = newTitle;
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

        entryTitle = (TextView) getView().findViewById(R.id.entryTitle);
        entryTitle.setText(title);
        encyEntryContent = (TextView) getView().findViewById(R.id.textContent);
        //String fullStringID = "R.string." + stringID;
        String packageName = getContext().getPackageName();
        int stringIDInt = getContext().getResources().getIdentifier(stringID, "string", packageName);
        String fullString = getContext().getString(stringIDInt);
        Spanned htmlText = Html.fromHtml(fullString);
        encyEntryContent.setText(htmlText, TextView.BufferType.SPANNABLE);

    }




}
