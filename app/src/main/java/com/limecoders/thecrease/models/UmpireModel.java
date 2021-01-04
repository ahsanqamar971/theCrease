package com.limecoders.thecrease.models;

public class UmpireModel {
    private String id;
    private String fullName;
    private int age;
    private String sex;
    private String email;
    private String role;
    private String religion;
    private String tour1;
    private String tour2;
    private String phoneNumber;
    private String experience;
    private String education;
    private String rank;
    private String level;

    public int getMatches() {
        return matches;
    }

    public UmpireModel(String id, String fullName, int age, String sex, String email, String role, String religion, String tour1, String tour2, String phoneNumber, String experience, String education, String rank, String level, int matches) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.role = role;
        this.religion = religion;
        this.tour1 = tour1;
        this.tour2 = tour2;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
        this.education = education;
        this.rank = rank;
        this.level = level;
        this.matches = matches;
    }

    private int matches;

    public String getRank() {
        return rank;
    }

    public String getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
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

    public String getRole() {
        return role;
    }

    public String getReligion() {
        return religion;
    }

    public String getTour1() {
        return tour1;
    }

    public String getTour2() {
        return tour2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getExperience() {
        return experience;
    }

    public String getEducation() {
        return education;
    }

}
