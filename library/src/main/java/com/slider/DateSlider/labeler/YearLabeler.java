package com.slider.DateSlider.labeler;

import java.util.Calendar;

import com.slider.DateSlider.TimeObject;

/**
 * A Labeler that displays months
 */
public class YearLabeler extends Labeler {
    private final String mFormatString;

    public YearLabeler(String formatString) {
        super(200, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectFromCalendar(Util.addYears(time, val));
    }

    @Override
    protected TimeObject timeObjectFromCalendar(Calendar c) {
        return Util.getYear(c, mFormatString);
    }
}