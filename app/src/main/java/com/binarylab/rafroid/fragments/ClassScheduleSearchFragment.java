package com.binarylab.rafroid.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentClassScheduleBinding;
import com.binarylab.rafroid.databinding.FragmentClassScheduleSearchBinding;
import com.binarylab.rafroid.viewmodel.VMClassSchedule;
import com.binarylab.rafroid.viewmodel.VMClassScheduleSearch;

@SuppressLint("ValidFragment")
public class ClassScheduleSearchFragment extends TabFragment{


    private ClassScheduleSearchFragment(String title, Drawable icon) {
        super(title, icon);
    }

    FragmentClassScheduleSearchBinding mBinding;
    VMClassScheduleSearch mVMClassSchedule;

    public static ClassScheduleSearchFragment newInstance(Context context) {
        return new ClassScheduleSearchFragment(context.getString(R.string.search),null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_class_schedule_search, container, true);
        mVMClassSchedule = new VMClassScheduleSearch(getContext());
        mBinding.setVm(mVMClassSchedule);
//        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.news_feed);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }

}
