package com.limecoders.thecrease.models;

public class TeamModel {
    private String id;
    private String teamName;
    private String teamInfo;
    private String teamAddress;
    private String teamCaptain;
    private int teamPitches;
    private int squadNumber;
    private int coachNumber;

    public String getTeamAddress() {
        return teamAddress;
    }

    public String getTeamCaptain() {
        return teamCaptain;
    }

    public int getTeamPitches() {
        return teamPitches;
    }

    public int getSquadNumber() {
        return squadNumber;
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getLevel() {
        return level;
    }

    public TeamModel(String id, String teamName, String teamInfo, String teamAddress, String teamCaptain, int teamPitches, int squadNumber, int coachNumber, String teamSlogan, String profileImage, String level, int matchesWon, int matchesPlayed, int matchesLose, int matchesTie, int matchesNR, double teamRating, int teamRank, String registeredYear, String venue, int tourWon, int tourPlayed, int tourLose, int tourTie, int tourNR, String office, String phoneNumber, String email) {
        this.id = id;
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.teamAddress = teamAddress;
        this.teamCaptain = teamCaptain;
        this.teamPitches = teamPitches;
        this.squadNumber = squadNumber;
        this.coachNumber = coachNumber;
        this.teamSlogan = teamSlogan;
        this.profileImage = profileImage;
        this.level = level;
        this.matchesWon = matchesWon;
        this.matchesPlayed = matchesPlayed;
        this.matchesLose = matchesLose;
        this.matchesTie = matchesTie;
        this.matchesNR = matchesNR;
        this.teamRating = teamRating;
        this.teamRank = teamRank;
        this.registeredYear = registeredYear;
        this.venue = venue;
        this.tourWon = tourWon;
        this.tourPlayed = tourPlayed;
        this.tourLose = tourLose;
        this.tourTie = tourTie;
        this.tourNR = tourNR;
        this.office = office;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    private String teamSlogan;
    private String profileImage;
    private String level;
    private int matchesWon;
    private int matchesPlayed;
    private int matchesLose;
    private int matchesTie;
    private int matchesNR;
    private double teamRating;
    private int teamRank;
    private String registeredYear;
    private String venue;
    private int tourWon;
    private int tourPlayed;
    private int tourLose;
    private int tourTie;
    private int tourNR;
    private String office;
    private String phoneNumber;
    private String email;

    public String getTeamName() {
        return teamName;
    }

    public String getTeamInfo() {
        return teamInfo;
    }

    public String getTeamSlogan() {
        return teamSlogan;
    }

    public String getTeamImage() {
        return profileImage;
    }

    public String getTeamType() {
        return level;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getMatchesLose() {
        return matchesLose;
    }

    public int getMatchesTie() {
        return matchesTie;
    }

    public int getMatchesNR() {
        return matchesNR;
    }

    public double getTeamRating() {
        return teamRating;
    }

    public int getTeamRank() {
        return teamRank;
    }

    public String getRegisteredYear() {
        return registeredYear;
    }

    public String getVenue() {
        return venue;
    }

    public int getTourWon() {
        return tourWon;
    }

    public int getTourPlayed() {
        return tourPlayed;
    }

    public int getTourLose() {
        return tourLose;
    }

    public int getTourTie() {
        return tourTie;
    }

    public int getTourNR() {
        return tourNR;
    }

    public String getOffice() {
        return office;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
