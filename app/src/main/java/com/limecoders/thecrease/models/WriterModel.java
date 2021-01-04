package com.limecoders.thecrease.models;

public class WriterModel {
    private String id;
    private String fullName;
    private String profileImage;
    private String experience;
    private String education;
    private int age;
    private String sex;
    private String email;
    private String role;

    public String getRole() {
        return role;
    }

    public WriterModel(String id, String fullName, String profileImage, String experience, String education, int age, String sex, String email, String role, String phoneNumber, String religion, int articlesWritten) {
        this.id = id;
        this.fullName = fullName;
        this.profileImage = profileImage;
        this.experience = experience;
        this.education = education;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.religion = religion;
        this.articlesWritten = articlesWritten;
    }

    private String phoneNumber;
    private String religion;
    private int articlesWritten;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getExperience() {
        return experience;
    }

    public String getEducation() {
        return education;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getReligion() {
        return religion;
    }

    public int getArticlesWritten() {
        return articlesWritten;
    }

}
