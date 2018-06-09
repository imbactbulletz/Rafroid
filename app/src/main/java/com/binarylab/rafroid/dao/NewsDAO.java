package com.binarylab.rafroid.dao;

import com.binarylab.rafroid.dto.CompleteNewsDTO;
import com.binarylab.rafroid.dto.ExamDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.dto.NewsDTO;
import com.binarylab.rafroid.model.Exam;
import com.binarylab.rafroid.model.ExamType;
import com.binarylab.rafroid.model.News;
import com.binarylab.rafroid.util.DateUtil;

import java.util.List;

import io.realm.Realm;

public class NewsDAO {
    private static NewsDAO instance;

    private NewsDAO(){}

    public static NewsDAO getInstance() {
        if(instance == null)
            instance = new NewsDAO();
        return instance;
    }

    public void clear(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(News.class);
        realm.commitTransaction();
    }

    public void saveNewsFromDTO(CompleteNewsDTO dtos){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for(NewsDTO dto:dtos.getNews()){
            News news = new News();
            news.setText(dto.getText());
            news.setTitle(dto.getTitle());
            news.setDate(dto.getDate());
            realm.copyToRealm(news);
        }

        realm.commitTransaction();

    }

    public List<News> getAll(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(News.class).findAll();
    }
}
