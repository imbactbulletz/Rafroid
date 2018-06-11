package com.binarylab.rafroid.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentClassScheduleBinding;
import com.binarylab.rafroid.viewmodel.VMClassSchedule;

@SuppressLint("ValidFragment")
public class ClassScheduleFragment extends TabFragment{


    private ClassScheduleFragment(String title, Drawable icon) {
        super(title, icon);
    }

    FragmentClassScheduleBinding mBinding;
    VMClassSchedule mVMClassSchedule;

    public static ClassScheduleFragment newInstance(Context context) {
        return new ClassScheduleFragment(context.getString(R.string.schedule),null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_class_schedule, container, true);
        mVMClassSchedule = new VMClassSchedule(getContext());
        mBinding.setVm(mVMClassSchedule);
        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.class_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }

}
