package com.binarylab.rafroid.listeners;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.binarylab.rafroid.fragments.CalendarFragment;
import com.stacktips.view.CustomCalendarView;

public class SwipeListener implements View.OnTouchListener {
    private int min_distance = 100;
    private float downX, downY, upX, upY;
    View v;

    CustomCalendarView customCalendarView;
    java.util.Calendar calendar;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.v = v;
        switch(event.getAction()) { // Check vertical and horizontal touches
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > min_distance) {
                        // left or right
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                //VERTICAL SCROLL
                else {
                    if (Math.abs(deltaY) > min_distance) {
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public void onLeftToRightSwipe(){
        customCalendarView = CalendarFragment.customCalendarView;

        calendar = customCalendarView.getCurrentCalendar();
        calendar.add(java.util.Calendar.MONTH, -1);
        customCalendarView.refreshCalendar(calendar);
    }

    public void onRightToLeftSwipe() {
        customCalendarView = CalendarFragment.customCalendarView;

        calendar = customCalendarView.getCurrentCalendar();
        calendar.add(java.util.Calendar.MONTH, 1);
        customCalendarView.refreshCalendar(calendar);
    }

    public void onTopToBottomSwipe() {
        return;
    }

    public void onBottomToTopSwipe() {
        return;
    }
}