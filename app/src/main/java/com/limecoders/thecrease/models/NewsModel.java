package com.limecoders.thecrease.models;

public class NewsModel {

    private String id;
    private String writerName;
    private String writerImage;
    private String dateTime;
    private String newsTitle;
    private String newsDesc;
    private String newsImage;
    private String category;
    private String tags;
    private String mainCategory;

    public String getMainCategory() {
        return mainCategory;
    }

    public NewsModel(String id, String writerName, String writerImage, String dateTime, String newsTitle, String newsDesc, String newsImage, String category, String tags, String mainCategory) {
        this.id = id;
        this.writerName = writerName;
        this.writerImage = writerImage;
        this.dateTime = dateTime;
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
        this.newsImage = newsImage;
        this.category = category;
        this.tags = tags;
        this.mainCategory = mainCategory;
    }

    public String getId() {
        return id;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getWriterImage() {
        return writerImage;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }

}
