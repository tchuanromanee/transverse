package com.example.transverse;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyXAxisValueFormatter implements IAxisValueFormatter {

   /* @Override
    public String getFormattedValue(long dateInMillisecons, int index, ViewPortHandler viewPortHandler) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
            return sdf.format(new Date(dateInMillisecons));

        } catch (Exception e) {

            return "error";
        }
    }*/


    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        long yourmilliseconds = (long) value;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");// HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        //System.out.println(sdf.format(resultdate));

        //DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
        //return mFormat.format(value) + " $";
        return sdf.format(resultdate);
    }
}
