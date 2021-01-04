package com.limecoders.thecrease.models;

public class PlayerModel {
    private String id;
    private String teamName;
    private boolean isInTeam;
    private String role;
    private String fullName;
    private String profileImage;
    private String batType;
    private String dob;
    private String batStyle;
    private String bowlType;
    private String rank;
    private String level;

    public String getPlayerRank() {
        return rank;
    }

    public String getPlayerLevel() {
        return level;
    }

    public String getPlayerName() {
        return fullName;
    }

    public PlayerModel(String id, String teamName, boolean isInTeam, String role, String fullName, String profileImage, String batType, String dob, String batStyle, String bowlType, String rank, String level) {
        this.id = id;
        this.teamName = teamName;
        this.isInTeam = isInTeam;
        this.role = role;
        this.fullName = fullName;
        this.profileImage = profileImage;
        this.batType = batType;
        this.dob = dob;
        this.batStyle = batStyle;
        this.bowlType = bowlType;
        this.rank = rank;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isInTeam() {
        return isInTeam;
    }

    public String getRole() {
        return role;
    }

    public String getPlayerImage() {
        return profileImage;
    }

    public String getBatType() {
        return batType;
    }

    public String getDob() {
        return dob;
    }

    public String getBatStyle() {
        return batStyle;
    }

    public String getBowlType() {
        return bowlType;
    }

}
