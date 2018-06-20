package com.binarylab.rafroid.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentAboutBinding;
import com.binarylab.rafroid.viewmodel.VMAbout;

@SuppressLint("ValidFragment")
public class AboutFragment extends TabFragment {
    FragmentAboutBinding mBinding;
    private VMAbout vmAbout;
    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmAbout = new VMAbout(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        mBinding.setVm(vmAbout);
        return mBinding.getRoot();
    }
}
