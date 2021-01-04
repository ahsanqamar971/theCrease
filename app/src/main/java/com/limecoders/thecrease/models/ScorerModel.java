package com.limecoders.thecrease.models;

public class ScorerModel {
    private String name;
    private String password;
    private String role;
    private String sex;
    private int age;
    private String religion;
    private String email;
    private String phoneNumber;
    private int matchesScored;
    private String education;
    private String experience;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getSex() {
        return sex;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMatchesScored() {
        return matchesScored;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public ScorerModel(String name, String password, String role, String sex, int age, String religion, String email, String phoneNumber, int matchesScored, String education, String experience) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.sex = sex;
        this.age = age;
        this.religion = religion;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.matchesScored = matchesScored;
        this.education = education;
        this.experience = experience;
    }
}
