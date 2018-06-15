package com.binarylab.rafroid.dao;

import android.util.Log;

import com.binarylab.rafroid.dto.ClassDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.model.ClassSchedule;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.util.DateUtil;

import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

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
            cs.setClassroom(dto.getClassroom().trim());
            cs.setDayOfWeek(DayOfWeek.valueOf(dto.getDay_of_week()));
            cs.setLecturer(dto.getLecturer());
            cs.setStudentGroups(dto.getStudent_groups().trim());
            cs.setType(dto.getType());
            cs.setStartTime(DateUtil.parseTime(dto.getTime(),true));
            cs.setEndTime(DateUtil.parseTime(dto.getTime(),false));
            realm.copyToRealm(cs);
        }

        realm.commitTransaction();

    }

    public List<ClassSchedule> getAll(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(ClassSchedule.class).findAll();
    }

    public HashSet<String> getAllTypes(){
        HashSet<String> set = new HashSet<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("type").findAll()){
            set.add(sch.getType());
        }

        return set;
    }

    public HashSet<String> getAllLecturers(){
        HashSet<String> set = new HashSet<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("lecturer").findAll()){
            set.add(sch.getLecturer());
        }

        return set;
    }

    public HashSet<String> getAllDayOfWeeks(){
        HashSet<String> set = new HashSet<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("dayOfWeek").findAll()){
            set.add(sch.getDayOfWeek().toString());
        }

        return set;
    }

    public HashSet<String> getAllClassrooms(){
        HashSet<String> set = new HashSet<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("classroom").findAll()){
            set.add(sch.getClassroom());
        }

        return set;
    }

    public HashSet<String> getAllSubjects(){
        HashSet<String> set = new HashSet<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("className").findAll()){
            set.add(sch.getClassName());
        }

        return set;
    }

    public RealmQuery<ClassSchedule> getQueryBuilder(){
        return Realm.getDefaultInstance().where(ClassSchedule.class);
    }
}
