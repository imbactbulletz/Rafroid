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

import com.binarylab.rafroid.databinding.FragmentConsultationsBinding;
import com.binarylab.rafroid.viewmodel.VMConsultations;



@SuppressLint("ValidFragment")
public class ConsultationsFragment extends TabFragment {

    FragmentConsultationsBinding mBinding;

    VMConsultations mVMConsultations;

    public static ConsultationsFragment newInstance(){
        return new ConsultationsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mVMConsultations = new VMConsultations(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_consultations, container, false);
        mBinding.setVm(mVMConsultations);

        AutoCompleteTextView autoCompleter = mBinding.getRoot().findViewById(R.id.spLecturer);
        autoCompleter.setAdapter(mVMConsultations.getLecturerListAdapter());

        autoCompleter = mBinding.getRoot().findViewById(R.id.spSubject);
        autoCompleter.setAdapter(mVMConsultations.getSubjectListAdapter());

        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.consultations_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mBinding.getRoot();
    }
}
