package com.tt.tutorials.java.datetime;

import java.util.Calendar;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class TimeStampToTime {
    public static void main(String[] args) {
        long millis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        System.out.println("Year: " + calendar.get(Calendar.YEAR));
        System.out.println("Month: " + calendar.get(Calendar.MONTH));
        System.out.println("Day: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("Hour: " + calendar.get(Calendar.HOUR));
        System.out.println("Minute: " + calendar.get(Calendar.MINUTE));
        System.out.println("Second: " + calendar.get(Calendar.SECOND));
    }
}
