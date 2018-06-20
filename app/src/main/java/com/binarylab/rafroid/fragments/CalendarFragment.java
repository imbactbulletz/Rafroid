package com.binarylab.rafroid.fragments;
import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentCalendarBinding;
import com.binarylab.rafroid.decorators.CalendarDecorator;

import com.stacktips.view.CustomCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class CalendarFragment extends TabFragment {
    FragmentCalendarBinding mBinding;
    public static CalendarFragment newInstance(){
        return new CalendarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);

        CustomCalendarView customCalendarView = mBinding.getRoot().findViewById(R.id.custom_calendar_view);



        customCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        CalendarDecorator mdc = new CalendarDecorator();

        List decorators = new ArrayList<>();
        decorators.add(mdc);
        customCalendarView.setDecorators(decorators);
        customCalendarView.setShowOverflowDate(false);
        customCalendarView.refreshCalendar(currentCalendar);
        return mBinding.getRoot();
    }
}
