package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.CalendarDTO;
import com.binarylab.rafroid.dto.CalendarsDTO;
import com.binarylab.rafroid.model.Calendar;
import com.binarylab.rafroid.model.CalendarType;
import com.binarylab.rafroid.util.DateUtil;

import io.realm.Realm;

public class CalendarDAO {

    private static CalendarDAO instance;

    private CalendarDAO(){}

    public static CalendarDAO getInstance() {
        if(instance == null)
            instance = new CalendarDAO();
        return instance;
    }

    public void clear(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Calendar.class);
        realm.commitTransaction();
    }

    public void saveCalendarsFromDTO(CalendarsDTO dtos){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for(CalendarDTO dto:dtos.getSchedule()){
            Calendar calendar = new Calendar();
            calendar.setType(CalendarType.valueOf(dto.getType()));
            calendar.setStartDate(DateUtil.parseDate(dto.getStart_date()));
            calendar.setEndDate(DateUtil.parseDate(dto.getEnd_date()));
            realm.copyToRealm(calendar);
        }

        realm.commitTransaction();

    }

}
