package com.limecoders.thecrease.models;

public class TourTableModel {
    private String id;
    private String teamName;
    private String rank;
    private String points;
    private String noResults;
    private String matchesPlayed;
    private String matchesWin;
    private String matchesLose;
    private String matchesTie;
    private String netRating;

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRank() {
        return rank;
    }

    public String getPoints() {
        return points;
    }

    public String getNoResults() {
        return noResults;
    }

    public String getMatchesPlayed() {
        return matchesPlayed;
    }

    public String getMatchesWin() {
        return matchesWin;
    }

    public String getMatchesLose() {
        return matchesLose;
    }

    public String getMatchesTie() {
        return matchesTie;
    }

    public String getNetRating() {
        return netRating;
    }

    public TourTableModel(String id, String teamName, String rank, String points, String noResults, String matchesPlayed, String matchesWin, String matchesLose, String matchesTie, String netRating) {
        this.id = id;
        this.teamName = teamName;
        this.rank = rank;
        this.points = points;
        this.noResults = noResults;
        this.matchesPlayed = matchesPlayed;
        this.matchesWin = matchesWin;
        this.matchesLose = matchesLose;
        this.matchesTie = matchesTie;
        this.netRating = netRating;
    }
}
