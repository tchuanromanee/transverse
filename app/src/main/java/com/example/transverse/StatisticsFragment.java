package com.example.transverse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public ArrayList<Entry> getEntries() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("entries");
            ArrayList<Entry> entriesList = new ArrayList();
            //HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Entry newEntry = new Entry();
                Log.d("Details-->", jo_inside.getString("time"));
                String dateVal = jo_inside.getString("date");
                int moodVal = jo_inside.getInt("mood");
                //Get tags
                JSONArray tagsJSO = jo_inside.getJSONArray("tags");
                int tagsLength = tagsJSO.length();

                ArrayList<String> tags = new ArrayList<>();
                for(int j=0; j<tagsLength; j++) {
                    JSONObject json = tagsJSO.getJSONObject(j);
                    tags.add(json.getString("name"));
                }

                String journalVal = jo_inside.getString("journal");
                //Get triggers
                JSONArray triggersJSO = jo_inside.getJSONArray("tags");
                int triggersLength = triggersJSO.length();

                ArrayList<String> triggers = new ArrayList<>();
                for(int j=0; j<triggersLength; j++) {
                    JSONObject json = triggersJSO.getJSONObject(j);
                    triggers.add(json.getString("name"));
                }

                Boolean hasDysphoria = jo_inside.getBoolean("hasDysphoria");
                int dysphoriaType;
                int dysphoriaIntensity;
                if (hasDysphoria) {
                    dysphoriaType = jo_inside.getInt("dysphoriaType");
                    dysphoriaIntensity = jo_inside.getInt("intensity");
                }
                else {
                    dysphoriaType = -1;
                    dysphoriaIntensity = -1;
                }


                //Add your values in your `ArrayList` as below:
                //m_li = new HashMap<String, int>();
                //m_li.put("date", dateVal);
                //m_li.put("dysphoriaVal", dysphoriaVal);

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