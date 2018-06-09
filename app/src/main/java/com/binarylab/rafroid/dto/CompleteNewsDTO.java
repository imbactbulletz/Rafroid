package com.binarylab.rafroid.dto;

import java.util.List;

public class CompleteNewsDTO {

    private List<NewsDTO> news;

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        this.news = news;
    }
}
