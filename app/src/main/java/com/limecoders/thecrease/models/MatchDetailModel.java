package com.limecoders.thecrease.models;

public class MatchDetailModel {
    private String id;
    private String playerName;
    private String runs;
    private String ballsPlayed;
    private String fours;
    private String sixes;
    private String strikeRate;
    private String fow;
    private String catchBy;
    private String ballBy;
    private boolean isCatch;
    private boolean isBowled;
    private boolean isLbw;
    private boolean isRetire;
    private boolean isRunOut;
    private boolean isOther;
    private boolean isStump;
    private String overs;
    private String wickets;
    private String economy;
    private String bb;

    public String getBowlerId() {
        return bowlerId;
    }

    private String fRonOut;
    private String fCatches;
    private String fStumps;
    private String ballStatus;
    private String fielderName;
    private String teamName;
    private String playerId;
    private String bowlerId;
    private String status;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getBallStatus() {
        return ballStatus;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRuns() {
        return runs;
    }

    public String getBallsPlayed() {
        return ballsPlayed;
    }

    public String getFours() {
        return fours;
    }

    public String getSixes() {
        return sixes;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public String getFow() {
        return fow;
    }

    public String getCatchBy() {
        return catchBy;
    }

    public String getBallBy() {
        return ballBy;
    }

    public boolean isCatch() {
        return isCatch;
    }

    public boolean isBowled() {
        return isBowled;
    }

    public boolean isLbw() {
        return isLbw;
    }

    public boolean isRetire() {
        return isRetire;
    }

    public boolean isRunOut() {
        return isRunOut;
    }

    public boolean isOther() {
        return isOther;
    }

    public String getOvers() {
        return overs;
    }

    public String getWickets() {
        return wickets;
    }

    public String getEconomy() {
        return economy;
    }

    public String getBb() {
        return bb;
    }

    public String getfRonOut() {
        return fRonOut;
    }

    public String getfCatches() {
        return fCatches;
    }

    public String getFielderName() {
        return fielderName;
    }

    public boolean isStump() {
        return isStump;
    }

    public String getTeamName() {
        return teamName;
    }

    public MatchDetailModel(String id, String playerName, String runs, String ballsPlayed, String fours, String sixes,
                            String strikeRate, String fow, String catchBy, String ballBy, boolean isCatch,
                            boolean isBowled, boolean isLbw, boolean isRetire, boolean isRunOut, boolean isOther, boolean isStump,
                            String overs, String wickets, String economy, String bb, String fRonOut, String fCatches,
                            String fStumps, String ballStatus, String fielderName, String teamName, String playerId,
                            String bowlerId, String status, String timeStamp) {
        this.id = id;
        this.playerName = playerName;
        this.runs = runs;
        this.ballsPlayed = ballsPlayed;
        this.fours = fours;
        this.sixes = sixes;
        this.strikeRate = strikeRate;
        this.fow = fow;
        this.catchBy = catchBy;
        this.ballBy = ballBy;
        this.isCatch = isCatch;
        this.isBowled = isBowled;
        this.isLbw = isLbw;
        this.isRetire = isRetire;
        this.isRunOut = isRunOut;
        this.isOther = isOther;
        this.isStump = isStump;
        this.overs = overs;
        this.wickets = wickets;
        this.economy = economy;
        this.bb = bb;
        this.fRonOut = fRonOut;
        this.fCatches = fCatches;
        this.fStumps = fStumps;
        this.ballStatus = ballStatus;
        this.fielderName = fielderName;
        this.teamName = teamName;
        this.playerId = playerId;
        this.bowlerId = bowlerId;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getfStumps() {
        return fStumps;
    }
}
