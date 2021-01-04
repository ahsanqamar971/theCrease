package com.limecoders.thecrease.models;

public class PlayerRequestModel {
    private String id;
    private String playerName;
    private String rank;
    private String playerId;
    private String batHand;
    private String batStyle;
    private String bowlHand;
    private String bowlStyle;
    private String teamId;
    private boolean isApproved;
    private boolean isRejected;
    private boolean isTeam;

    public boolean isTeam() {
        return isTeam;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRank() {
        return rank;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getBatHand() {
        return batHand;
    }

    public String getBatStyle() {
        return batStyle;
    }

    public String getBowlHand() {
        return bowlHand;
    }

    public String getBowlStyle() {
        return bowlStyle;
    }

    public String getTeamId() {
        return teamId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public PlayerRequestModel(String id, String playerName, String rank, String playerId, String batHand, String batStyle,
                              String bowlHand, String bowlStyle, String teamId, boolean isApproved, boolean isRejected,
                              boolean isTeam) {
        this.id = id;
        this.playerName = playerName;
        this.rank = rank;
        this.playerId = playerId;
        this.batHand = batHand;
        this.batStyle = batStyle;
        this.bowlHand = bowlHand;
        this.bowlStyle = bowlStyle;
        this.teamId = teamId;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.isTeam = isTeam;
    }
}
