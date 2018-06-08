package com.binarylab.rafroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.binarylab.rafroid.events.LoadingFinishedEvent;
import com.binarylab.rafroid.fragments.SplashScreenFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SplashScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SplashScreenFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    void onEvent(LoadingFinishedEvent event){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}