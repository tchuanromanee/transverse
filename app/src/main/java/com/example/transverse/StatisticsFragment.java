package com.example.transverse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;


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



    ListView entriesDisp;

    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

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

        ((MainActivity) getActivity()).updateTitle("Statistics");
       // displayEntries();
    }

    @Override
    public void onResume() {

        super.onResume();
        ((MainActivity) getActivity()).sortEntries();
        displayEntries();


    }



    public void displayEntries() {
        entriesDisp = (ListView) getView().findViewById(R.id.entriesDisplay);


        final UserEntryAdapter userEntryAdapter = new UserEntryAdapter(this, R.layout.user_entry_list_item);
        entriesDisp.setAdapter(userEntryAdapter);
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<UserEntry> allEntries = activity.allEntries;
        // Populate the list, through the adapter
        for(final UserEntry entry : allEntries) {
            userEntryAdapter.add(entry);
        }

        entriesDisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = entriesDisp.getItemAtPosition(position);
                UserEntry currentEntry = (UserEntry) o;
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
                Fragment fragment = UserEntryViewFragment.newInstance(currentEntry);
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
                /*LinearLayout fragContainer = (LinearLayout) findViewById(R.id.llFragmentContainer);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ll.setId(12345);

        getFragmentManager().beginTransaction().add(ll.getId(), TestFragment.newInstance("I am frag 1"), "someTag1").commit();
        getFragmentManager().beginTransaction().add(ll.getId(), TestFragment.newInstance("I am frag 2"), "someTag2").commit();

        fragContainer.addView(ll);

                 */


                //prestationEco str = (prestationEco)o; //As you are using Default String Adapter
                //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        /*EntriesDisp used to be a testview, which was why here there is setText if (allEntries == null) {
            entriesDisp.setText("No entries!!");
            return;
        }

        for (int i = 0; i < allEntries.size(); i++) {
            entriesDisp.append(allEntries.get(i).toString() + "\n");
        }*/
       // ArrayAdapter<UserEntry> arrayAdapter = new ArrayAdapter<UserEntry>(getActivity(), android.R.layout.simple_list_item_1, allEntries);

        //entriesDisp.setAdapter(arrayAdapter);
        //  entriesDisp.setAdapter(entriesAdapter);
        displayChart(allEntries);
        //setData(allEntries);

    }



    public void displayChart(ArrayList<UserEntry> allEntries) {
        if (allEntries.size() == 0) {
            return;
        }
        // // Chart Style // //
        chart = getView().findViewById(R.id.chart);

        // background color
        chart.setBackgroundColor(Color.WHITE);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
       // chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //String xAxisValue = chart.getData().getXVals().get(e.getXIndex());
                Log.e("VAL SELECTED", "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: "); // x will be some number w 10^12 places
                long timeAndDate = (long) h.getX();

                //Log.d("test", "xAxisValue: " + xAxisValue);
                // Find the entry w the same date/time (given by the X value)
                //ArrayList<UserEntry> allEntries
                /*int len=allEntries.size();
                for(int i=0; i<len; i++) {
                    if (allEntries.get(i).getTimeAndDate().equals(timeAndDate)) {
                        // Do something ...
                    }
                }*/
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setDrawGridBackground(false);

        // create marker to display box when values are selected
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the chart
        //mv.setChartView(chart);
        //chart.setMarker(mv);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        //chart.setPinchZoom(true);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new MyXAxisValueFormatter());


            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            //yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(5f);
            yAxis.setAxisMinimum(1f);
        }

        {   // // Create Limit Lines // //
            /*LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
            //llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
           // ll1.setTypeface(tfRegular);

            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);
            //ll2.setTypeface(tfRegular);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
            //xAxis.addLimitLine(llXAxis);

             */
        }

        // add data
        //seekBarX.setProgress(45);
        //seekBarY.setProgress(180);
        setData(allEntries);

        // draw points over time
        chart.animateX(800);
        chart.animateY(1000);

        // get the legend (only possible after setting data)
       // Legend l = chart.getLegend();

        // draw legend entries as lines
        //l.setForm(LegendForm.LINE);

    }

    private void setData(ArrayList<UserEntry> allEntries) {

        ArrayList<Entry> values = new ArrayList<>();//Entries are each entry in the chart, not dysphoria or mood entries
        ArrayList<Entry> dysphoriaVals = new ArrayList<>();//Entries are each entry in the chart, not dysphoria or mood entries
        for (int i = 0; i < allEntries.size(); i++) {
            //entriesDisp.append(allEntries.get(i).toString() + "\n");
            int val = allEntries.get(i).getMood().getMoodLevel(); //get mood value
            long timeAndDate = allEntries.get(i).getTimeAndDate();
            if (allEntries.get(i).getDysphoria() != null) {
                int dysphoriaVal = allEntries.get(i).getDysphoria().getIntensity();
                dysphoriaVals.add(new Entry(timeAndDate, dysphoriaVal, getResources().getDrawable(R.drawable.edit_text_bg)));
            }
            //float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(timeAndDate, val, getResources().getDrawable(R.drawable.edit_text_bg)));
        }
        LineDataSet moodDataSet;
        LineDataSet dysphoriaDataSet;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            moodDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            moodDataSet.setValues(values);
            moodDataSet.notifyDataSetChanged();
            dysphoriaDataSet = (LineDataSet) chart.getData().getDataSetByIndex(1);
            dysphoriaDataSet.setValues(dysphoriaVals);
            dysphoriaDataSet.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            moodDataSet = new LineDataSet(values, "Mood");

            dysphoriaDataSet = new LineDataSet(dysphoriaVals, "Dysphoria");
            moodDataSet.setDrawIcons(false);
            dysphoriaDataSet.setDrawIcons(false);

            // draw dashed line
            moodDataSet.enableDashedLine(10f, 5f, 0f);
            //dysphoriaDataSet.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            moodDataSet.setColor(Color.BLACK);
            moodDataSet.setCircleColor(Color.BLACK);

            dysphoriaDataSet.setColor(Color.YELLOW);
            dysphoriaDataSet.setCircleColor(Color.YELLOW);

            // line thickness and point size
            moodDataSet.setLineWidth(1f);
            moodDataSet.setCircleRadius(3f);

            dysphoriaDataSet.setLineWidth(1f);
            dysphoriaDataSet.setCircleRadius(3f);

            // draw points as solid circles
            moodDataSet.setDrawCircleHole(false);
            dysphoriaDataSet.setDrawCircleHole(false);

            // customize legend entry
            moodDataSet.setFormLineWidth(1f);
            moodDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            moodDataSet.setFormSize(15.f);
            dysphoriaDataSet.setFormLineWidth(1f);
            dysphoriaDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dysphoriaDataSet.setFormSize(15.f);

            // text size of values
            moodDataSet.setValueTextSize(9f);
            dysphoriaDataSet.setValueTextSize(9f);

            // draw selection line as dashed
            //moodDataSet.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            moodDataSet.setDrawFilled(true);
            moodDataSet.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button_submit);
                moodDataSet.setFillDrawable(drawable);
            } else {
                moodDataSet.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(moodDataSet); // add the data sets
            dataSets.add(dysphoriaDataSet);
            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }




}