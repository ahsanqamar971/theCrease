package com.limecoders.thecrease.models;

public class CoachModel {
    private String id;
    private String name;
    private String phoneNumber;
    private String sex;
    private String role;
    private int age;
    private String religion;
    private String email;
    private double experience;
    private String teamName;
    private String teamId;
    private String info;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String getReligion() {
        return religion;
    }

    public String getEmail() {
        return email;
    }

    public double getExperience() {
        return experience;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getInfo() {
        return info;
    }

    public boolean isCricketBackground() {
        return cricketBackground;
    }

    public String getEducation() {
        return education;
    }

    private boolean cricketBackground;
    private String education;

    public CoachModel(String id, String name, String phoneNumber, String sex, String role, int age, String religion, String email, double experience, String teamName, String teamId, String info, boolean cricketBackground, String education) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.role = role;
        this.age = age;
        this.religion = religion;
        this.email = email;
        this.experience = experience;
        this.teamName = teamName;
        this.teamId = teamId;
        this.info = info;
        this.cricketBackground = cricketBackground;
        this.education = education;
    }
}
