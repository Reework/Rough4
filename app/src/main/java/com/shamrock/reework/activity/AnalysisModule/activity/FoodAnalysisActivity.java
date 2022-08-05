package com.shamrock.reework.activity.AnalysisModule.activity;

import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.shamrock.R;

import java.util.ArrayList;

public class FoodAnalysisActivity extends AppCompatActivity {

    private LineChart linegraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);
//        linegraph=findViewById(R.id.linegraph);
//        linegraph.animate();
//
//        setData(5,10);
    }

    private void setData(int count, int range) {

        final ArrayList<Entry> yValues1=new ArrayList<>();
        yValues1.add(new Entry(0,1700));
        yValues1.add(new Entry(1,1800));
        yValues1.add(new Entry(2,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(4,1900));
        yValues1.add(new Entry(5,1900));
        yValues1.add(new Entry(6,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(3,1900));
        yValues1.add(new Entry(3,1900));



        ArrayList<Entry> yValues2=new ArrayList<>();
        yValues2.add(new Entry(0,1500));
        yValues2.add(new Entry(1,1800));
        yValues2.add(new Entry(2,1200));


        ArrayList<Entry> yValues3=new ArrayList<>();
        yValues3.add(new Entry(0,1400));
        yValues3.add(new Entry(1,1500));
        yValues3.add(new Entry(2,1800));






//        final ArrayList<String> xAxes = new ArrayList<>();
//        for (int i=0; i < 3; i++) {
//            xAxes.add(i, String.valueOf("sunit") + "_" + i); //Dynamic x-axis labels
//        }
//
//        linegraph.getXAxis().setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                int index = (int) value;
//                return xAxes.get(index);
//            }
//        });
//


        LineDataSet set1,set2,set3;
        set1=new LineDataSet(yValues1,"total");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(this, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);

        set2=new LineDataSet(yValues2,"burned");
        set2.setFillAlpha(110);
        set2.setColor(ContextCompat.getColor(this,R.color.actionbar_color));
        set2.setLineWidth(2f);
        set3=new LineDataSet(yValues3,"Consumed");


        set3.setFillAlpha(110);
        set3.setColor(ContextCompat.getColor(this, R.color.colorReescore_BlueYellow));
        set3.setLineWidth(2f);
        LineData data=new LineData(set1,set2,set3);
        linegraph.setData(data);

        XAxis xAxis = linegraph.getXAxis();
//        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.parseColor("#000000")); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                for (int i = 0; i < yValues1.size(); ++i)
                {
                    if (yValues1.get(i).getX() == value)
                    {
                        return "sunut"+i;

                    }
                }
                return "";
            }
        });


    }
}
