package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.ConsultationDTO;
import com.binarylab.rafroid.dto.ConsultationsDTO;
import com.binarylab.rafroid.dto.ExamDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.model.Consultation;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.util.DateUtil;

import java.util.Date;

import io.realm.Realm;

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
}