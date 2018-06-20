package com.binarylab.rafroid.decorators;

import android.graphics.Color;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.dao.CalendarDAO;
import com.binarylab.rafroid.listeners.SwipeListener;
import com.binarylab.rafroid.model.Calendar;
import com.binarylab.rafroid.util.DateUtil;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarDecorator implements DayDecorator {
    private List<Calendar> semesterIntervals;
    private List<Calendar> holidayIntervals;
    private List<Calendar> curriculumIntervals;
    private List<Calendar> examIntervals;

    public CalendarDecorator(){
        CalendarDAO dao = CalendarDAO.getInstance();

        semesterIntervals = dao.getAllSemesterIntervals();
        holidayIntervals = dao.getAllHolidayIntervals();
        curriculumIntervals = dao.getAllCurriculumIntervals();
        examIntervals = dao.getAllExamIntervals();
    }


    @Override
    public void decorate(DayView dayView) {
        dayView.setOnTouchListener(new SwipeListener() );
        Date decoraterDate = DateUtil.getZeroTimeDate(dayView.getDate());
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        Date startInterval;
        Date endInterval;

        for(Calendar semesterInterval : semesterIntervals){
            startInterval = DateUtil.getZeroTimeDate(semesterInterval.getStartDate());
            endInterval = DateUtil.getZeroTimeDate(semesterInterval.getEndDate());

            if( decoraterDate.equals(startInterval) || decoraterDate.equals(endInterval) ||
                    (decoraterDate.after(startInterval) && decoraterDate.before(endInterval))){
                calendar.setTime(decoraterDate);

                if(calendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SATURDAY ||
                        calendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY){
                    return;
                }
                else{
                     dayView.setBackgroundResource(R.color.semesterColor);
                     return;
                }
            }
        }

        for(Calendar holidayInterval: holidayIntervals){
            startInterval = DateUtil.getZeroTimeDate(holidayInterval.getStartDate());
            endInterval = DateUtil.getZeroTimeDate(holidayInterval.getEndDate());

            if( decoraterDate.equals(startInterval) || decoraterDate.equals(endInterval) ||
                    (decoraterDate.after(startInterval) && decoraterDate.before(endInterval))){
                dayView.setBackgroundResource(R.color.holidayColor);
                return;
            }
        }

        for(Calendar curriculumInterval: curriculumIntervals){
            startInterval = DateUtil.getZeroTimeDate(curriculumInterval.getStartDate());
            endInterval = DateUtil.getZeroTimeDate(curriculumInterval.getEndDate());

            if( decoraterDate.equals(startInterval) || decoraterDate.equals(endInterval) ||
                    (decoraterDate.after(startInterval) && decoraterDate.before(endInterval))){
                dayView.setBackgroundResource(R.color.curriculumColor);
                return;
            }
        }

        for(Calendar examInterval: examIntervals){
            startInterval = DateUtil.getZeroTimeDate(examInterval.getStartDate());
            endInterval = DateUtil.getZeroTimeDate(examInterval.getEndDate());

            if( decoraterDate.equals(startInterval) || decoraterDate.equals(endInterval) ||
                    (decoraterDate.after(startInterval) && decoraterDate.before(endInterval))){
                dayView.setBackgroundResource(R.color.examColor);
                return;
            }
        }

    }
}
