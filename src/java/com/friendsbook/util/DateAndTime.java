package com.friendsbook.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAndTime {
	//a format string for the date and time
    public static final String DATE_FORMAT_NOW = "MMM-dd-yyyy HH:mm:ss";
    public static String dt;
    //get the date and time now
    public static String dateTimeString()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
}
