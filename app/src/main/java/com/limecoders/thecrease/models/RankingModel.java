package com.limecoders.thecrease.models;

public class RankingModel {
    private String id;
    private String teamName;
    private int teamPos;
    private int teamMatches;
    private String teamId;
    private double teamRating;
    private double teamPoints;
    private String teamType;

    public String getTeamType() {
        return teamType;
    }

    public RankingModel(String id, String teamName, int teamPos, int teamMatches, String teamId, double teamRating, double teamPoints, String teamType) {
        this.id = id;
        this.teamName = teamName;
        this.teamPos = teamPos;
        this.teamMatches = teamMatches;
        this.teamId = teamId;
        this.teamRating = teamRating;
        this.teamPoints = teamPoints;
        this.teamType = teamType;
    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamPos() {
        return teamPos;
    }

    public int getTeamMatches() {
        return teamMatches;
    }

    public String getTeamId() {
        return teamId;
    }

    public double getTeamRating() {
        return teamRating;
    }

    public double getTeamPoints() {
        return teamPoints;
    }

}
