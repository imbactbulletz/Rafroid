package com.binarylab.rafroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.events.LoadingFinishedEvent;
import com.binarylab.rafroid.fragments.SplashScreenFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;

public class SplashScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SplashScreenFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        // Initialize Realm (just once per application)
        Realm.init(getApplicationContext());

        // Initialize Preferences
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        // Subscribe to all messages
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    void onEvent(LoadingFinishedEvent event){
            Intent intent = new Intent(this, MainActivity.class);
            if(event.isFailed())
                intent.putExtra("showConnectionError",true);
            startActivity(intent);
            finish();
    }
}