package com.example.transverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new EncyclopediaFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottomNav);
        navigation.setOnNavigationItemSelectedListener(this);

        //Load JSON assets
        //https://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi/19945484#19945484
        populateEntries();

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.bottomNavigationEncyclopediaMenuId:
                fragment = new EncyclopediaFragment();
                break;

            case R.id.bottomNavigationAddnewMenuId:
                fragment = new AddNewEntryFragment();
                break;

            case R.id.bottomNavigationStatisticsMenuId:
                fragment = new StatisticsFragment();
                break;

            case R.id.bottomNavigationSelfHelpMenuId:
                fragment = new SelfHelpFragment();
                break;
        }
        return loadFragment(fragment);
    }

    public String loadJSONFromAsset(String jsonFileName) throws IOException {
        String json = null;
        InputStream is = this.getAssets().open(jsonFileName);//"yourfilename.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        return json;
    }

    private void populateEntries() {
        try {
            JSONObject entries = new JSONObject(loadJSONFromAsset("entries.json"));
            JSONArray m_jArry = entries.getJSONArray("formules");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject entry = m_jArry.getJSONObject(i);
                Log.d("Details-->", entry.getString("mood"));
                String mood_value = entry.getString("mood");
                String time_value = entry.getString("time");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("mood", mood_value);
                m_li.put("time", time_value);

                formList.add(m_li);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
