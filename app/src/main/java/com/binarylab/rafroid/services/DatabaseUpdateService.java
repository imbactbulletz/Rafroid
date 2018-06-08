package com.binarylab.rafroid.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.api.ApiImpl;
import com.binarylab.rafroid.dao.ExamsDAO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.dto.VersionDTO;
import com.binarylab.rafroid.util.SharedPreferencesKeyStore;

import java.io.IOException;

public class DatabaseUpdateService extends AsyncTask {

    private Context mContext;
    DatabaseUpdateServiceMessage messageService;
    ApiImpl api;

    private SharedPreferences.Editor ed;
    private SharedPreferences mSharedPreferences;

    public DatabaseUpdateService(DatabaseUpdateServiceMessage message, Context context) {
        this.messageService = message;
        this.mContext = context;
        api = ApiImpl.getInstance();

        this.mSharedPreferences = context.getSharedPreferences(SharedPreferencesKeyStore.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.ed = mSharedPreferences.edit();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        messageService.setMessage(mContext.getString(R.string.checking_for_update));
    }

    @Override
    protected Object doInBackground(Object[] objects) {


        try {
            checkExamsVersion();
            checkClassesVersion();
            checkCalenderVersion();
            checkConsultationsVersion();
            checkNewsVersion();
        } catch (IOException e) {
            messageService.notifyError();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        messageService.onPostUpdate();
    }

    private void checkExamsVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.updating_exams));
        VersionDTO vdto = api.examsVersion();
        if (vdto.getVersion() > mSharedPreferences.getInt(SharedPreferencesKeyStore.EXAMS_VERSION, 0)) {

            ExamsDTO dto = api.getExams();
            ExamsDAO dao = ExamsDAO.getInstance();
            dao.clear();
            dao.saveExamsFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.EXAMS_VERSION, vdto.getVersion());
            ed.apply();

        }
    }

    private void checkClassesVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.updating_classes));
        VersionDTO dto = api.classesVersion();
        //TODO: Update database
    }

    private void checkCalenderVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_calender));
        VersionDTO dto = api.calenderVersion();
        //TODO: Update database
    }

    private void checkConsultationsVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_consultations));
        VersionDTO dto = api.consultationVersion();
        //TODO: Update database
    }

    private void checkNewsVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_news));
        VersionDTO dto = api.newsVersion();
        //TODO: Update database
    }
}
