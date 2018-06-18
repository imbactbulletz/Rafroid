package com.binarylab.rafroid.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.binarylab.rafroid.util.SharedPreferencesKeyStore;
import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    private static final String TAG = FirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(SharedPreferencesKeyStore.FIREBASE_TOKEN, refreshedToken).apply();


        //Displaying token in logcat
        Log.e(TAG, "Firebase token: " + refreshedToken);

    }

}
