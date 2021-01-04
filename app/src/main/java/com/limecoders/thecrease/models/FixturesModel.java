package com.limecoders.thecrease.models;


public class FixturesModel {

    private String id;
    private String team1;
    private String team2;
    private String venue;
    private String tournId;
    private String tournName;
    private String matchCondition;
    private String team1Image;
    private String team2Image;
    private String overs;

    public String getDateTime() {
        return dateTime;
    }

    public String getOvers() {
        return overs;
    }

    public FixturesModel(String id, String team1, String team2, String venue, String tournId, String tournName, String matchCondition, String team1Image, String team2Image, String dateTime, String overs) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.tournId = tournId;
        this.tournName = tournName;
        this.matchCondition = matchCondition;
        this.team1Image = team1Image;
        this.team2Image = team2Image;
        this.dateTime = dateTime;
        this.overs = overs;
    }

    private String dateTime;

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getVenue() {
        return venue;
    }

    public String getTournId() {
        return tournId;
    }

    public String getTournName() {
        return tournName;
    }

    public String getMatchCondition() {
        return matchCondition;
    }

    public String getTeam1Image() {
        return team1Image;
    }

    public String getTeam2Image() {
        return team2Image;
    }

}
