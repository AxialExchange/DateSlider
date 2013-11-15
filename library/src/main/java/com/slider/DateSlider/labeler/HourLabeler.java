package com.slider.DateSlider.labeler;

import java.util.Calendar;

import com.slider.DateSlider.TimeObject;

/**
 * A Labeler that displays hours
 */
public class HourLabeler extends Labeler {
    private final String mFormatString;

    public HourLabeler(String formatString) {
        super(90, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectFromCalendar(Util.addHours(time, val));
    }

    @Override
    protected TimeObject timeObjectFromCalendar(Calendar c) {
        return Util.getHour(c, mFormatString);
    }
}
