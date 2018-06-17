package com.binarylab.rafroid.viewmodel;

import android.app.DatePickerDialog;
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
import com.binarylab.rafroid.adapters.ExamAdapter;
import com.binarylab.rafroid.dao.ExamDAO;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmQuery;

//TODO: Some fields needs to be sorted out
//TODO: Maybe add some animation on layout change
//TODO: Implement Adapter for the recycler view
public class VMExam extends BaseObservable {

    private Context mContext;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    private ExamAdapter mAdapter;
    private boolean isInputVisible;

    private ObservableList<String> mDay, mClassroomsList, mLecturerList,
            mSubjectList;
    private ObservableList<Exam> mExamList;
    private ObservableInt mDayIndex, mClassroomsListIndex;
    private String lecturer, subject, date, time;

    public VMExam(Context context) {
        this.mContext = context;
        isInputVisible = false;

        ExamDAO dao = ExamDAO.getInstance();

        mExamList = new ObservableArrayList<>();
        mExamList.addAll(dao.getAllExams());
        mAdapter = new ExamAdapter(mExamList, context);

        //Filling the Day spinner
        //TODO: Implement this 1 day
        mDay = new ObservableArrayList<>();
        mDayIndex = new ObservableInt(0);
        mDay.add(context.getString(R.string.day));
        mDay.addAll(dao.getAllDays());

        //Filling the classroom spinner
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

    //Bindings//
    //--------------------------------------------------------------------------------------------//
    @Bindable
    public boolean isInputVisible() {
        return isInputVisible;
    }

    public void setInputVisible(boolean inputVisible) {
        isInputVisible = inputVisible;
        notifyPropertyChanged(BR.inputVisible);
    }

    @Bindable
    public ObservableList<String> getDay() {
        return mDay;
    }

    @Bindable
    public ObservableInt getDayIndex() {
        return mDayIndex;
    }

    @Bindable
    public ObservableList<String> getClassroomsList() {
        return mClassroomsList;
    }

    @Bindable
    public ObservableInt getClassroomsListIndex() {
        return mClassroomsListIndex;
    }

    @Bindable
    public ArrayAdapter<String> getLecturerListAdapter() {
        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mLecturerList);
    }

    @Bindable
    public ArrayAdapter<String> getSubjectListAdapter() {
        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mSubjectList);
    }

    @Bindable
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
        notifyPropertyChanged(BR.lecturer);
    }

    @Bindable
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        notifyPropertyChanged(BR.subject);
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public RecyclerView.Adapter getExamAdapter() {
        return mAdapter;
    }

    @Bindable
    public boolean getNoDataVisible() {
        return mAdapter.getItemCount() <= 0;
    }

    //Action Commands//
    //--------------------------------------------------------------------------------------------//
    public View.OnClickListener onSearchClicked() {
        return v -> {
            ExamDAO dao = ExamDAO.getInstance();
            RealmQuery<Exam> query = dao.getExamQueryBuilder();

            if (subject != null && !subject.isEmpty())
                query = query.and().equalTo("testName", subject);

            if (lecturer != null && !lecturer.isEmpty())
                query = query.and().equalTo("professor", lecturer);

            if (getSelectedClassroom() != null)
                query = query.and().equalTo("classroom", getSelectedClassroom());

            mExamList.clear();
            mExamList.addAll(query.findAll());

            if (getSelectedDay() != null) {
                DayOfWeek selectedDay = DayOfWeek.valueOf(getSelectedDay());
                for (Exam exam : new ArrayList<>(mExamList)) {
                    DayOfWeek day = DateUtil.getDayOfWeek(exam.getStartTime());

                    if (day != selectedDay)
                        mExamList.remove(exam);
                }
            }

            if (date != null && !date.isEmpty()) {
                for (Exam exam : new ArrayList<>(mExamList)) {
                    if (!date.equals(mDateFormat.format(exam.getStartTime())))
                        mExamList.remove(exam);
                }
            }

            if (time != null && !time.isEmpty()) {
                for (Exam exam : new ArrayList<>(mExamList)) {
                    if (!time.equals(mTimeFormat.format(exam.getStartTime())))
                        mExamList.remove(exam);
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
            mTimePicker = new TimePickerDialog(mContext, (timePicker, selectedHour, selectedMinute) -> {
                String hourString = selectedHour + "";
                if (hourString.length() == 1)
                    hourString = "0" + selectedHour;

                String minuteString = "" + selectedMinute;
                if (minuteString.length() == 1)
                    minuteString = "0" + selectedMinute;

                setTime(hourString + ":" + minuteString);
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle(mContext.getString(R.string.select_time1));
            mTimePicker.show();

        };
    }

    public View.OnClickListener onDateClicked() {
        return v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            DatePickerDialog mDatePicker;
            int year = mcurrentTime.get(Calendar.YEAR);
            int month = mcurrentTime.get(Calendar.MONTH);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            mDatePicker = new DatePickerDialog(mContext, (datePicker, selectedYear, selectedMonth, selctedDayOfMonth) -> {
                selectedMonth++;

                String monthString = selectedMonth + "";
                if (monthString.length() == 1)
                    monthString = "0" + monthString;

                setDate(selctedDayOfMonth + "/" + monthString + "/" + selectedYear);
            }, year, month, day);//Yes 24 hour time
            mDatePicker.setTitle(mContext.getString(R.string.select_date));
            mDatePicker.show();

        };
    }
}
