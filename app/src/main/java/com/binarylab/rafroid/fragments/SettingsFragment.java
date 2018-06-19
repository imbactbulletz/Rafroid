package com.binarylab.rafroid.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.services.DatabaseUpdateService;
import com.binarylab.rafroid.services.DatabaseUpdateServiceMessage;
import com.binarylab.rafroid.util.SharedPreferencesKeyStore;

import java.util.Objects;

import io.realm.Realm;

public class SettingsFragment extends PreferenceFragmentCompat implements DatabaseUpdateServiceMessage {

    private SettingsFragment instance;

    public SettingsFragment(){
        instance = this;
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        //Update Button
        Preference update = findPreference(getString(R.string.update_data));
        update.setOnPreferenceClickListener(preference -> {
            DatabaseUpdateService service = new DatabaseUpdateService(instance, requireContext());
            service.execute();
            return true;
        });

        //ClearData Button
        Preference clear = findPreference(getString(R.string.clear_cache_data));
        clear.setOnPreferenceClickListener(preference -> {
            new AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.clear_data_dialog_message))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                        //Clear data
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();

                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SharedPreferencesKeyStore.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sharedPreferences.edit();

                        ed.putInt(SharedPreferencesKeyStore.CALENDAR_VERSION, 0);
                        ed.putInt(SharedPreferencesKeyStore.CLASSES_VERSION, 0);
                        ed.putInt(SharedPreferencesKeyStore.CONSULTATIONS_VERSION, 0);
                        ed.putInt(SharedPreferencesKeyStore.EXAMS_VERSION, 0);
                        ed.putInt(SharedPreferencesKeyStore.NEWS_VERSION, 0);

                        ed.apply();

                        Toast.makeText(requireContext(), getString(R.string.cached_data_deleted), Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton(getString(R.string.no), null)
                    .show();
            return true;
        });


        //Theme Switch
        SwitchPreference theme = (SwitchPreference) findPreference("theme");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
        if(pref.getBoolean("theme", false))
            theme.setChecked(true);
        else
            theme.setChecked(false);

        theme.setOnPreferenceChangeListener((preference, newValue) -> {
            SharedPreferences.Editor ed = pref.edit();
            if((Boolean) newValue){
                ed.putBoolean("theme", true);
            }else {
                ed.putBoolean("theme", false);
            }

            ed.apply();

            Objects.requireNonNull(getActivity()).finish();
            final Intent intent = getActivity().getIntent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("fragment", "settings");
            getActivity().startActivity(intent);

            return true;
        });
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void notifyError() {
        Toast.makeText(getContext(), getString(R.string.error_connecting_to_server), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUpdate() {
        Toast.makeText(getContext(), getString(R.string.update_completed), Toast.LENGTH_SHORT).show();
    }
}
