package com.limecoders.thecrease.models;

public class TournamentModel {
    private String id;
    private String tourName;
    private String tourDate;
    private String tourDesc;

    public String getId() {
        return id;
    }

    public String getTourName() {
        return tourName;
    }

    public String getTourDate() {
        return tourDate;
    }

    public String getTourDesc() {
        return tourDesc;
    }

    public int getTeams() {
        return teams;
    }

    public String getLevel() {
        return level;
    }

    private int teams;
    private String level;
    private String condition;

    public String getCondition() {
        return condition;
    }

    public TournamentModel(String id, String tourName, String tourDate, String tourDesc, int teams, String level, String condition) {
        this.id = id;
        this.tourName = tourName;
        this.tourDate = tourDate;
        this.tourDesc = tourDesc;
        this.teams = teams;
        this.level = level;
        this.condition = condition;
    }
}
