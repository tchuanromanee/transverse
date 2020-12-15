package com.example.transverse;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelfHelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfHelpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GridView allMethodsView;

    public SelfHelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfHelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfHelpFragment newInstance(String param1, String param2) {
        SelfHelpFragment fragment = new SelfHelpFragment();
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
        return inflater.inflate(R.layout.fragment_self_help, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).updateTitle("Self Help");
        displayMethods();
        // displayEntries();
    }

    public void displayMethods() {
        allMethodsView = (GridView) getView().findViewById(R.id.allSelfHelpEntries);


        final SelfHelpAdapter selfHelpAdapter = new SelfHelpAdapter(this, R.layout.self_help_list_item);
        allMethodsView.setAdapter(selfHelpAdapter);
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<SelfHelp> allMethodsArray = activity.allMethods;
        // Populate the list, through the adapter
        for(final SelfHelp method : allMethodsArray) {
            selfHelpAdapter.add(method);
        }

        allMethodsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = allMethodsView.getItemAtPosition(position);
                SelfHelp currMethod = (SelfHelp) o;
                // Intent intent = new Intent(this,EntryViewFragment.class);
                //based on item add info to intent
                //startActivity(intent);
                /*
                Fragment nextFrag= null;
                nextFrag = new UserEntryViewFragment(currentEntry);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, nextFrag,"tag");
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.detach(nextFrag);
                fragmentTransaction.attach(nextFrag);
                fragmentTransaction.commit();
                */


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = SelfHelpViewFragment.newInstance(currMethod);
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}