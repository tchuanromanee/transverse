package com.example.transverse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

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

    private int timesCalled;

    File entriesFile;
    TextView entriesDisp;

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

       // displayEntries();
    }

    @Override
    public void onResume() {

        super.onResume();
        timesCalled++;
        Log.i(String.valueOf(timesCalled), "Tiems Called");
        displayEntries();


    }



    public void displayEntries() {
        entriesDisp = (TextView) getView().findViewById(R.id.entriesText);

        ArrayList<UserEntry> allEntries = getEntries();
        if (allEntries == null) {
            entriesDisp.setText("No entries!!");
            return;
        }

        for (int i = 0; i < allEntries.size(); i++) {
            entriesDisp.append(allEntries.get(i).toString() + "\n");
        }
        displayChart(allEntries);
        //setData(allEntries);
    }

    public ArrayList<UserEntry> getEntries() {

        ArrayList<UserEntry> entriesList = new ArrayList();
        try {
            entriesFile = new File(getContext().getFilesDir(), "entries.json");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entriesList;

    }

    public void displayChart(ArrayList<UserEntry> allEntries) {
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
        chart.setDrawGridBackground(false);

        // create marker to display box when values are selected
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the chart
        //mv.setChartView(chart);
        //chart.setMarker(mv);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        //chart.setPinchZoom(true);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

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
            yAxis.setAxisMaximum(10f);
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
        for (int i = 0; i < allEntries.size(); i++) {
            //entriesDisp.append(allEntries.get(i).toString() + "\n");
            int val = allEntries.get(i).getMood().getMoodLevel(); //get mood value
            long timeAndDate = allEntries.get(i).getTimeAndDate();
            //float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(timeAndDate, val, getResources().getDrawable(R.drawable.edit_text_bg)));
        }
        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button_submit);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
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
}