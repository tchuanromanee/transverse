package com.example.transverse;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class UserEntryViewFragment extends Fragment {
    Button deleteButton;
    private static final String ENTRY_KEY = "entry_key";
    private static UserEntry thisEntry, editedEntry; //
    EditText autoD8, autoTime, journal;
    ProgressBar moodSeekbar, dysphoriaSeekbar;
    Button saveChangesButton;
    //TODO: later, dynamically generate these buttons for custom tags and triggers
    ToggleButton trigger1, trigger2, trigger3, trigger4;
    ToggleButton tag1, tag2, tag3;
    ToggleButton physicalDysphoriaButton, mentalDysphoriaButton, socialDysphoriaButton, noDysphoriaButton;
    Calendar dialogueCal;

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

        //Set values of fields to be the value of the user entry

        autoD8 = (EditText) getView().findViewById(R.id.editTextDate);
        autoTime = (EditText) getView().findViewById(R.id.editTextTime);
        journal = (EditText) getView().findViewById(R.id.editTextJournal);
        moodSeekbar = (SeekBar) getView().findViewById(R.id.moodSeekbar);
        dysphoriaSeekbar = (SeekBar) getView().findViewById(R.id.dysphoriaSeekbar);
        saveChangesButton = (Button) getView().findViewById(R.id.saveChangesButton);
        deleteButton = (Button) getView().findViewById(R.id.deleteEntryButton);



        trigger1 = (ToggleButton) getView().findViewById(R.id.triggerButton1);
        trigger2 = (ToggleButton) getView().findViewById(R.id.triggerButton2);
        trigger3 = (ToggleButton) getView().findViewById(R.id.triggerButton3);
        trigger4 = (ToggleButton) getView().findViewById(R.id.triggerButton4);


        tag1 = (ToggleButton) getView().findViewById(R.id.tagButton1);
        tag2 = (ToggleButton) getView().findViewById(R.id.tagButton2);
        tag3 = (ToggleButton) getView().findViewById(R.id.tagButton3);

        physicalDysphoriaButton = (ToggleButton) getView().findViewById(R.id.physicalDysphoriaButton);
        mentalDysphoriaButton = (ToggleButton) getView().findViewById(R.id.mentalDysphoriaButton);
        socialDysphoriaButton = (ToggleButton) getView().findViewById(R.id.socialDysphoriaButton);
        noDysphoriaButton = (ToggleButton) getView().findViewById(R.id.noDysphoriaButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                saveChanges();
            }
        });

        noDysphoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (noDysphoriaButton.isChecked()) {
                    physicalDysphoriaButton.setChecked(false);
                    mentalDysphoriaButton.setChecked(false);
                    socialDysphoriaButton.setChecked(false);
                }
            }
        });


        physicalDysphoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (physicalDysphoriaButton.isChecked()) {
                    noDysphoriaButton.setChecked(false);
                }
            }
        });

        mentalDysphoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (mentalDysphoriaButton.isChecked()) {
                    noDysphoriaButton.setChecked(false);
                }
            }
        });

        socialDysphoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (socialDysphoriaButton.isChecked()) {
                    noDysphoriaButton.setChecked(false);
                }
            }
        });

        //Populate tags selection
        for (String currentTag:thisEntry.getMood().getTags()) {
            if (currentTag.equals(tag1.getTextOn())) {
                tag1.setChecked(true);
            }
            if (currentTag.equals(tag2.getTextOn())) {
                tag2.setChecked(true);
            }
            if (currentTag.equals(tag3.getTextOn())) {
                tag3.setChecked(true);
            }
        }
        //Populate triggers selection
        for (String currentTrigger:thisEntry.getMood().getTriggers()) {
            if (currentTrigger.equals(trigger1.getTextOn())) {
                trigger1.setChecked(true);
            }
            if (currentTrigger.equals(trigger2.getTextOn())) {
                trigger2.setChecked(true);
            }
            if (currentTrigger.equals(trigger3.getTextOn())) {
                trigger3.setChecked(true);
            }
            if (currentTrigger.equals(trigger4.getTextOn())) {
                trigger4.setChecked(true);
            }
        }

        moodSeekbar.setProgress(thisEntry.getMood().getMoodLevel() - 1); // Switch back to zero indexing to adjust for val
        journal.setText(thisEntry.getMood().getJournal());
        if (thisEntry.getDysphoria() != null) {
            dysphoriaSeekbar.setProgress(thisEntry.getDysphoria().getIntensity());
            if (thisEntry.getDysphoria().isMental()) {
                mentalDysphoriaButton.setChecked(true);
            }
            if (thisEntry.getDysphoria().isPhysical()) {
                physicalDysphoriaButton.setChecked(true);
            }
            if (thisEntry.getDysphoria().isSocial()) {
                socialDysphoriaButton.setChecked(true);
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(thisEntry.getTimeAndDate());
        SimpleDateFormat dateF = new SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

        //String time = cal.getTime().toString();
        //String date = "date";
        String date = dateF.format(cal.getTime());
        String time = timeF.format(cal.getTime());
        //timeAndDate = 0;
        dialogueCal = Calendar.getInstance();
        dialogueCal.setTimeInMillis((thisEntry.getTimeAndDate()));
        autoD8.setText(date);
        autoTime.setText(time);
        // Set onclick listener for d8
        autoD8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(thisEntry.getTimeAndDate());
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //Format tdate o previous format

                                autoD8.setText(dayOfMonth + " " + monthString(monthOfYear + 1) + " " + year);

                                dialogueCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dialogueCal.set(Calendar.YEAR, year);
                                dialogueCal.set(Calendar.MONTH, monthOfYear);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

        // Set onclick listener for time

        autoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();

                c.setTimeInMillis(thisEntry.getTimeAndDate());
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                String ampm;
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                autoTime.setText(timeFormatter(hourOfDay, minute));

                                autoTime.setText(timeFormatter(hourOfDay, minute));
                                dialogueCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dialogueCal.set(Calendar.MINUTE, minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

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

    public void saveChanges() {
        // Pull values from fields
        // Pull values from form
        long timeAndDate = dialogueCal.getTimeInMillis();
        int moodRating = moodSeekbar.getProgress() + 1; // Compensate for range of 0-4 from seekbar by adding 1

        ArrayList<String>tags = new ArrayList<String>();
        if (tag1.isChecked()) {
            tags.add((String) tag1.getTextOn());
        }
        if (tag2.isChecked()) {
            tags.add((String) tag2.getTextOn());
        }
        if (tag3.isChecked()) {
            tags.add((String) tag3.getTextOn());
        }
        ArrayList<String> triggers = new ArrayList<String>();
        if (trigger1.isChecked()) {
            triggers.add((String) trigger1.getTextOn());
        }
        if (trigger2.isChecked()) {
            triggers.add((String) trigger2.getTextOn());
        }
        if (trigger3.isChecked()) {
            triggers.add((String) trigger3.getTextOn());
        }
        if (trigger4.isChecked()) {
            triggers.add((String) trigger4.getTextOn());
        }

        String journalString = journal.getText().toString();
        //if "no dysphoria" is not selected
        boolean hasDysphoria = false;
        boolean hasMentalDysphoria, hasPhysicalDysphoria, hasSocialDysphoria;
        hasMentalDysphoria = false;
        hasPhysicalDysphoria = false;
        hasSocialDysphoria = false;
        int dysphoriaIntensity = 0;
        if (noDysphoriaButton.isChecked()) {
            hasDysphoria = false;
        }
        else {
            hasDysphoria = true;
            //dysphoriaType = 1;
            if (mentalDysphoriaButton.isChecked()) {
                hasMentalDysphoria = true;
            }
            if (physicalDysphoriaButton.isChecked()) {
                hasPhysicalDysphoria = true;
            }
            if (socialDysphoriaButton.isChecked()) {
                hasSocialDysphoria = true;
            }
            dysphoriaIntensity = dysphoriaSeekbar.getProgress();
        }

        editedEntry = new UserEntry();
        //Add new entry to arraylist
        Mood newMood = new Mood(moodRating, tags, journalString, triggers);
        editedEntry.setTimeAndDate(timeAndDate);
        editedEntry.setMood(newMood);
        if (hasDysphoria) {
            Dysphoria newDysphoria = new Dysphoria(hasPhysicalDysphoria, hasMentalDysphoria, hasSocialDysphoria, dysphoriaIntensity);
            editedEntry.setDysphoria(newDysphoria);
        }

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
        fragmentTransaction.commit();
    }

    public void deleteEntry() {
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

    }

    public String monthString(int monthNum) {
        String monthString;
        switch (monthNum) {
            case 1:  monthString = "Jan";
                break;
            case 2:  monthString = "Feb";
                break;
            case 3:  monthString = "Mar";
                break;
            case 4:  monthString = "Apr";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "Jun";
                break;
            case 7:  monthString = "Jul";
                break;
            case 8:  monthString = "Aug";
                break;
            case 9:  monthString = "Sep";
                break;
            case 10: monthString = "Oct";
                break;
            case 11: monthString = "Nov";
                break;
            case 12: monthString = "Dec";
                break;
            default: monthString = "NUL";
                break;
        }
        return monthString;
    }

    //Determines if a time is AM or PM
    public String timeFormatter(int hour, int min) {
        String minString = String.valueOf(min);
        String amOrPm = "AM";
        if (hour > 12) {
            hour -= 12;
            amOrPm = "PM";
        }
        if (minString.length() < 2) {
            minString = "0" + minString;
        }
        return (String.valueOf(hour) + ":" + minString + " " + amOrPm);

    }
}
