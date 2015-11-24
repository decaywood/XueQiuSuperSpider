package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:04
 */
public abstract class DateParser {


    private static DateFormat dateFormat =
            new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);


    public static Date parseToDate(String time) {
        try {
            return dateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
