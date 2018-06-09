package com.binarylab.rafroid.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.api.ApiImpl;
import com.binarylab.rafroid.dao.CalendarDAO;
import com.binarylab.rafroid.dao.ClassScheduleDAO;
import com.binarylab.rafroid.dao.ConsultationDAO;
import com.binarylab.rafroid.dao.ExamDAO;
import com.binarylab.rafroid.dao.NewsDAO;
import com.binarylab.rafroid.dto.CalendarsDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.dto.CompleteNewsDTO;
import com.binarylab.rafroid.dto.ConsultationDTO;
import com.binarylab.rafroid.dto.ConsultationsDTO;
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
            ExamDAO dao = ExamDAO.getInstance();
            dao.clear();
            dao.saveExamsFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.EXAMS_VERSION, vdto.getVersion());
            ed.apply();

        }
    }

    private void checkClassesVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.updating_classes));
        VersionDTO vdto = api.classesVersion();

        if (vdto.getVersion() > mSharedPreferences.getInt(SharedPreferencesKeyStore.CLASSES_VERSION, 0)) {

            ClassesDTO dto = api.getClasses();
            ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
            dao.clear();
            dao.saveClassScheduleFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.CLASSES_VERSION, vdto.getVersion());
            ed.apply();

        }
    }

    private void checkCalenderVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_calender));

        VersionDTO vdto = api.calenderVersion();

        if (vdto.getVersion() > mSharedPreferences.getInt(SharedPreferencesKeyStore.CALENDAR_VERSION, 0)) {

            CalendarsDTO dto = api.getCalendars();
            CalendarDAO dao = CalendarDAO.getInstance();
            dao.clear();
            dao.saveCalendarsFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.CALENDAR_VERSION, vdto.getVersion());
            ed.apply();

        }
    }

    private void checkConsultationsVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_consultations));
        VersionDTO vdto = api.consultationVersion();

        if (vdto.getVersion() > mSharedPreferences.getInt(SharedPreferencesKeyStore.CONSULTATIONS_VERSION, 0)) {

            ConsultationsDTO dto = api.getConsultations();
            ConsultationDAO dao = ConsultationDAO.getInstance();
            dao.clear();
            dao.saveConsultationsFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.CONSULTATIONS_VERSION, vdto.getVersion());
            ed.apply();

        }
    }

    private void checkNewsVersion() throws IOException {
        messageService.setMessage(mContext.getString(R.string.update_news));
        VersionDTO vdto = api.newsVersion();

        if (vdto.getVersion() > mSharedPreferences.getInt(SharedPreferencesKeyStore.NEWS_VERSION, 0)) {

            CompleteNewsDTO dto = api.getNews();
            NewsDAO dao = NewsDAO.getInstance();
            dao.clear();
            dao.saveNewsFromDTO(dto);
            ed.putInt(SharedPreferencesKeyStore.NEWS_VERSION, vdto.getVersion());
            ed.apply();

        }
    }
}
