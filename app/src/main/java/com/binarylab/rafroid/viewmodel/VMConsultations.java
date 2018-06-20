package com.binarylab.rafroid.viewmodel;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.binarylab.rafroid.BR;
import com.binarylab.rafroid.R;
import com.binarylab.rafroid.adapters.ConsultationsAdapter;
import com.binarylab.rafroid.dao.ConsultationDAO;
import com.binarylab.rafroid.model.Consultation;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.services.DatabaseUpdateService;
import com.binarylab.rafroid.services.DatabaseUpdateServiceMessage;
import com.binarylab.rafroid.util.DateUtil;
import com.binarylab.rafroid.util.SharedPreferencesKeyStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmQuery;

public class VMConsultations extends BaseObservable implements DatabaseUpdateServiceMessage{
    private Context mContext;
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    private ConsultationsAdapter mAdapter;
    private boolean isInputVisible;

    private ObservableList<String> mDay, mClassroomsList, mLecturerList, mSubjectList;
    private ObservableList<Consultation> mConsultationList;
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableInt mDayIndex, mClassroomsListIndex;
    private String lecturer, subject, time;

    public VMConsultations(Context context){
        this.mContext = context;
        isInputVisible = false;

        ConsultationDAO dao = ConsultationDAO.getInstance();


        mConsultationList = new ObservableArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
            mConsultationList.addAll(dao.getAllConsultations());
        else {
            mConsultationList.addAll(dao.getAllConsultationsPersonalized(preferences));
        }
        mAdapter = new ConsultationsAdapter(mConsultationList, context);


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

    @Bindable
    public ObservableBoolean getIsLoading(){
        return isLoading;
    }

    //Action Commands//
    //--------------------------------------------------------------------------------------------//
    public View.OnClickListener onSearchClicked() {
        return v -> {
            ConsultationDAO dao = ConsultationDAO.getInstance();
            RealmQuery<Consultation> query;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
                query = dao.getConsultationQueryBuilder();
            else {
                query = dao.getConsultationPersonalizedQueryBuilder(preferences);
            }

            if(subject != null && !subject.isEmpty()){
                query = query.and().equalTo("className", subject);
            }

            if(lecturer != null && !lecturer.isEmpty()){
                query = query.and().equalTo("lecturer", lecturer);
            }

            if(getSelectedClassroom() != null){
                query = query.and().equalTo("classroom", getSelectedClassroom());
            }


            mConsultationList.clear();
            mConsultationList.addAll(query.findAll());

            if(getSelectedDay() != null){
                DayOfWeek selectedDay = DayOfWeek.valueOf(getSelectedDay());

                for(Consultation consultation : new ArrayList<>(mConsultationList)){
                    DayOfWeek day = DateUtil.getDayOfWeek(consultation.getStartTime());

                    if(day != selectedDay){
                        mConsultationList.remove(day);
                    }
                }

            }

            if(time != null && !time.isEmpty()){
                for(Consultation consultation : new ArrayList<>(mConsultationList)){
                    if(!time.equals(mTimeFormat.format(consultation.getStartTime()))){
                        mConsultationList.remove(consultation);
                    }
                }
            }

            mAdapter.notifyDataSetChanged();
            notifyPropertyChanged(BR.noDataVisible);
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

    public void onRefresh(){
        DatabaseUpdateService service = new DatabaseUpdateService(this, mContext);
        service.execute();
        isLoading.set(true);
    }

    @Override
    public void setMessage(String message) {
        //Ignore
    }

    @Override
    public void notifyError() {
        Toast.makeText(mContext, mContext.getString(R.string.error_connecting_to_server), Toast.LENGTH_SHORT).show();
        isLoading.set(false);
    }

    @Override
    public void onPostUpdate() {
        ConsultationDAO dao = ConsultationDAO.getInstance();
        RealmQuery<Consultation> query;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
            query = dao.getConsultationQueryBuilder();
        else {
            query = dao.getConsultationPersonalizedQueryBuilder(preferences);
        }

        if(subject != null && !subject.isEmpty()){
            query = query.and().equalTo("className", subject);
        }

        if(lecturer != null && !lecturer.isEmpty()){
            query = query.and().equalTo("lecturer", lecturer);
        }

        if(getSelectedClassroom() != null){
            query = query.and().equalTo("classroom", getSelectedClassroom());
        }


        mConsultationList.clear();
        mConsultationList.addAll(query.findAll());

        if(getSelectedDay() != null){
            DayOfWeek selectedDay = DayOfWeek.valueOf(getSelectedDay());

            for(Consultation consultation : new ArrayList<>(mConsultationList)){
                DayOfWeek day = DateUtil.getDayOfWeek(consultation.getStartTime());

                if(day != selectedDay){
                    mConsultationList.remove(day);
                }
            }

        }

        if(time != null && !time.isEmpty()){
            for(Consultation consultation : new ArrayList<>(mConsultationList)){
                if(!time.equals(mTimeFormat.format(consultation.getStartTime()))){
                    mConsultationList.remove(consultation);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
        notifyPropertyChanged(BR.noDataVisible);
        isLoading.set(false);
    }
}
