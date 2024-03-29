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
import com.binarylab.rafroid.adapters.ClassScheduleAdapter;
import com.binarylab.rafroid.dao.ClassScheduleDAO;
import com.binarylab.rafroid.model.ClassSchedule;
import com.binarylab.rafroid.services.DatabaseUpdateService;
import com.binarylab.rafroid.services.DatabaseUpdateServiceMessage;
import com.binarylab.rafroid.util.SharedPreferencesKeyStore;

import java.util.Calendar;

import io.realm.RealmQuery;

//TODO: Some fields needs to be sorted out
//TODO: Maybe add some animation on layout change
public class VMClassSchedule extends BaseObservable implements DatabaseUpdateServiceMessage {

    private Context mContext;

    private boolean isInputVisible;

    private ObservableList<String> mTypeList, mDayOfWeekList, mClassroomsList, mLecturerList,
            mSubjectList;
    private ObservableInt mTypeListIndex, mDayOfWeekListIndex, mClassroomsListIndex;
    private String lecturer, subject, groups, time;

    private ObservableList<ClassSchedule> mClassScheduleList;
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ClassScheduleAdapter mClassScheduleAdapter;

    public VMClassSchedule(Context context) {
        this.mContext = context;
        isInputVisible = false;

        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();

        //Filling the Types spinner
        mTypeList = new ObservableArrayList<>();
        mTypeList.add(context.getString(R.string.type));
        mTypeList.addAll(dao.getAllTypes());
        mTypeListIndex = new ObservableInt(0);

        //Filling the DayOfWeak spinner
        mDayOfWeekList = new ObservableArrayList<>();
        mDayOfWeekListIndex = new ObservableInt(0);
        mDayOfWeekList.add(context.getString(R.string.day));
        mDayOfWeekList.addAll(dao.getAllDayOfWeeks());

        //Filling the classroom spinner
        mClassroomsList = new ObservableArrayList<>();
        mClassroomsListIndex = new ObservableInt(0);
        mClassroomsList.add(context.getString(R.string.classroom));
        mClassroomsList.addAll(dao.getAllClassrooms());

        mLecturerList = new ObservableArrayList<>();
        mLecturerList.addAll(dao.getAllLecturers());

        mSubjectList = new ObservableArrayList<>();
        mSubjectList.addAll(dao.getAllSubjects());

        mClassScheduleList = new ObservableArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
            mClassScheduleList.addAll(dao.getAll());
        else {
            mClassScheduleList.addAll(dao.getAllPersonalized(preferences));
        }
        mClassScheduleAdapter = new ClassScheduleAdapter(mClassScheduleList, mContext);
    }


    private String getSelectedTypeItem() {
        if (mTypeListIndex.get() == 0)
            return null;

        return mTypeList.get(mTypeListIndex.get());
    }

    private String getSelectedDayOfWeek() {
        if (mDayOfWeekListIndex.get() == 0)
            return null;

        return mDayOfWeekList.get(mDayOfWeekListIndex.get());
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
    public ObservableList<String> getTypeList() {
        return mTypeList;
    }

    @Bindable
    public ObservableInt getTypeListIndex() {
        return mTypeListIndex;
    }

    @Bindable
    public ObservableList<String> getDayOfWeekList() {
        return mDayOfWeekList;
    }

    @Bindable
    public ObservableInt getDayOfWeekListIndex() {
        return mDayOfWeekListIndex;
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
    }

    @Bindable
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Bindable
    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
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
    public RecyclerView.Adapter getClassScheduleAdapter() {
        return mClassScheduleAdapter;
    }

    @Bindable
    public boolean getNoDataVisible() {
        return mClassScheduleAdapter.getItemCount() <= 0;
    }

    @Bindable
    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    //Action Commands//
    //--------------------------------------------------------------------------------------------//
    public View.OnClickListener onSearchClicked() {
        return v -> {
            ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            RealmQuery<ClassSchedule> query;
            if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
                query = dao.getQueryBuilder();
            else
                query = dao.getQueryBuilderPersonalized(preferences);

            if (subject != null && !subject.isEmpty())
                query = query.and().equalTo("className", subject);

            if (getSelectedTypeItem() != null)
                query = query.and().equalTo("type", getSelectedTypeItem());

            if (lecturer != null && !lecturer.isEmpty())
                query = query.and().equalTo("lecturer", lecturer);

            if (groups != null && !groups.isEmpty()) {
                query = query.and().beginGroup();

                query = query.contains("studentGroups", groups.split(" ")[0]);
                for (int i = 1; i < groups.split(" ").length; i++)
                    query = query.or().contains("studentGroups", groups.split(" ")[i]);

                query.endGroup();
            }

            if (getSelectedDayOfWeek() != null)
                query = query.and().equalTo("dayOfWeek", getSelectedDayOfWeek());

            if (getSelectedClassroom() != null)
                query = query.and().equalTo("classroom", getSelectedClassroom());

            if (time != null && !time.isEmpty())
                query = query.and().equalTo("startTime", time);

            mClassScheduleList.clear();
            mClassScheduleList.addAll(query.findAll());


            mClassScheduleAdapter.notifyDataSetChanged();
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
            int minute = 15;
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
            mTimePicker.setTitle(mContext.getString(R.string.select_time));
            mTimePicker.show();

        };
    }

    public void onRefresh() {
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
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        RealmQuery<ClassSchedule> query;
        if (!preferences.getBoolean(SharedPreferencesKeyStore.USER_FILTER, false))
            query = dao.getQueryBuilder();
        else
            query = dao.getQueryBuilderPersonalized(preferences);

        if (subject != null && !subject.isEmpty())
            query = query.and().equalTo("className", subject);

        if (getSelectedTypeItem() != null)
            query = query.and().equalTo("type", getSelectedTypeItem());

        if (lecturer != null && !lecturer.isEmpty())
            query = query.and().equalTo("lecturer", lecturer);

        if (groups != null && !groups.isEmpty()) {
            query = query.and().beginGroup();

            query = query.contains("studentGroups", groups.split(" ")[0]);
            for (int i = 1; i < groups.split(" ").length; i++)
                query = query.or().contains("studentGroups", groups.split(" ")[i]);

            query.endGroup();
        }

        if (getSelectedDayOfWeek() != null)
            query = query.and().equalTo("dayOfWeek", getSelectedDayOfWeek());

        if (getSelectedClassroom() != null)
            query = query.and().equalTo("classroom", getSelectedClassroom());

        if (time != null && !time.isEmpty())
            query = query.and().equalTo("startTime", time);

        mClassScheduleList.clear();
        mClassScheduleList.addAll(query.findAll());


        mClassScheduleAdapter.notifyDataSetChanged();
        notifyPropertyChanged(BR.noDataVisible);
        isLoading.set(false);
    }
}
