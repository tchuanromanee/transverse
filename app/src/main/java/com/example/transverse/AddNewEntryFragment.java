package com.example.transverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewEntryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    File entriesFile;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText autoD8, autoTime, journal;
    ProgressBar moodSeekbar, dysphoriaSeekbar;
    Button submitButton;
    //TODO: later, dynamically generate these buttons for custom tags and triggers
    ToggleButton trigger1, trigger2, trigger3, trigger4;
    ToggleButton tag1, tag2, tag3;
    ToggleButton physicalDysphoriaButton, mentalDysphoriaButton, socialDysphoriaButton, noDysphoriaButton;

    ImageButton tagsInfoButton, triggersInfoButton;

    // Variables to be stored for each entry
    int moodRating;
    ArrayList<String> tags;
    String date, time; //"time": "1301", "date": "20200611",
    long timeAndDate;
    String journalString;

    //Dysphoria-specific variables for each entry
    boolean hasDysphoria;
    boolean hasPhysicalDysphoria, hasMentalDysphoria, hasSocialDysphoria;
    int dysphoriaIntensity; // 1-10
    ArrayList<String> triggers;
    Calendar dialogueCal = Calendar.getInstance();


    public AddNewEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewEntryFragment newInstance(String param1, String param2) {
        AddNewEntryFragment fragment = new AddNewEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        autoD8 = (EditText) getView().findViewById(R.id.editTextDate);
        autoTime = (EditText) getView().findViewById(R.id.editTextTime);
        journal = (EditText) getView().findViewById(R.id.editTextJournal);
        moodSeekbar = (SeekBar) getView().findViewById(R.id.moodSeekbar);
        dysphoriaSeekbar = (SeekBar) getView().findViewById(R.id.dysphoriaSeekbar);
        submitButton = (Button) getView().findViewById(R.id.submitEntryButton);
        tagsInfoButton = (ImageButton) getView().findViewById(R.id.tagsInfoButton);
        triggersInfoButton = (ImageButton) getView().findViewById(R.id.triggersInfoButton);

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    submitEntry();
                }
                catch (JSONException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        tagsInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Tags");
                alertDialog.setMessage("Tags are things that you want to associate with your entry. They can be something that you want to keep track" +
                        "of to see associations between moods and certain events.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        triggersInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Triggers");
                alertDialog.setMessage("Triggers are things that ...");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateF = new SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

        //String time = cal.getTime().toString();
        //String date = "date";
        String date = dateF.format(cal.getTime());
        String time = timeF.format(cal.getTime());
        //timeAndDate = 0;

        autoD8.setText(date);
        autoTime.setText(time);
        timeAndDate = cal.getTimeInMillis();

        // Set onclick listener for d8
        autoD8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
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
                                timeAndDate = dialogueCal.getTimeInMillis();

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
                                dialogueCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dialogueCal.set(Calendar.MINUTE, minute);
                                timeAndDate = dialogueCal.getTimeInMillis();
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_entry, container, false);
    }

    private void submitEntry() throws JSONException {
        entriesFile = new File(getContext().getFilesDir(), "entries.json");
        //entriesFile.delete();
        // Get previous JSON array to write to
        try {
            if (!entriesFile.exists()) {
                Log.e("App","file not exist");
                entriesFile.createNewFile();
            }
            String strFileJson = getStringFromFile(entriesFile);
            JSONObject previousJSONObj;
            JSONArray array;
            if (strFileJson != "") {
                previousJSONObj = new JSONObject(strFileJson);
                array = previousJSONObj.getJSONArray("entries");
            }
            else {
                previousJSONObj = new JSONObject();
                array = new JSONArray();
            }
            // get JSON Object to be written
            JSONObject newEntryJSON = entryToJSON();

            array.put(newEntryJSON);
            JSONObject currentJsonObject = new JSONObject();
            currentJsonObject.put("entries", array);
            //Append JSON object to file

            writeJsonFile(entriesFile, currentJsonObject.toString());

            //Add new entry to arraylist
            UserEntry newEntry = new UserEntry();
            Mood newMood = new Mood(moodRating, tags, journalString, triggers);
            newEntry.setTimeAndDate(timeAndDate);
            newEntry.setMood(newMood);
            if (hasDysphoria) {
                Dysphoria newDysphoria = new Dysphoria(hasPhysicalDysphoria, hasMentalDysphoria, hasSocialDysphoria, dysphoriaIntensity);
                newEntry.setDysphoria(newDysphoria);
            }
            ((MainActivity) getActivity()).allEntries.add(newEntry);
            //Success!
            Toast.makeText(getContext(), "Entry created", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }


        // Reload stats fragment
        Fragment nextFrag= null;

        //nextFrag = new StatisticsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        nextFrag = new StatisticsFragment();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFrag,"stats_frag");
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.detach(nextFrag);
        fragmentTransaction.attach(nextFrag);
        fragmentTransaction.commit();

    }


    private void readForm() {
        //entriesDisp = (TextView) getView().findViewById(R.id.entriesText);
    }

    private JSONObject entryToJSON() throws JSONException {
        JSONObject newEntryJSON = new JSONObject();
        //populate arrays if needed
        JSONArray tagsArray = new JSONArray();
        JSONArray triggersArray = new JSONArray();


        // Pull values from form
        //timeAndDate should be already populated, as it was called when calendar was dealt with
        moodRating = moodSeekbar.getProgress() + 1; // Compensate for range of 0-4 from seekbar by adding 1

        tags = new ArrayList<String>();
        if (tag1.isChecked()) {
            tags.add((String) tag1.getTextOn());
        }
        if (tag2.isChecked()) {
            tags.add((String) tag2.getTextOn());
        }
        if (tag3.isChecked()) {
            tags.add((String) tag3.getTextOn());
        }
        triggers = new ArrayList<String>();
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

        journalString = journal.getText().toString();
        //if "no dysphoria" is not selected

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


        // populate tags
        for (int i = 0; i < tags.size(); i++) {
            JSONObject tagObj = new JSONObject();
            tagObj.put("name", tags.get(i));
            tagsArray.put(tagObj);
        }

        try {
            //newEntryJSON.put("time", time);
            //newEntryJSON.put("date", date);
            newEntryJSON.put("timeAndDate", timeAndDate);
            newEntryJSON.put("mood", moodRating);
            newEntryJSON.put("tags", tagsArray);//tags);
            newEntryJSON.put("journal", journalString);
            if (hasDysphoria) {

                // populate triggers
                for (int i = 0; i < triggers.size(); i++) {
                    JSONObject triggerObj = new JSONObject();
                    triggerObj.put("name", triggers.get(i));
                    triggersArray.put(triggerObj);
                }
                newEntryJSON.put("hasDysphoria", true);
                newEntryJSON.put("hasPhysicalDysphoria", hasPhysicalDysphoria);
                newEntryJSON.put("hasMentalDysphoria", hasMentalDysphoria);
                newEntryJSON.put("hasSocialDysphoria", hasSocialDysphoria);
                newEntryJSON.put("triggers", triggersArray);
                newEntryJSON.put("intensity", dysphoriaIntensity);
            }
            else {
                newEntryJSON.put("hasDysphoria", false);
                newEntryJSON.put("dysphoriaType", 0);
                newEntryJSON.put("triggers", triggersArray);
                newEntryJSON.put("intensity", dysphoriaIntensity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newEntryJSON;

    }

    public static String getStringFromFile(File fl) throws Exception {
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static void writeJsonFile(File file, String json) {
        BufferedWriter bufferedWriter = null;
        try {
            if (!file.exists()) {
                Log.e("App","file not exist");
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}