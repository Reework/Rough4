package com.shamrock.reework.util;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class  MyValueFormatter  implements IValueFormatter, IAxisValueFormatter
{

    protected DecimalFormat mFormat;
    private PieChart pieChart;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param
     */
    public MyValueFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + "";
    }

    // IAxisValueFormatter
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + "";
    }

    public int getDecimalDigits() {
        return 1;
    }
}
