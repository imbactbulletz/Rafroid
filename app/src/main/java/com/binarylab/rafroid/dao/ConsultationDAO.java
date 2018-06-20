package com.binarylab.rafroid.dao;

import android.content.SharedPreferences;

import com.binarylab.rafroid.dto.ConsultationDTO;
import com.binarylab.rafroid.dto.ConsultationsDTO;
import com.binarylab.rafroid.model.Consultation;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;

public class ConsultationDAO {

    private static ConsultationDAO instance;

    private ConsultationDAO(){}

    public static ConsultationDAO getInstance() {
        if(instance == null)
            instance = new ConsultationDAO();
        return instance;
    }

    public void clear(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Consultation.class);
        realm.commitTransaction();
    }

    public void saveConsultationsFromDTO(ConsultationsDTO dtos){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

            for(ConsultationDTO dto:dtos.getSchedule()){
                Consultation consultation = new Consultation();
                consultation.setClassName(dto.getClass_name());
                consultation.setClassroom(dto.getClassroom());
                consultation.setDay(dto.getDay());
                consultation.setLecturer(dto.getLecturer());
                consultation.setStartTime(DateUtil.parseTimeHrs(dto.getTime(), true));
                consultation.setEndTime(DateUtil.parseTimeHrs(dto.getTime(), false));
                realm.copyToRealm(consultation);
            }

        realm.commitTransaction();

    }

    public List<Consultation> getAllConsultations(){
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Consultation.class).findAll();
    }

    public List<Consultation> getAllConsultationsPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Consultation> query = realm.where(Consultation.class);
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("className", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("className", subject, Case.INSENSITIVE);
            }
        }
        query.endGroup();

        return query.findAll();
    }

    public List<String> getAllDays() {
        List<String> set = new ArrayList<>();
        for (DayOfWeek dayofWeek : DayOfWeek.values())
            set.add(dayofWeek.toString());

        return set;
    }

    public List<String> getAllClassrooms() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation : realm.where(Consultation.class).distinct("classroom").findAll().sort("classroom"))
            set.add(consultation.getClassroom());

        return set;
    }

    public List<String> getAllLecturers() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation: realm.where(Consultation.class).distinct("lecturer").findAll().sort("lecturer"))
            set.add(consultation.getLecturer());

        return set;
    }

    public List<String> getAllSubjects() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation : realm.where(Consultation.class).distinct("className").findAll().sort("className"))
            set.add(consultation.getClassName());

        return set;
    }

    public RealmQuery<Consultation> getConsultationQueryBuilder(){
        return Realm.getDefaultInstance().where(Consultation.class);
    }

    public RealmQuery<Consultation> getConsultationPersonalizedQueryBuilder(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Consultation> query = realm.where(Consultation.class);
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("className", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("className", subject, Case.INSENSITIVE);
            }
        }
        query.endGroup();

        return query;
    }
}