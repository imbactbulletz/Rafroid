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
import com.binarylab.rafroid.databinding.FragmentExamBinding;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.viewmodel.VMExam;

@SuppressLint("ValidFragment")
public class CurriculumFragment extends TabFragment {

    FragmentExamBinding mBinding;
    VMExam mVMExam;

    public static CurriculumFragment newInstance() {
        return new CurriculumFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVMExam = new VMExam(getContext(), ExamType.CURRICULUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_exam, container, false);
        mBinding.setVm(mVMExam);

        AutoCompleteTextView autoCompleter = mBinding.getRoot().findViewById(R.id.spLecturer);
        autoCompleter.setAdapter(mVMExam.getLecturerListAdapter());

        autoCompleter = mBinding.getRoot().findViewById(R.id.spSubject);
        autoCompleter.setAdapter(mVMExam.getSubjectListAdapter());

        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.exam_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }

}
