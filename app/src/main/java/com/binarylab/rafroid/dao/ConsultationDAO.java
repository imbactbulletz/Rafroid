package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.ConsultationDTO;
import com.binarylab.rafroid.dto.ConsultationsDTO;
import com.binarylab.rafroid.dto.ExamDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.model.Consultation;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<String> getAllDays() {
        List<String> set = new ArrayList<>();
        for (DayOfWeek dayofWeek : DayOfWeek.values())
            set.add(dayofWeek.toString());

        return set;
    }

    public List<String> getAllClassrooms() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation : realm.where(Consultation.class).distinct("classroom").findAll())
            set.add(consultation.getClassroom());

        return set;
    }

    public List<String> getAllLecturers() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation: realm.where(Consultation.class).distinct("lecturer").findAll())
            set.add(consultation.getLecturer());

        return set;
    }

    public List<String> getAllSubjects() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Consultation consultation : realm.where(Consultation.class).distinct("className").findAll())
            set.add(consultation.getClassName());

        return set;
    }

    public RealmQuery<Consultation> getConsultationQueryBuilder(){
        return Realm.getDefaultInstance().where(Consultation.class);
    }
}