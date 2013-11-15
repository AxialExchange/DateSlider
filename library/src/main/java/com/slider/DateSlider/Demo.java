/*
 * Copyright (C) 2013 Adam Colclough - Axial Exchange
 *
 * This is a small demo application which demonstrates the use of the
 * dateSelector
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.slider.DateSlider;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.slider.DateSlider.labeler.TimeLabeler;

import java.util.Calendar;

/**
 * Small Demo activity which demonstrates the use of the DateSlideSelector
 *
 * @author Daniel Berndt - Codeus Ltd
 */
public class Demo extends Activity implements OnClickListener {

    private TextView dateText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // load and initialise the Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dateText = (TextView) this.findViewById(R.id.selectedDateLabel);

        this.findViewById(R.id.defaultDateSelectButton).setOnClickListener(this);
        this.findViewById(R.id.defaultDateLimitSelectButton).setOnClickListener(this);
        this.findViewById(R.id.alternativeDateSelectButton).setOnClickListener(this);
        this.findViewById(R.id.customDateSelectButton).setOnClickListener(this);
        this.findViewById(R.id.monthYearDateSelectButton).setOnClickListener(this);
        this.findViewById(R.id.timeSelectButton).setOnClickListener(this);
        this.findViewById(R.id.timeLimitSelectButton).setOnClickListener(this);
        this.findViewById(R.id.dateTimeSelectButton).setOnClickListener(this);
    }

    // define the listener which is called once a user selected the date.
    private DateSlider.OnDateSetListener mDateSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    // update the dateText view with the corresponding date
                    dateText.setText(String.format("The chosen date:%n%te. %tB %tY", selectedDate, selectedDate, selectedDate));
                }
            };

    private DateSlider.OnDateSetListener mMonthYearSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    // update the dateText view with the corresponding date
                    dateText.setText(String.format("The chosen date:%n%tB %tY", selectedDate, selectedDate));
                }
            };

    private DateSlider.OnDateSetListener mTimeSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    // update the dateText view with the corresponding date
                    dateText.setText(String.format("The chosen time:%n%tR", selectedDate));
                }
            };

    private DateSlider.OnDateSetListener mDateTimeSetListener =
            new DateSlider.OnDateSetListener() {
                public void onDateSet(DateSlider view, Calendar selectedDate) {
                    // update the dateText view with the corresponding date
                    int minute = selectedDate.get(Calendar.MINUTE) /
                            TimeLabeler.MINUTEINTERVAL * TimeLabeler.MINUTEINTERVAL;
                    dateText.setText(String.format("The chosen date and time:%n%te. %tB %tY%n%tH:%02d",
                            selectedDate, selectedDate, selectedDate, selectedDate, minute));
                }
            };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        DialogFragment dialogFragment = getDemoView(id);
        dialogFragment.show(getFragmentManager(), null);
    }

    private DialogFragment getDemoView(int id) {
        final Calendar c = Calendar.getInstance();
        if (id == R.id.defaultDateSelectButton) {
            return new DefaultDateSlider(this, mDateSetListener, c);
        } else if (id == R.id.defaultDateLimitSelectButton) {
            final Calendar maxTime = Calendar.getInstance();
            maxTime.add(Calendar.DAY_OF_MONTH, 14);
            return new DefaultDateSlider(this, mDateSetListener, c, c, maxTime);
        } else if (id == R.id.alternativeDateSelectButton) {
            return new AlternativeDateSlider(this, mDateSetListener, c, c, null);
        } else if (id == R.id.customDateSelectButton) {
            return new CustomDateSlider(this, mDateSetListener, c);
        } else if (id == R.id.monthYearDateSelectButton) {
            return new MonthYearDateSlider(this, mMonthYearSetListener, c);
        } else if (id == R.id.timeSelectButton) {
            return new TimeSlider(this, mTimeSetListener, c, 15);
        } else if (id == R.id.timeLimitSelectButton) {
            final Calendar minTime = Calendar.getInstance();
            minTime.add(Calendar.HOUR, -2);
            return new TimeSlider(this, mTimeSetListener, c, minTime, c, 5);
        } else if (id == R.id.dateTimeSelectButton) {
            return new DateTimeSlider(this, mDateTimeSetListener, c);
        }
        return null;
    }
}