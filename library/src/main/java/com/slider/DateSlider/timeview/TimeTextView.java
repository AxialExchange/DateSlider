package com.slider.DateSlider.timeview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.slider.DateSlider.TimeObject;

/**
 * This is a simple implementation of a TimeView which is implemented
 * as a TextView. It is aware of whether or not it is the center view
 * in the ScrollLayout so that it can alter its appearance to indicate
 * that it is currently selected.
 */
public class TimeTextView extends TextView implements TimeView {
    protected long endTime, startTime;
    protected boolean isOutOfBounds = false;

    /**
     * constructor
     * @param isCenterView true if the element is the centered view in the ScrollLayout
     * @param textSize text size in dps
     */
    public TimeTextView(Context context, boolean isCenterView, int textSize) {
        super(context);
        setupView(isCenterView, textSize);
    }

    /**
     * this method should be overwritten by inheriting classes to define its own look and feel
     * @param isCenterView true if the element is in the center of the scrollLayout
     * @param textSize textSize in dps
     */
    protected void setupView(boolean isCenterView, int textSize) {
        setGravity(Gravity.CENTER);

        if (isCenterView) {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, (textSize * 120)/100);
            setTypeface(Typeface.DEFAULT_BOLD);
            setTextColor(0xFF333333);
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
            setTextColor(0xFF999999);
        }
    }

    
    public void setTime(TimeObject to) {
        setText(to.text);
        this.startTime = to.startTime;
        this.endTime = to.endTime;
    }

    
    public void setTime(TimeView other) {
        setText(other.getTimeText());
        startTime = other.getStartTime();
        endTime = other.getEndTime();
    }
    
    public long getStartTime() {
        return this.startTime;
    }

    
    public long getEndTime() {
        return this.endTime;
    }

    
    public String getTimeText() {
        return String.valueOf(getText());
    }

	public boolean isOutOfBounds() {
		return isOutOfBounds;
	}

	public void setOutOfBounds(boolean outOfBounds) {
		if (outOfBounds && !isOutOfBounds) {
			setTextColor(0x44666666);
		}
		else if (!outOfBounds && isOutOfBounds) {
            setTextColor(0xFF666666);
		}
		isOutOfBounds = outOfBounds;
	}

}