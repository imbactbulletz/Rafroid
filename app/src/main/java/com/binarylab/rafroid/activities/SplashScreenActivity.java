package com.binarylab.rafroid.activities;

import android.support.v4.app.Fragment;

import com.binarylab.rafroid.fragments.SplashScreenFragment;

public class SplashScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SplashScreenFragment.newInstance();
    }
}