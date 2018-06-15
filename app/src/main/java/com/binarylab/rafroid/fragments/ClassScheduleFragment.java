package com.binarylab.rafroid.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentClassScheduleSearchBinding;
import com.binarylab.rafroid.viewmodel.VMClassSchedule;

@SuppressLint("ValidFragment")
public class ClassScheduleFragment extends TabFragment{


    FragmentClassScheduleSearchBinding mBinding;
    VMClassSchedule mVMClassSchedule;

    public static ClassScheduleFragment newInstance() {
        return new ClassScheduleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVMClassSchedule = new VMClassSchedule(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_class_schedule_search, container, false);
        mBinding.setVm(mVMClassSchedule);

        AutoCompleteTextView autoCompleter = mBinding.getRoot().findViewById(R.id.spLecturer);
        autoCompleter.setAdapter(mVMClassSchedule.getLecturerListAdapter());

        autoCompleter = mBinding.getRoot().findViewById(R.id.spSubject);
        autoCompleter.setAdapter(mVMClassSchedule.getSubjectListAdapter());

        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.class_schedule_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }

}
