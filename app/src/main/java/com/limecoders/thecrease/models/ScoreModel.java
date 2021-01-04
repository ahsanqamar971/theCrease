package com.limecoders.thecrease.models;


public class ScoreModel {

    private String leagueName, venue, team1Name, team2Name, team1Id, team2Id,
            won, dateTime, momName, team1Image, team2Image, momPlayerImage, id, matchCondition, team1Players, team2Players;

    private int score1, score2;
    private double overs1, overs2;

    public String getLeagueName() {
        return leagueName;
    }

    public String getVenue() {
        return venue;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public String getWon() {
        return won;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getMomName() {
        return momName;
    }

    public String getTeam1Image() {
        return team1Image;
    }

    public String getTeam2Image() {
        return team2Image;
    }

    public String getMomPlayerImage() {
        return momPlayerImage;
    }

    public String getId() {
        return id;
    }

    public String getMatchCondition() {
        return matchCondition;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public double getOvers1() {
        return overs1;
    }

    public double getOvers2() {
        return overs2;
    }

    public ScoreModel(String leagueName, String venue, String team1Name, String team2Name, String won, String dateTime, String momName, String team1Image, String team2Image, String momPlayerImage, String id, String matchCondition, int score1, int score2, double overs1, double overs2) {
        this.leagueName = leagueName;
        this.venue = venue;
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.won = won;
        this.dateTime = dateTime;
        this.momName = momName;
        this.team1Image = team1Image;
        this.team2Image = team2Image;
        this.momPlayerImage = momPlayerImage;
        this.id = id;
        this.matchCondition = matchCondition;
        this.score1 = score1;
        this.score2 = score2;
        this.overs1 = overs1;
        this.overs2 = overs2;
    }
}
