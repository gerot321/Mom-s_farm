package com.example.finalproject.page;
import com.github.mikephil.charting.formatter.ValueFormatter;


public class AxisDateFormatter extends ValueFormatter{
    private final String[] mValues;

    public String getFormattedValue(float value) {
        return value >= (float)0 ? (this.mValues.length > (int)value ? this.mValues[(int)value] : "") : "";
    }

    public AxisDateFormatter( String[] mValues) {
        this.mValues = mValues;
    }
}
