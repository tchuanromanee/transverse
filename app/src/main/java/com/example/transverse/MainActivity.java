package com.example.transverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.example.transverse.AddNewEntryFragment.writeJsonFile;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    File entriesFile;
    ArrayList<UserEntry> allEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new EncyclopediaFragment(), "");

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottomNav);
        navigation.setOnNavigationItemSelectedListener(this);

        //Load JSON assets
        //https://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi/19945484#19945484
        //populateEntries();

        allEntries = getEntries();
        sortEntries();



    }

    public void sortEntries() {
        Collections.sort(allEntries, new Comparator<UserEntry>() {
            @Override
            public int compare(UserEntry z1, UserEntry z2) {
                if (z1.getTimeAndDate() > z2.getTimeAndDate())
                    return 1;
                if (z1.getTimeAndDate() < z2.getTimeAndDate())
                    return -1;
                return 0;
            }
        });
    }

    private boolean loadFragment(Fragment fragment, String tag) {
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
        String tag = "";
        switch (item.getItemId()) {
            case R.id.bottomNavigationEncyclopediaMenuId:
                fragment = new EncyclopediaFragment();
                break;

            case R.id.bottomNavigationAddnewMenuId:
                fragment = new AddNewEntryFragment();
                break;

            case R.id.bottomNavigationStatisticsMenuId:
                fragment = new StatisticsFragment();
                tag = "stats_frag";
                break;

            case R.id.bottomNavigationSelfHelpMenuId:
                fragment = new SelfHelpFragment();
                break;
        }
        return loadFragment(fragment, tag);
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

    public void updateTitle(String newTitle) {
        TextView titleText = findViewById(R.id.toolbar_title);
        titleText.setText(newTitle);
    }
    public ArrayList<UserEntry> getEntries() {

        ArrayList<UserEntry> entriesList = new ArrayList();
        try {
            entriesFile = new File(getFilesDir(), "entries.json");
            /*if (entriesFile.exists()) {
                entriesFile.delete(); //FOR DEBUGGING
            }
            else {
                entriesFile.createNewFile();
            }*/
            JSONObject obj = new JSONObject(getStringFromFile(entriesFile));
            JSONArray m_jArry = obj.getJSONArray("entries");
            //HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                UserEntry newEntry = new UserEntry();
                Mood newMood = new Mood();
                Dysphoria newDysphoria = new Dysphoria();

                //String timeVal = jo_inside.getString("time");
                //String dateVal = jo_inside.getString("date");
                //newEntry.setTime(timeVal);
                //newEntry.setDate(dateVal);
                long timeAndDateVal = jo_inside.getLong("timeAndDate");
                newEntry.setTimeAndDate(timeAndDateVal);
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
                //int dysphoriaType;
                int dysphoriaIntensity;
                if (hasDysphoria) {
                    //dysphoriaType = jo_inside.getInt("dysphoriaType");
                    dysphoriaIntensity = jo_inside.getInt("intensity");

                    boolean hasPhysicalDysphoria = jo_inside.getBoolean("hasPhysicalDysphoria");
                    boolean hasMentalDysphoria = jo_inside.getBoolean("hasMentalDysphoria");
                    boolean hasSocialDysphoria = jo_inside.getBoolean("hasSocialDysphoria");
                    //newDysphoria.setMental(dysphoriaType);
                    newDysphoria.setIntensity(dysphoriaIntensity);
                    newDysphoria.setMental(hasMentalDysphoria);
                    newDysphoria.setPhysical(hasPhysicalDysphoria);
                    newDysphoria.setSocial(hasSocialDysphoria);
                    newEntry.setDysphoria(newDysphoria);

                }
                else { // both dysphoria type and intensity will be -1 if default constructor was used
                    //dysphoriaType = -1;
                    dysphoriaIntensity = -1;
                }

                newEntry.setMood(newMood);

                entriesList.add(newEntry);
            }

            return entriesList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entriesList;

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

    public void removeFromJSON(UserEntry entryToRemove) {

        entriesFile = new File(this.getFilesDir(), "entries.json");
        //entriesFile.delete();
        // Get previous JSON array to write to
        try {
            if (!entriesFile.exists()) {
                Log.e("App","file not exist");
                entriesFile.createNewFile();
            }
            JSONObject obj = new JSONObject(getStringFromFile(entriesFile));
            JSONArray m_jArry = obj.getJSONArray("entries");

            //String strFileJson = getStringFromFile(entriesFile);
            JSONArray newArray = new JSONArray();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject target = m_jArry.getJSONObject(i);
                if (entryToRemove.getTimeAndDate() != target.getLong("timeAndDate")) {
                    newArray.put(target);
                }
            }

            //String strFileJson = getStringFromFile(entriesFile);
           // JSONObject previousJSONObj;
            //JSONArray array;

            /*if (strFileJson != "") {
                previousJSONObj = new JSONObject(strFileJson);
                array = previousJSONObj.getJSONArray("entries");
            }
            else {
                previousJSONObj = new JSONObject();
                array = new JSONArray();
            }*/
            // get JSON Object to be written
            //JSONObject newEntryJSON = entryToJSON();
            JSONObject currentJsonObject = new JSONObject();
            currentJsonObject.put("entries", newArray);
            //Append JSON object to file

            writeJsonFile(entriesFile, currentJsonObject.toString());
        }
        catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public void editEntryinJSON(UserEntry originalEntry, UserEntry newEntryToReplace) {

        entriesFile = new File(this.getFilesDir(), "entries.json");
        //entriesFile.delete();
        // Get previous JSON array to write to
        try {
            if (!entriesFile.exists()) {
                Log.e("App","file not exist");
                entriesFile.createNewFile();
            }
            JSONObject obj = new JSONObject(getStringFromFile(entriesFile));
            JSONArray m_jArry = obj.getJSONArray("entries");

            //String strFileJson = getStringFromFile(entriesFile);
            JSONArray newArray = new JSONArray();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject target = m_jArry.getJSONObject(i);
                if (originalEntry.getTimeAndDate() != target.getLong("timeAndDate")) {
                    newArray.put(target);
                }
                else{
                    //newArray edit target with info from newarray
                    target.put("timeAndDate", newEntryToReplace.getTimeAndDate());
                    target.put("mood", newEntryToReplace.getMood().getMoodLevel());
                    //Tags and triggers as JSONArray
                    ArrayList<String> tags = newEntryToReplace.getMood().getTags();
                    JSONArray tagsArray = new JSONArray();
                    for (int k = 0; k < tags.size(); k++) {
                        JSONObject tagObj = new JSONObject();
                        tagObj.put("name", tags.get(k));
                        tagsArray.put(tagObj);
                    }
                    target.put("tags", tagsArray);//tags);
                    target.put("journal", newEntryToReplace.getMood().getJournal());
                    ArrayList<String> triggers = newEntryToReplace.getMood().getTriggers();
                    JSONArray triggersArray = new JSONArray();
                    // populate triggers
                    for (int k = 0; k < triggers.size(); k++) {
                        JSONObject triggerObj = new JSONObject();
                        triggerObj.put("name", triggers.get(k));
                        triggersArray.put(triggerObj);
                    }
                    if (newEntryToReplace.getDysphoria() != null) {
                        target.put("hasDysphoria", true);
                        target.put("hasPhysicalDysphoria", newEntryToReplace.getDysphoria().isPhysical());
                        target.put("hasMentalDysphoria", newEntryToReplace.getDysphoria().isMental());
                        target.put("hasSocialDysphoria", newEntryToReplace.getDysphoria().isSocial());
                        target.put("triggers", triggersArray);
                        target.put("intensity", newEntryToReplace.getDysphoria().getIntensity());
                    }
                    else {
                        target.put("hasDysphoria", false);
                        target.put("dysphoriaType", 0);
                        target.put("triggers", triggersArray);
                        target.put("intensity", 0);
                    }
                }
            }

            //String strFileJson = getStringFromFile(entriesFile);
            // JSONObject previousJSONObj;
            //JSONArray array;

            /*if (strFileJson != "") {
                previousJSONObj = new JSONObject(strFileJson);
                array = previousJSONObj.getJSONArray("entries");
            }
            else {
                previousJSONObj = new JSONObject();
                array = new JSONArray();
            }*/
            // get JSON Object to be written
            //JSONObject newEntryJSON = entryToJSON();
            JSONObject currentJsonObject = new JSONObject();
            currentJsonObject.put("entries", newArray);
            //Append JSON object to file

            writeJsonFile(entriesFile, currentJsonObject.toString());
        }
        catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
}
