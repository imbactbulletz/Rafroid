package com.binarylab.rafroid.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.databinding.FragmentSplashscreenBinding;
import com.binarylab.rafroid.viewmodel.VMSplashScreen;

public class SplashScreenFragment extends Fragment {

    FragmentSplashscreenBinding mBinding;

    public static SplashScreenFragment newInstance() {
        return new SplashScreenFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splashscreen, container, false);
        mBinding.setVm(new VMSplashScreen());
        return mBinding.getRoot();
    }

}
