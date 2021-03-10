package com.transverse.transverse;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncyclopediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncyclopediaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EncyclopediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EncyclopediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EncyclopediaFragment newInstance(String param1, String param2) {
        EncyclopediaFragment fragment = new EncyclopediaFragment();
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).updateTitle("Encyclopedia");
        // Create textviews dynamically depending on encyclopedia.json
        //Each array name would be a header and a different group
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("encyclopedia.json"));
            JSONArray basicsEntries = obj.getJSONArray("basics");
            JSONArray comingoutEntries = obj.getJSONArray("comingout");
            JSONArray transitioningEntries = obj.getJSONArray("transitioning");
            JSONArray dysphoriaEncEntries = obj.getJSONArray("dysphoria");

            LinearLayout.LayoutParams headerParams=new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
            headerParams.leftMargin = 50;
            headerParams.topMargin  = 50;

            LinearLayout.LayoutParams entryParams=new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
            entryParams.leftMargin = 50;
            //entryParams.setMarginEnd(18);
            entryParams.rightMargin = 50;
            entryParams.topMargin  = 0;


            LinearLayout encyLinearLayout = (LinearLayout) getView().findViewById(R.id.encyclopedia_linearlayout);
            TextView basicsHeader = new TextView(getContext());
            basicsHeader.setText("Basics");
            //TODO: Don't hardcode padding values
            basicsHeader.setPadding(10, 10, 10, 18);
            basicsHeader.setTextSize(24);
            basicsHeader.setLayoutParams(headerParams);
            TextViewCompat.setTextAppearance(basicsHeader, R.style.HeaderText);
            encyLinearLayout.addView(basicsHeader);

            for (int i = 0; i < basicsEntries.length(); i++) {
                JSONObject jo_inside = basicsEntries.getJSONObject(i);
                final String encyEntryname = jo_inside.getString("name");
                String encyEntryID = jo_inside.getString("id");
                final String encryStringXMLID = jo_inside.getString("stringXMLID");
                //TODO: dynamically change style and set onclick listener
                TextView newTextView = new TextView(getContext());
                newTextView.setText(encyEntryname);
                newTextView.setPadding(10, 10, 10, 28);
                newTextView.setTextSize(16);
                newTextView.setLayoutParams(entryParams);
                newTextView.setTextAppearance(R.style.EncyclopediaEntry);
                newTextView.setBackground(getResources().getDrawable(R.drawable.text_border_bottom));
                //TextViewCompat.setTextAppearance(newTextView, R.style.EncyclopediaEntry);
                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment fragment = EncyEntryFragment.newInstance(encryStringXMLID, encyEntryname);
                        ft.replace(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
                encyLinearLayout.addView(newTextView);
                }

            //Coming Out Entries
            TextView comingOutHeader = new TextView(getContext());
            comingOutHeader.setText("Coming Out");
            //TODO: Don't hardcode padding values
            comingOutHeader.setPadding(10, 10, 10, 18);
            comingOutHeader.setTextSize(24);
            comingOutHeader.setLayoutParams(headerParams);
            TextViewCompat.setTextAppearance(comingOutHeader, R.style.HeaderText);
            encyLinearLayout.addView(comingOutHeader);

            for (int i = 0; i < comingoutEntries.length(); i++) {
                JSONObject jo_inside = comingoutEntries.getJSONObject(i);
                final String encyEntryname = jo_inside.getString("name");
                String encyEntryID = jo_inside.getString("id");
                final String encryStringXMLID = jo_inside.getString("stringXMLID");
                //TODO: dynamically change style and set onclick listener
                TextView newTextView = new TextView(getContext());
                newTextView.setText(encyEntryname);
                newTextView.setPadding(10, 10, 10, 28);
                newTextView.setTextSize(16);
                newTextView.setLayoutParams(entryParams);
                newTextView.setTextAppearance(R.style.EncyclopediaEntry);
                newTextView.setBackground(getResources().getDrawable(R.drawable.text_border_bottom));
                //TextViewCompat.setTextAppearance(newTextView, R.style.EncyclopediaEntry);
                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment fragment = EncyEntryFragment.newInstance(encryStringXMLID, encyEntryname);
                        ft.replace(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
                encyLinearLayout.addView(newTextView);
            }

            //Transitioning Entries
            TextView transitioningHeader = new TextView(getContext());
            transitioningHeader.setText("Transitioning");
            //TODO: Don't hardcode padding values
            transitioningHeader.setPadding(10, 10, 10, 18);
            transitioningHeader.setTextSize(24);
            transitioningHeader.setLayoutParams(headerParams);
            TextViewCompat.setTextAppearance(transitioningHeader, R.style.HeaderText);
            encyLinearLayout.addView(transitioningHeader);

            for (int i = 0; i < transitioningEntries.length(); i++) {
                JSONObject jo_inside = transitioningEntries.getJSONObject(i);
                final String encyEntryname = jo_inside.getString("name");
                String encyEntryID = jo_inside.getString("id");
                final String encryStringXMLID = jo_inside.getString("stringXMLID");
                //TODO: dynamically change style and set onclick listener
                TextView newTextView = new TextView(getContext());
                newTextView.setText(encyEntryname);
                newTextView.setPadding(10, 10, 10, 28);
                newTextView.setTextSize(16);
                newTextView.setLayoutParams(entryParams);
                newTextView.setTextAppearance(R.style.EncyclopediaEntry);
                newTextView.setBackground(getResources().getDrawable(R.drawable.text_border_bottom));
                //TextViewCompat.setTextAppearance(newTextView, R.style.EncyclopediaEntry);
                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment fragment = EncyEntryFragment.newInstance(encryStringXMLID, encyEntryname);
                        ft.replace(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
                encyLinearLayout.addView(newTextView);
            }

            // Dysphoria Entries
            TextView dysphoriaHeader = new TextView(getContext());
            dysphoriaHeader.setText("Dysphoria");
            dysphoriaHeader.setPadding(10, 10, 10, 18);
            dysphoriaHeader.setTextSize(24);
            dysphoriaHeader.setLayoutParams(headerParams);
            encyLinearLayout.addView(dysphoriaHeader);


            for (int i = 0; i < dysphoriaEncEntries.length(); i++) {
                JSONObject jo_inside = dysphoriaEncEntries.getJSONObject(i);
                final String encyEntryname = jo_inside.getString("name");
                String encyEntryID = jo_inside.getString("id");
                final String encryStringXMLID = jo_inside.getString("stringXMLID");
                //TODO: Add textview under header and set onclick listener
                TextView newTextView = new TextView(getContext());
                newTextView.setText(encyEntryname);
                newTextView.setPadding(10, 10, 10, 18);
                newTextView.setTextSize(16);
                newTextView.setTextAppearance(R.style.EncyclopediaEntry);
                newTextView.setBackground(getResources().getDrawable(R.drawable.text_border_bottom));
                newTextView.setLayoutParams(entryParams);
                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment fragment = EncyEntryFragment.newInstance(encryStringXMLID, encyEntryname);
                        ft.replace(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
                encyLinearLayout.addView(newTextView);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_encyclopedia, container, false);
    }
}