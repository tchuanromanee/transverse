package com.transverse.transverse;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class SelfHelpViewFragment extends Fragment {
    //Button deleteButton;
    private static final String ENTRY_KEY = "entry_key";
    private static SelfHelp thisSelfHelp;//, editedEntry; //
   // EditText notes;
    TextView description;
    ImageView selfHelpImageView;
    Drawable icon;
   // Button saveChangesButton;

    public static SelfHelpViewFragment newInstance(SelfHelp currentSelfHelp) {

        SelfHelpViewFragment f = new SelfHelpViewFragment();

        Bundle b = new Bundle();
        // b.putString("currentEntry", currentEntry.toString());
        b.putSerializable(ENTRY_KEY, (Serializable) currentSelfHelp);
        f.setArguments(b);
        //thisEntry = currentEntry;

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisSelfHelp = (SelfHelp) getArguments().getSerializable(
                ENTRY_KEY);
        View v =  inflater.inflate(R.layout.fragment_self_help_view, container, false);

        description = ((TextView) v.findViewById(R.id.self_help_description));
        selfHelpImageView = (ImageView) v.findViewById(R.id.self_help_fragment_img);

        description.setText(thisSelfHelp.getDescription());

        String uri = "@drawable/ic_" + thisSelfHelp.getID().toLowerCase();  // where myresource (without the extension) is the file

        int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());

        //imageview = (ImageView)findViewById(R.id.imageView);
        Drawable res = getContext().getResources().getDrawable(imageResource);
        selfHelpImageView.setImageDrawable(res);

        //viewHolder.imageView.setImageResource(R.drawable.ic_shhobby);
        // Setting image view is also simple
        //Get image ID, depending on the mood
        //Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart);

       // selfHelpImageView.setImageResource(R.drawable.ic_heart);


        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String selfHelpName = thisSelfHelp.getName();
        ((MainActivity) getActivity()).updateTitle(selfHelpName);
        //Set values of fields to be the value of the user entry

        //notes = (EditText) getView().findViewById(R.id.editTextNotes);
        //saveChangesButton = (Button) getView().findViewById(R.id.saveChangesButton);


        /*saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                saveChanges();
            }
        });*/

//        journal.setText(thisEntry.getMood().getJournal());

        /*deleteButton.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    public void saveChanges() {
        // Pull values from fields
        // Pull values from form

        //String journalString = journal.getText().toString();
        //if "no dysphoria" is not selected

       // editedEntry = new SelfHelp();
        //Add new entry to arraylist
       //editedEntry.setTimeAndDate(timeAndDate);

/*
        //Edit this entry in the array list
        if (((MainActivity) getActivity()).allEntries.contains(thisEntry)) {
            int thisEntryIndex = ((MainActivity) getActivity()).allEntries.indexOf((thisEntry));//get(((MainActivity) getActivity()).allEntries.indexOf(thisEntry));
            ((MainActivity) getActivity()).allEntries.set(thisEntryIndex, editedEntry);
        }
        //Edit this entry in the JSON
        ((MainActivity) getActivity()).editEntryinJSON(thisEntry, editedEntry);

        // Reload stats fragment
        Fragment nextFrag= null;

        nextFrag = new StatisticsFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFrag,"stats_frag");
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.detach(nextFrag);
        fragmentTransaction.attach(nextFrag);
        fragmentTransaction.commit();*/
    }

    /*public void deleteEntry() {
        //delete the entry from array list
        ((MainActivity) getActivity()).allEntries.remove(thisEntry);
        //delete the entry from json by writing the new array w/o entry to JSON
        ((MainActivity) getActivity()).removeFromJSON(thisEntry);

        // Reload stats fragment
        Fragment nextFrag= null;

        nextFrag = new StatisticsFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFrag,"stats_frag");
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.detach(nextFrag);
        fragmentTransaction.attach(nextFrag);
        fragmentTransaction.commit();

    }*/
}
