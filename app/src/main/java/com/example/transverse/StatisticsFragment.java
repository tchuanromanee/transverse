package com.example.transverse;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView entriesDisp;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        entriesDisp = (TextView) getView().findViewById(R.id.entriesText);
        displayEntries();
    }

    public void displayEntries() {

        ArrayList<Entry> allEntries = getEntries();
        for (int i = 0; i < allEntries.size(); i++) {
            entriesDisp.append(allEntries.get(i).toString() + "\n");
        }
    }

    public ArrayList<Entry> getEntries() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("entries");
            ArrayList<Entry> entriesList = new ArrayList();
            //HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Entry newEntry = new Entry();
                Mood newMood = new Mood();
                Dysphoria newDysphoria = new Dysphoria();

                String timeVal = jo_inside.getString("time");
                String dateVal = jo_inside.getString("date");
                newEntry.setTime(timeVal);
                newEntry.setDate(dateVal);

                int moodVal = jo_inside.getInt("mood");
                newMood.setMoodLevel(moodVal);
                //Get tags
                JSONArray tagsJSO = jo_inside.getJSONArray("tags");
                int tagsLength = tagsJSO.length();


                for(int j=0; j<tagsLength; j++) {
                    JSONObject json = tagsJSO.getJSONObject(j);
                    newMood.addTag(json.getString("name"));
                }

                String journalVal = jo_inside.getString("journal");
                newMood.setJournal(journalVal);

                //Get triggers
                JSONArray triggersJSO = jo_inside.getJSONArray("triggers");
                int triggersLength = triggersJSO.length();


                for(int j=0; j<triggersLength; j++) {
                    JSONObject json = triggersJSO.getJSONObject(j);
                    newMood.addTrigger(json.getString("name"));
                }

                Boolean hasDysphoria = jo_inside.getBoolean("hasDysphoria");
                int dysphoriaType;
                int dysphoriaIntensity;
                if (hasDysphoria) {
                    dysphoriaType = jo_inside.getInt("dysphoriaType");
                    dysphoriaIntensity = jo_inside.getInt("intensity");
                    newDysphoria.setType(dysphoriaType);
                    newDysphoria.setIntensity(dysphoriaIntensity);
                    newEntry.setDysphoria(newDysphoria);

                }
                else { // both dysphoria type and intensity will be -1 if default constructor was used
                    dysphoriaType = -1;
                    dysphoriaIntensity = -1;
                }

                newEntry.setMood(newMood);

                entriesList.add(newEntry);
            }

            return entriesList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("entries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}