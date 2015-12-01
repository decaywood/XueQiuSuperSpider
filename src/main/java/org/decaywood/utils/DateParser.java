package org.decaywood.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:04
 */
public abstract class DateParser {


    private static final Date quarter1;
    private static final Date quarter2;
    private static final Date quarter3;
    private static final Date quarter4;



    static {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, Calendar.MARCH, 31, 23, 59, 59);
        quarter1 = calendar.getTime();
        calendar.set(year, Calendar.JUNE, 30, 23, 59, 59);
        quarter2 = calendar.getTime();
        calendar.set(year, Calendar.SEPTEMBER, 30, 23, 59, 59);
        quarter3 = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        quarter4 = calendar.getTime();
    }

    public static Date parseToDate(String time) {
        DateFormat dateFormat =
                new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            return dateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimePrefix(boolean quarterScope) {
        return getTimePrefix(new Date(), quarterScope);
    }

    public static String getTimePrefix(Date date, boolean quarterScope) {
        String time_prefix;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int diff = 1 - Calendar.JANUARY;
        int month = calendar.get(Calendar.MONTH) + diff;
        int day = calendar.get(Calendar.DATE);

        if(quarterScope) {
            year = calendar.get(Calendar.YEAR);
            if (date.after(quarter3)) calendar.setTime(quarter3);
            else if(date.after(quarter2)) calendar.setTime(quarter2);
            else if(date.after(quarter1)) calendar.setTime(quarter1);
            else {
                year--;
                calendar.setTime(quarter4);
            }

            month = calendar.get(Calendar.MONTH) + diff;
            day = calendar.get(Calendar.DATE);
        }

        time_prefix = String.valueOf(year) + String.format("%02d", month) + String.format("%02d", day);
        return time_prefix;
    }


    public static Date parseDate(String yyyymmdd) {

        if(yyyymmdd == null || yyyymmdd.length() != 8 || !isdigit(yyyymmdd))
            throw new IllegalArgumentException();

        Calendar calendar = Calendar.getInstance();
        int diff = Calendar.JANUARY - 1;
        int year = Integer.parseInt(yyyymmdd.substring(0, 4));
        int month = Integer.parseInt(yyyymmdd.substring(4, 6));
        int day = Integer.parseInt(yyyymmdd.substring(6, 8));
        calendar.set(year, month + diff, day);
        return calendar.getTime();

    }

    private static boolean isdigit(String str) {
        for (char c : str.toCharArray()) if(!Character.isDigit(c)) return false;
        return true;
    }

}
