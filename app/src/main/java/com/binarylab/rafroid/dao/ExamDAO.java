package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.ExamDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.util.DateUtil;

import io.realm.Realm;

public class ExamDAO {

    private static ExamDAO instance;

    private ExamDAO(){}

    public static ExamDAO getInstance() {
        if(instance == null)
            instance = new ExamDAO();
        return instance;
    }

    public void clear(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Exam.class);
        realm.commitTransaction();
    }

    public void saveExamsFromDTO(ExamsDTO dtos){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

            for(ExamDTO dto:dtos.getSchedule()){
                Exam exam = new Exam();
                exam.setTestName(dto.getTest_name());
                exam.setClassroom(dto.getClassroom());
                exam.setType(ExamType.valueOf(dto.getType()));
                exam.setProfessor(dto.getProfessor());
                exam.setStartTime(DateUtil.parseDateAndTime(dto.getDate_and_time(),true));
                exam.setEndTime(DateUtil.parseDateAndTime(dto.getDate_and_time(), false));
                realm.copyToRealm(exam);
            }

        realm.commitTransaction();

    }
}