package com.example.transverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText autoD8, autoTime, journal;
    Button submitButton;

    // Variables to be stored for each entry
    int moodRating;
    String [] tags;
    String date, time; //"time": "1301", "date": "20200611",
    String journalString;

    //Dysphoria-specific variables for each entry
    boolean hasDysphoria;
    int dysphoriaType; // 1 = physical, 2 = mental, 3 = social
    int dysphoriaIntensity; // 1-10
    String [] triggers;


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
        submitButton = (Button) getView().findViewById(R.id.submitEntryButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                submitEntry();
            }
        });

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateF = new SimpleDateFormat("EEE MMM yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

        //String time = cal.getTime().toString();
        //String date = "date";
        String date = dateF.format(cal.getTime());
        String time = timeF.format(cal.getTime());

        autoD8.setText(date);
        autoTime.setText(time);

        // Set onclick listender for d8
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

    private void submitEntry() {
        // get JSON Object
        JSONObject newEntryJSON = entryToJSON();
        //Append JSON object to file

        Fragment nextFrag= null;
        nextFrag = new StatisticsFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFrag,"tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private JSONObject entryToJSON() {
        JSONObject newEntryJSON = new JSONObject();
        //populate arrays if needed
        JSONArray tagsArray = new JSONArray();
        JSONArray triggersArray = new JSONArray();

        //***HARD CODED STUFF
        time = "1849";
        date = "20200807";
        moodRating = 8;
        tags = new String[]{"tag1", "tag2"};
        journalString = "Journal here";
        hasDysphoria = true;
        dysphoriaType = 1;
        triggers = new String[]{"mirror", "misgendered"};
        dysphoriaIntensity = 3;


        // populate tags
        for (int i = 0; i < tags.length; i++) {
            tagsArray.put(tags[i]);
        }

        try {
            newEntryJSON.put("time", time);
            newEntryJSON.put("date", date);
            newEntryJSON.put("mood", moodRating);
            newEntryJSON.put("tags", tags);
            newEntryJSON.put("journal", journalString);
            if (hasDysphoria) {

                // populate triggers
                for (int i = 0; i < triggers.length; i++) {
                    tagsArray.put(triggers[i]);
                }
                newEntryJSON.put("hasDysphoria", true);
                newEntryJSON.put("dysphoriaType", dysphoriaType);
                newEntryJSON.put("triggers", triggers);
                newEntryJSON.put("intensity", dysphoriaIntensity);
            }
            else {
                newEntryJSON.put("hasDysphoria", false);
                newEntryJSON.put("dysphoriaType", 0);
                newEntryJSON.put("triggers", triggers);
                newEntryJSON.put("intensity", dysphoriaIntensity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newEntryJSON;

    }
}