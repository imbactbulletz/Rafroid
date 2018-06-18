package com.binarylab.rafroid.viewmodel;


import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.binarylab.rafroid.BR;
import com.binarylab.rafroid.R;
import com.binarylab.rafroid.adapters.ConsultationsAdapter;
import com.binarylab.rafroid.dao.ConsultationDAO;

import java.util.Calendar;

public class VMConsultations extends BaseObservable {
    private Context mContext;

    private ConsultationsAdapter mAdapter;
    private boolean isInputVisible;

    private ObservableList<String> mDay, mClassroomsList, mLecturerList, mSubjectList;
    private ObservableInt mDayIndex, mClassroomsListIndex;
    private String day, lecturer, classroom, subject, time;

    public VMConsultations(Context context){
        this.mContext = context;
        isInputVisible = false;

        ConsultationDAO dao = ConsultationDAO.getInstance();
        mAdapter = new ConsultationsAdapter(dao.getAllConsultations(), context);


        // Filling the day spinner
        mDay = new ObservableArrayList<>();
        mDayIndex = new ObservableInt(0);
        mDay.add(context.getString(R.string.day));
        mDay.addAll(dao.getAllDays());


        //Filling the clasroom spinner
        mClassroomsList = new ObservableArrayList<>();
        mClassroomsListIndex = new ObservableInt(0);
        mClassroomsList.add(context.getString(R.string.classroom));
        mClassroomsList.addAll(dao.getAllClassrooms());

        mLecturerList = new ObservableArrayList<>();
        mLecturerList.addAll(dao.getAllLecturers());

        mSubjectList = new ObservableArrayList<>();
        mSubjectList.addAll(dao.getAllSubjects());

    }

    private String getSelectedDay() {
        if (mDayIndex.get() == 0)
            return null;

        return mDay.get(mDayIndex.get());
    }

    private String getSelectedClassroom() {
        if (mClassroomsListIndex.get() == 0)
            return null;

        return mClassroomsList.get(mClassroomsListIndex.get());
    }



    // -- Bindings --

    @Bindable
    public boolean isInputVisible(){
        return isInputVisible;
    }

    public void setInputVisible(boolean inputVisible) {
        isInputVisible = inputVisible;
        notifyPropertyChanged(BR.inputVisible);
    }

    @Bindable
    public ObservableList<String> getDay(){
        return mDay;
    }

    @Bindable
    public ObservableInt getDayIndex(){
        return mDayIndex;
    }

    @Bindable
    public ObservableList<String> getClassroomsList(){
        return mClassroomsList;
    }

    @Bindable
    public ObservableInt getClassroomsListIndex(){
        return mClassroomsListIndex;
    }

    @Bindable
    public ArrayAdapter<String> getLecturerListAdapter(){
        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mLecturerList);
    }

    @Bindable
    public ArrayAdapter<String> getSubjectListAdapter(){
        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mSubjectList);
    }

    @Bindable
    public String getLecturer(){
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
        notifyPropertyChanged(BR.lecturer);
    }

    @Bindable
    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        notifyPropertyChanged(BR.subject);
    }

    @Bindable
    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    @Bindable
    public RecyclerView.Adapter getConsultationsAdapter(){
        return mAdapter;
    }

    @Bindable
    public boolean getNoDataVisible(){
        return mAdapter.getItemCount() <= 0;
    }



    //Action Commands
    public View.OnClickListener onSearchClicked() {
        return v -> {
            //TODO: ImplementThis
            setInputVisible(false);
        };
    }

    public View.OnClickListener onFilterClicked() {
        return v -> setInputVisible(true);
    }

    public View.OnClickListener onTimeClicked() {
        return v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = 0;
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(mContext, (timePicker, selectedHour, selectedMinute) -> setTime(selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle(mContext.getString(R.string.select_time1));
            mTimePicker.show();

        };
    }
}
