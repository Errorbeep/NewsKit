package com.example.newskit.pojo;

import java.io.Serializable;
import java.util.List;

public class CardItem implements Serializable {

    private int resourceId;
    private String header;
    private List<TopNews> news;

    public CardItem(int resourceId, String header, List<TopNews> news) {
        this.resourceId = resourceId;
        this.header = header;
        this.news = news;
    }

    public List<TopNews> getNews() {
        return news;
    }

    public void setNews(List<TopNews> news) {
        this.news = news;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public CardItem() {
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "CardItem{" +
                "resourceId=" + resourceId +
                ", news=" + news +
                '}';
    }
}
