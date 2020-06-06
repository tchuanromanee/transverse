package com.example.transverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    //Detect user interaction
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { //getItemID will say what was selected
            case R.id.bottomNavigationAddnewMenuId:
                //addNewEntry();
                return true;
            case R.id.bottomNavigationEncyclopediaMenuId:
                //showEncyclopedia();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //respond to menu item selection

    }
}