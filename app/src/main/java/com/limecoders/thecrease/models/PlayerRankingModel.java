package com.limecoders.thecrease.models;

public class PlayerRankingModel {
    private String id;
    private String playerId;
    private String name;
    private String teamName;
    private String rating;
    private String rank;

    public String getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRating() {
        return rating;
    }

    public String getRank() {
        return rank;
    }

    public PlayerRankingModel(String id, String playerId, String name, String teamName, String rating, String rank) {
        this.id = id;
        this.playerId = playerId;
        this.name = name;
        this.teamName = teamName;
        this.rating = rating;
        this.rank = rank;
    }
}
