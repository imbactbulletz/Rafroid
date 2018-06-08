package com.binarylab.rafroid.model;

import java.util.Date;

import io.realm.RealmObject;

public class News extends RealmObject{

    private Date date;
    private String title;
    private String text;

    //Getters & Setters//
    //--------------------------------------------------------------------------------------------//
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
