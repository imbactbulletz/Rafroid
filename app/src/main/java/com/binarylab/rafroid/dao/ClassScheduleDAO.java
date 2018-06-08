package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.ClassDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.model.ClassSchedule;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.util.DateUtil;

import io.realm.Realm;

public class ClassScheduleDAO {

    private static ClassScheduleDAO instance;

    private ClassScheduleDAO(){}

    public static ClassScheduleDAO getInstance() {
        if(instance == null)
            instance = new ClassScheduleDAO();
        return instance;
    }

    public void clear(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(ClassSchedule.class);
        realm.commitTransaction();
    }

    public void saveClassScheduleFromDTO(ClassesDTO dtos){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for(ClassDTO dto:dtos.getSchedule()){
            ClassSchedule cs = new ClassSchedule();
            cs.setClassName(dto.getClass_name());
            cs.setClassroom(dto.getClassroom());
            cs.setDayOfWeek(DayOfWeek.valueOf(dto.getDay_of_week()));
            cs.setLecturer(dto.getLecturer());
            cs.setStudentGroups(dto.getStudent_groups());
            cs.setType(cs.getType());
            cs.setStartTime(DateUtil.parseTime(dto.getTime(),true));
            cs.setEndTime(DateUtil.parseTime(dto.getTime(),false));
            realm.copyToRealm(cs);
        }

        realm.commitTransaction();

    }

}
