package com.limecoders.thecrease.models;

public class ProductModel {

    private String id;
    private String proName;
    private String proModel;
    private String proPrice;
    private String dateTime;
    private String proImage;
    private String proCategory;

    public ProductModel(String id, String proName, String proModel, String proPrice, String dateTime, String proImage, String proCategory) {
        this.id = id;
        this.proName = proName;
        this.proModel = proModel;
        this.proPrice = proPrice;
        this.dateTime = dateTime;
        this.proImage = proImage;
        this.proCategory = proCategory;
    }

    public String getId() {
        return id;
    }

    public String getProName() {
        return proName;
    }

    public String getProModel() {
        return proModel;
    }

    public String getProPrice() {
        return proPrice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getProImage() {
        return proImage;
    }

    public String getProCategory() {
        return proCategory;
    }
}
