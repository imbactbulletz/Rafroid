package com.binarylab.rafroid.dao;

import android.content.SharedPreferences;

import com.binarylab.rafroid.dto.ClassDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.model.ClassSchedule;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.util.DateUtil;
import com.binarylab.rafroid.util.SharedPreferencesKeyStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<ClassSchedule> getAllPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ClassSchedule> query = realm.where(ClassSchedule.class);

        String stringGroups = sharedPreferences.getString(SharedPreferencesKeyStore.USER_GROUPS, null);
        Set<String> subjects = sharedPreferences.getStringSet(SharedPreferencesKeyStore.USER_SUBJECTS, null);

        boolean isFirst = false;
        if(stringGroups != null && !stringGroups.isEmpty()){
            query = query.and().beginGroup();

            query = query.contains("studentGroups", stringGroups.split(" ")[0]);
            for (int i = 1; i < stringGroups.split(" ").length; i++)
                query = query.or().contains("studentGroups", stringGroups.split(" ")[i]);

            query.endGroup();

            isFirst = true;
        }

        if (subjects != null && subjects.size() > 0) {
            if (isFirst)
                query = query.or().beginGroup();
            else
                query = query.and().beginGroup();

            isFirst = true;
            for (String subj : subjects) {
                if (isFirst) {
                    query = query.contains("className", subj);
                    isFirst = false;
                } else query = query.or().contains("className", subj);
            }

            query.endGroup();
        }

        return query.findAll();
    }

    public List<String> getAllTypes() {
        List<String> set = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for (ClassSchedule sch : realm.where(ClassSchedule.class).distinct("type").findAll().sort("type")) {
            set.add(sch.getType());
        }

        return set;
    }

    public List<String> getAllLecturers(){
        List<String> set = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("lecturer").findAll().sort("lecturer")){
            set.add(sch.getLecturer());
        }

        return set;
    }

    public List<String> getAllDayOfWeeks() {
        List<String> set = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            set.add(day.toString());
        }

        return set;
    }

    public List<String> getAllClassrooms(){
        List<String> set = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("classroom").findAll().sort("classroom")){
            set.add(sch.getClassroom());
        }

        return set;
    }

    public List<String> getAllSubjects(){
        List<String> set = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        for(ClassSchedule sch: realm.where(ClassSchedule.class).distinct("className").findAll().sort("className")){
            set.add(sch.getClassName());
        }

        return set;
    }

    public List<String> getAllSubjectsPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ClassSchedule> query = realm.where(ClassSchedule.class);

        String stringGroups = sharedPreferences.getString(SharedPreferencesKeyStore.USER_GROUPS, null);
        Set<String> subjects = sharedPreferences.getStringSet(SharedPreferencesKeyStore.USER_SUBJECTS, null);

        boolean isFirst = false;
        if (stringGroups != null && !stringGroups.isEmpty()) {
            query = query.and().beginGroup();

            query = query.contains("studentGroups", stringGroups.split(" ")[0]);
            for (int i = 1; i < stringGroups.split(" ").length; i++)
                query = query.or().contains("studentGroups", stringGroups.split(" ")[i]);

            query.endGroup();

            isFirst = true;
        }

        if (subjects != null && subjects.size() > 0) {
            if (isFirst)
                query = query.or().beginGroup();
            else
                query = query.and().beginGroup();

            isFirst = true;
            for (String subj : subjects) {
                if (isFirst) {
                    query = query.contains("className", subj);
                    isFirst = false;
                } else query = query.or().contains("className", subj);
            }

            query.endGroup();
        }

        List<String> set = new ArrayList<>();
        for (ClassSchedule sch : query.distinct("className").findAll().sort("className")) {
            set.add(sch.getClassName());
        }

        return set;
    }

    public RealmQuery<ClassSchedule> getQueryBuilder(){
        return Realm.getDefaultInstance().where(ClassSchedule.class);
    }


    public RealmQuery<ClassSchedule> getQueryBuilderPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ClassSchedule> query = realm.where(ClassSchedule.class);

        String stringGroups = sharedPreferences.getString(SharedPreferencesKeyStore.USER_GROUPS, null);
        Set<String> subjects = sharedPreferences.getStringSet(SharedPreferencesKeyStore.USER_SUBJECTS, null);

        boolean isFirst = false;
        if (stringGroups != null && !stringGroups.isEmpty()) {
            query = query.and().beginGroup();

            query = query.contains("studentGroups", stringGroups.split(" ")[0]);
            for (int i = 1; i < stringGroups.split(" ").length; i++)
                query = query.or().contains("studentGroups", stringGroups.split(" ")[i]);

            query.endGroup();

            isFirst = true;
        }

        if (subjects != null && subjects.size() > 0) {
            if (isFirst)
                query = query.or().beginGroup();
            else
                query = query.and().beginGroup();

            isFirst = true;
            for (String subj : subjects) {
                if (isFirst) {
                    query = query.contains("className", subj);
                    isFirst = false;
                } else query = query.or().contains("className", subj);
            }

            query.endGroup();
        }

        return query;
    }
}
