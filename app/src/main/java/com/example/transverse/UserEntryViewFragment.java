package com.example.transverse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;

import java.io.Serializable;
import java.util.Map;

public class UserEntryViewFragment extends Fragment {
    Button deleteButton;
    private static final String ENTRY_KEY = "entry_key";
    private static UserEntry thisEntry;

    public static UserEntryViewFragment newInstance(UserEntry currentEntry) {

        UserEntryViewFragment f = new UserEntryViewFragment();

        Bundle b = new Bundle();
       // b.putString("currentEntry", currentEntry.toString());
        b.putSerializable(ENTRY_KEY, (Serializable) currentEntry);
        f.setArguments(b);
        //thisEntry = currentEntry;

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisEntry = (UserEntry) getArguments().getSerializable(
                ENTRY_KEY);
        View v =  inflater.inflate(R.layout.fragment_user_entry_view, container, false);

        //((TextView) v.findViewById(R.id.user_entry_subtitle)).setText("uwu");


        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        deleteButton = (Button) getView().findViewById(R.id.deleteEntryButton);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //Ask for confirmation from user?
                new AlertDialog.Builder(getContext())
                       // .setTitle("Title")
                        .setMessage("Do you really want to delete this entry?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getContext(), "Entry deleted", Toast.LENGTH_SHORT).show();
                                deleteEntry();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });
    }

    public void deleteEntry() {
        //delete the entry from array list
        ((MainActivity) getActivity()).allEntries.remove(thisEntry);
        //delete the entry from json by writing the new array w/o entry to JSON
        ((MainActivity) getActivity()).allEntriesToJSON(thisEntry);

        // Reload stats fragment
        Fragment nextFrag= null;
        nextFrag = new StatisticsFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFrag,"tag");
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.detach(nextFrag);
        fragmentTransaction.attach(nextFrag);
        fragmentTransaction.commit();
    }


}
