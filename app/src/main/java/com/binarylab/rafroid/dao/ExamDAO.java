package com.binarylab.rafroid.dao;

import android.content.SharedPreferences;

import com.binarylab.rafroid.dto.ExamDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.model.DayOfWeek;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;

public class ExamDAO {

    private static ExamDAO instance;

    private ExamDAO() {
    }

    public static ExamDAO getInstance() {
        if (instance == null)
            instance = new ExamDAO();
        return instance;
    }

    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Exam.class);
        realm.commitTransaction();
    }

    public void saveExamsFromDTO(ExamsDTO dtos) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (ExamDTO dto : dtos.getSchedule()) {
            Exam exam = new Exam();
            exam.setTestName(dto.getTest_name());
            exam.setClassroom(dto.getClassroom());
            exam.setType(ExamType.valueOf(dto.getType()));
            exam.setProfessor(dto.getProfessor());
            exam.setStartTime(DateUtil.parseDateAndTime(dto.getDate_and_time(), true));
            exam.setEndTime(DateUtil.parseDateAndTime(dto.getDate_and_time(), false));
            realm.copyToRealm(exam);
        }

        realm.commitTransaction();

    }

    public List<Exam> getAllExams() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Exam.class).equalTo("type", ExamType.EXAM.toString()).findAll();
    }

    public List<Exam> getAllExamsPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);
        RealmQuery<Exam> query = realm.where(Exam.class).equalTo("type", ExamType.EXAM.toString());

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("testName", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("testName", subject, Case.INSENSITIVE);
            }
        }
        query.endGroup();

        return query.findAll();
    }

    public List<Exam> getAllCurriculums() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Exam.class).equalTo("type", ExamType.CURRICULUM.toString()).findAll();
    }

    public List<Exam> getAllCurriculumsPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);
        RealmQuery<Exam> query = realm.where(Exam.class).equalTo("type", ExamType.CURRICULUM.toString());

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("testName", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("testName", subject, Case.INSENSITIVE);
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
        for (Exam exam : realm.where(Exam.class).distinct("classroom").findAll().sort("classroom"))
            set.add(exam.getClassroom());

        return set;
    }

    public List<String> getAllLecturers() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Exam exam : realm.where(Exam.class).distinct("professor").findAll().sort("professor"))
            set.add(exam.getProfessor());

        return set;
    }

    public List<String> getAllSubjects() {
        Realm realm = Realm.getDefaultInstance();
        List<String> set = new ArrayList<>();
        for (Exam exam : realm.where(Exam.class).distinct("testName").findAll())
            set.add(exam.getTestName());

        return set;
    }

    public RealmQuery<Exam> getExamQueryBuilder() {
        return Realm.getDefaultInstance().where(Exam.class).equalTo("type", ExamType.EXAM.toString());
    }

    public RealmQuery<Exam> getExamQueryBuilderPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);
        RealmQuery<Exam> query = realm.where(Exam.class).equalTo("type", ExamType.EXAM.toString());

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("testName", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("testName", subject, Case.INSENSITIVE);
            }
        }
        query.endGroup();

        return query;
    }

    public RealmQuery<Exam> getCurriculumQueryBuilder() {
        return Realm.getDefaultInstance().where(Exam.class).equalTo("type", ExamType.CURRICULUM.toString());
    }

    public RealmQuery<Exam> getCurriculumQueryBuilderPersonalized(SharedPreferences sharedPreferences) {
        Realm realm = Realm.getDefaultInstance();
        ClassScheduleDAO dao = ClassScheduleDAO.getInstance();
        List<String> subjects = dao.getAllSubjectsPersonalized(sharedPreferences);
        RealmQuery<Exam> query = realm.where(Exam.class).equalTo("type", ExamType.CURRICULUM.toString());

        query.and().beginGroup();
        boolean isFirst = true;
        for (String subject : subjects) {

            if (isFirst) {
                query.contains("testName", subject, Case.INSENSITIVE);
                isFirst = false;
            } else {
                query.or().contains("testName", subject, Case.INSENSITIVE);
            }
        }
        query.endGroup();

        return query;
    }
}