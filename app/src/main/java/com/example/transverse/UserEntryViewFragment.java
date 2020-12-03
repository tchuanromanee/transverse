package com.example.transverse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Map;

public class UserEntryViewFragment extends Fragment {

    public static UserEntryViewFragment newInstance(UserEntry currentEntry) {

        UserEntryViewFragment f = new UserEntryViewFragment();

        Bundle b = new Bundle();
        b.putString("text", currentEntry.toString());
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_user_entry_view, container, false);

        ((TextView) v.findViewById(R.id.user_entry_subtitle)).setText("uwu");
        return v;
    }
}
