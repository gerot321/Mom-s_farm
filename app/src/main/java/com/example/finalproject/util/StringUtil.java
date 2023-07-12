package com.example.finalproject.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

public class StringUtil {
    public static final String formatToIDR(String number) {
        try {
            if (number.isEmpty()) {
                return "";
            }
            return formatToIDR(Double.parseDouble(number));
        } catch (Exception e) {
            return number;
        }
    }

    public static final String formatToIDR(double number) {
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        formatter.setMaximumFractionDigits(0);
        formatter.setDecimalFormatSymbols(formatRp);

        return formatter.format(number);
    }



}
