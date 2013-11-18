/*
 * Copyright (C) 2013 Adam Colclough - Axial Exchange
 *
 * Class for setting up the dialog and initialising the underlying
 * ScrollLayouts
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

import java.util.Calendar;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slider.DateSlider.SliderContainer.OnTimeChangeListener;

/**
 * A Dialog subclass that hosts a SliderContainer and a couple of buttons,
 * displays the current time in the header, and notifies an observer
 * when the user selects a time.
 */
public class DateSlider extends DialogFragment {

//	private static String TAG = "DATESLIDER";

    protected OnDateSetListener onDateSetListener;
    protected Calendar mInitialTime, minTime, maxTime;
    protected int mLayoutID;
    protected TextView mTitleText;
    protected SliderContainer mContainer;
    protected int minuteInterval;


    public DateSlider(Context context, int layoutID, OnDateSetListener l, Calendar initialTime) {
    	this(context,layoutID,l,initialTime, null, null, 1);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l, Calendar initialTime, int minInterval) {
    	this(context,layoutID,l,initialTime, null, null, minInterval);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l,
            Calendar initialTime, Calendar minTime, Calendar maxTime) {
    	this(context,layoutID,l,initialTime, minTime, maxTime, 1);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l,
            Calendar initialTime, Calendar minTime, Calendar maxTime, int minInterval) {
        this.onDateSetListener = l;
        this.minTime = minTime; this.maxTime = maxTime;
        mInitialTime = Calendar.getInstance(initialTime.getTimeZone());
        mInitialTime.setTimeInMillis(initialTime.getTimeInMillis());
        mLayoutID = layoutID;
        this.minuteInterval = minInterval;
        if (minInterval>1) {
        	int minutes = mInitialTime.get(Calendar.MINUTE);
    		int diff = ((minutes+minuteInterval/2)/minuteInterval)*minuteInterval - minutes;
    		mInitialTime.add(Calendar.MINUTE, diff);
        }
    }

    /**
     * Set up the dialog with all the views and their listeners
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null) {
            Calendar c = (Calendar)savedInstanceState.getSerializable("time");
            if (c != null) {
                mInitialTime = c;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(mLayoutID, container, false);
        if(null == rootView){
            return null;
        }
        mTitleText = (TextView) rootView.findViewById(R.id.dateSliderTitleText);
        mContainer = (SliderContainer) rootView.findViewById(R.id.dateSliderContainer);

        mContainer.setOnTimeChangeListener(onTimeChangeListener);
        mContainer.setMinuteInterval(minuteInterval);
        mContainer.setTime(mInitialTime);
        if (minTime!=null) mContainer.setMinTime(minTime);
        if (maxTime!=null) mContainer.setMaxTime(maxTime);

        return rootView;
    }

    public void setTime(Calendar c) {
        mContainer.setTime(c);
    }


    private OnTimeChangeListener onTimeChangeListener = new OnTimeChangeListener() {

        public void onTimeChange(Calendar time) {

            if (onDateSetListener!=null)
                onDateSetListener.onDateSet(DateSlider.this, getTime());
            setTitle();
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState==null) outState = new Bundle();
        outState.putSerializable("time", getTime());
    }

    /**
     * @return The currently displayed time
     */
    protected Calendar getTime() {
        return mContainer.getTime();
    }

    /**
     * This method sets the title of the dialog
     */
    protected void setTitle() {
        if (mTitleText != null) {
            final Calendar c = getTime();
            mTitleText.setText(getString(R.string.dateSliderTitle) +
                    String.format(": %te. %tB %tY", c, c, c));
        }
    }


    /**
     * Defines the interface which defines the methods of the OnDateSetListener
     */
    public interface OnDateSetListener {
        /**
         * this method is called when a date was selected by the user
         * @param view			the caller of the method
         *
         */
        public void onDateSet(DateSlider view, Calendar selectedDate);
    }
}
