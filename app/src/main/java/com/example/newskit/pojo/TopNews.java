package com.example.newskit.pojo;

import java.io.Serializable;

public class TopNews implements Serializable {
    private String title;
    private String description;
    private String thumbnail;
    private String url;
    private String domain;
    private String siteName;
    private String logo;
    private String views;

    public TopNews(String title, String description, String thumbnail, String url, String domain, String siteName, String logo, String views) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.url = url;
        this.domain = domain;
        this.siteName = siteName;
        this.logo = logo;
        this.views = views;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDomain() {
        return domain;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getLogo() {
        return logo;
    }

    public String getViews() {
        return views;
    }
}
