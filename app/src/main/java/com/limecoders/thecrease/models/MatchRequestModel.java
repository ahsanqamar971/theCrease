package com.limecoders.thecrease.models;

public class MatchRequestModel {
    private String id;

    private String teamId;

    public String getTeamId() {
        return teamId;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public boolean isApproved() {
        return isApproved;
    }

    private String teamName;
    private String type;
    private String venue;
    private String date;
    private String time;
    private boolean isRejected;
    private boolean isApproved;

    public boolean isUmpire1Accepted() {
        return umpire1Accepted;
    }

    public boolean isUmpire1Rejected() {
        return umpire1Rejected;
    }

    private boolean umpire1Accepted;
    private boolean umpire1Rejected;
    private boolean umpire2Rejected;
    private boolean umpire2Accepted;
    private boolean scorerApproved;
    private boolean scorerRejected;
    private boolean isCompleted;
    private boolean isFinished;
    private boolean teamAccepted;
    private boolean teamRejected;

    public boolean isTeamAccepted() {
        return teamAccepted;
    }

    public boolean isTeamRejected() {
        return teamRejected;
    }

    private String team1PlayersId;
    private String team2PlayersId;
    private String team2Id;
    private String umpire1Id;
    private String umpire2Id;
    private String scorerId;
    private String score1;
    private String score2;
    private String momPlayerId;
    private String won;
    private String matchCondition;
    private String leagueName;
    private String overs1;
    private String overs2;

    public String getOvers2() {
        return overs2;
    }

    private String firstInnings;
    private String tossWon;
    private String batFirst;

    public String getTossWon() {
        return tossWon;
    }

    public String getBatFirst() {
        return batFirst;
    }

    public String getFirstInnings() {
        return firstInnings;
    }

    public String getOvers1() {
        return overs1;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }

    public String getMomPlayerId() {
        return momPlayerId;
    }

    public String getWon() {
        return won;
    }

    public String getMatchCondition() {
        return matchCondition;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getTeam1PlayersId() {
        return team1PlayersId;
    }

    public String getTeam2PlayersId() {
        return team2PlayersId;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public boolean isScorerApproved() {
        return scorerApproved;
    }

    public boolean isScorerRejected() {
        return scorerRejected;
    }

    public boolean isUmpire2Rejected() {
        return umpire2Rejected;
    }

    public boolean isUmpire2Accepted() {
        return umpire2Accepted;
    }

    public MatchRequestModel(String id, String teamId, String teamName, String type, String venue, String date,
                             String time, boolean isRejected, boolean isApproved, boolean umpire1Rejected, boolean umpire1Accepted,
                             boolean umpire2Rejected, boolean umpire2Accepted,
                             boolean scorerApproved, boolean scorerRejected, boolean isCompleted, boolean isFinished,
                             boolean teamAccepted, boolean teamRejected,
                             String team1PlayersId, String team2PlayersId, String team2Id, String umpire1Id,
                             String umpire2Id, String scorerId, String score1, String score2, String momPlayerId,
                             String won, String matchCondition, String leagueName, String overs1, String overs2,
                             String firstInnings, String tossWon, String batFirst) {
        this.id = id;
        this.teamId = teamId;
        this.teamName = teamName;
        this.type = type;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.isRejected = isRejected;
        this.isApproved = isApproved;
        this.umpire1Rejected = umpire1Rejected;
        this.umpire1Accepted = umpire1Accepted;
        this.umpire2Rejected = umpire2Rejected;
        this.umpire2Accepted = umpire2Accepted;
        this.scorerApproved = scorerApproved;
        this.scorerRejected = scorerRejected;
        this.team1PlayersId = team1PlayersId;
        this.team2PlayersId = team2PlayersId;
        this.isCompleted = isCompleted;
        this.isFinished = isFinished;
        this.teamAccepted = teamAccepted;
        this.teamRejected = teamRejected;
        this.team2Id = team2Id;
        this.umpire1Id = umpire1Id;
        this.umpire2Id = umpire2Id;
        this.scorerId = scorerId;
        this.score1 = score1;
        this.score2 = score2;
        this.momPlayerId = momPlayerId;
        this.won = won;
        this.matchCondition = matchCondition;
        this.leagueName = leagueName;
        this.overs1 = overs1;
        this.overs2 = overs2;
        this.firstInnings = firstInnings;
        this.tossWon = tossWon;
        this.batFirst = batFirst;
    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getType() {
        return type;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public String getUmpire1Id() {
        return umpire1Id;
    }

    public String getUmpire2Id() {
        return umpire2Id;
    }

    public String getScorerId() {
        return scorerId;
    }
}
