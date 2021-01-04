package com.limecoders.thecrease.models;

import java.io.Serializable;

public class TourModel implements Serializable {

    private String id;

    public String getTourName() {
        return tourName;
    }

    private String tourName;
    private String teamIds;
    private String teamPlayers;
    private String date;
    private String venue;
    private String time;
    private String type;
    private String level;
    private boolean isApproved;
    private boolean isRejected;
    private boolean scorerApproved;
    private boolean scorerRejected;

    public boolean isScorerApproved() {
        return scorerApproved;
    }

    public boolean isScorerRejected() {
        return scorerRejected;
    }

    public String getId() {
        return id;
    }

    public String getTeamIds() {
        return teamIds;
    }

    public String getTeamPlayers() {
        return teamPlayers;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public TourModel(String id, String teamIds, String teamPlayers, String date, String venue, String time, String type,
                     String level, boolean isApproved, boolean isRejected, boolean scorerApproved, boolean scorerRejected, String tourName) {
        this.id = id;
        this.teamIds = teamIds;
        this.teamPlayers = teamPlayers;
        this.date = date;
        this.venue = venue;
        this.time = time;
        this.type = type;
        this.level = level;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.scorerApproved = scorerApproved;
        this.scorerRejected = scorerRejected;
        this.tourName = tourName;
    }
}
