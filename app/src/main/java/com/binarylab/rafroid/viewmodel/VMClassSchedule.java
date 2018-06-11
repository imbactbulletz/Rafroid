package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;

import com.binarylab.rafroid.adapters.ClassScheduleAdapter;
import com.binarylab.rafroid.dao.ClassScheduleDAO;
import com.binarylab.rafroid.model.ClassSchedule;

import java.util.List;

public class VMClassSchedule extends BaseObservable{

    private Context mContext;
    private List<ClassSchedule> mClassScheduleList;
    private ClassScheduleAdapter mClassScheduleAdapter;

    public VMClassSchedule(Context context){
        this.mContext = context;

        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        mClassScheduleList = dao.getAll();
        mClassScheduleAdapter = new ClassScheduleAdapter(mClassScheduleList, context);
    }

    @Bindable
    public RecyclerView.Adapter getAdapter(){
        return mClassScheduleAdapter;
    }

}
