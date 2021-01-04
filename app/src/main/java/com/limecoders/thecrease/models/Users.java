package com.limecoders.thecrease.models;

import java.io.Serializable;

public class Users implements Serializable {
    private String id;
    private String email;
    private String role;
    private String age;
    private String sex;
    private String name;
    private String religion;
    private String profileImage;
    private String teamName;
    private boolean isInTeam;
    private String batType;
    private String batStyle;
    private String bowlType;
    private int rank;
    private String level;
    private int matchesWon;
    private int matchesPlayed;
    private int matchesLose;
    private int matchesTie;
    private double matchesNR;
    private String registeredYear;
    private String venue;
    private int tourWon;
    private int tourPlayed;
    private int tourLose;
    private int tourTie;
    private double tourNR;
    private String phoneNumber;
    private String info;
    private int matchScored;
    private String tour1;
    private String tour2;
    private String experience;
    private String preference;
    private String education;
    private String courses;
    private String bowlStyle;
    private String cric_bg;
    private int squadNumber;
    private int coachNumber;
    private int pitchNumber;
    private int runs;
    private double batAvg;
    private double batSR;
    private int batHS;
    private String debut;
    private String recent;
    private double bowlAvg;
    private double bowlSR;
    private double econ;
    private String bb;
    private int wkCatches;
    private int wkStumps;
    private int wkRunOut;
    private int fCatches;
    private int fRunOut;
    private int momAwards;
    private int mosAwards;
    private double rating;
    private String homeGround;
    private String playerRole;
    private boolean isApproved;
    private String address;
    private boolean isInSquad;
    private boolean isRejected;

    public Users(){

    }

    public String getAddress() {
        return address;
    }

    public boolean isInSquad() {
        return isInSquad;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public Users(String id, String email, String role, String age, String sex, String name, String religion,
                 String profileImage, String teamName, boolean isInTeam, String batType, String batStyle,
                 String bowlType, int rank, String level, int matchesWon, int matchesPlayed, int matchesLose,
                 int matchesTie, double matchesNR, String registeredYear, String venue, int tourWon, int tourPlayed,
                 int tourLose, int tourTie, double tourNR, String phoneNumber, String info, int matchScored,
                 String tour1, String tour2, String experience, String preference, String education, String courses,
                 String bowlStyle, String cric_bg, int squadNumber, int coachNumber, int pitchNumber, int runs,
                 double batAvg, double batSR, int batHS, String debut, String recent, double bowlAvg, double bowlSR,
                 double econ, String bb, int wkCatches, int wkStumps, int wkRunOut, int fCatches, int fRunOut,
                 int momAwards, int mosAwards, double rating, String homeGround, String playerRole, boolean isApproved,
                 String address, boolean isInSquad, boolean isRejected) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.age = age;
        this.sex = sex;
        this.name = name;
        this.religion = religion;
        this.profileImage = profileImage;
        this.teamName = teamName;
        this.isInTeam = isInTeam;
        this.batType = batType;
        this.batStyle = batStyle;
        this.bowlType = bowlType;
        this.rank = rank;
        this.level = level;
        this.matchesWon = matchesWon;
        this.matchesPlayed = matchesPlayed;
        this.matchesLose = matchesLose;
        this.matchesTie = matchesTie;
        this.matchesNR = matchesNR;
        this.registeredYear = registeredYear;
        this.venue = venue;
        this.tourWon = tourWon;
        this.tourPlayed = tourPlayed;
        this.tourLose = tourLose;
        this.tourTie = tourTie;
        this.tourNR = tourNR;
        this.phoneNumber = phoneNumber;
        this.info = info;
        this.matchScored = matchScored;
        this.tour1 = tour1;
        this.tour2 = tour2;
        this.experience = experience;
        this.preference = preference;
        this.education = education;
        this.courses = courses;
        this.bowlStyle = bowlStyle;
        this.cric_bg = cric_bg;
        this.squadNumber = squadNumber;
        this.coachNumber = coachNumber;
        this.pitchNumber = pitchNumber;
        this.runs = runs;
        this.batAvg = batAvg;
        this.batSR = batSR;
        this.batHS = batHS;
        this.debut = debut;
        this.recent = recent;
        this.bowlAvg = bowlAvg;
        this.bowlSR = bowlSR;
        this.econ = econ;
        this.bb = bb;
        this.wkCatches = wkCatches;
        this.wkStumps = wkStumps;
        this.wkRunOut = wkRunOut;
        this.fCatches = fCatches;
        this.fRunOut = fRunOut;
        this.momAwards = momAwards;
        this.mosAwards = mosAwards;
        this.rating = rating;
        this.homeGround = homeGround;
        this.playerRole = playerRole;
        this.isApproved = isApproved;
        this.address = address;
        this.isInSquad = isInSquad;
        this.isRejected = isRejected;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getReligion() {
        return religion;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isInTeam() {
        return isInTeam;
    }

    public String getBatType() {
        return batType;
    }

    public String getBatStyle() {
        return batStyle;
    }

    public String getBowlType() {
        return bowlType;
    }

    public int getRank() {
        return rank;
    }

    public String getLevel() {
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

    public double getMatchesNR() {
        return matchesNR;
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

    public double getTourNR() {
        return tourNR;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInfo() {
        return info;
    }

    public int getMatchScored() {
        return matchScored;
    }

    public String getTour1() {
        return tour1;
    }

    public String getTour2() {
        return tour2;
    }

    public String getExperience() {
        return experience;
    }

    public String getPreference() {
        return preference;
    }

    public String getEducation() {
        return education;
    }

    public String getCourses() {
        return courses;
    }

    public String getBowlStyle() {
        return bowlStyle;
    }

    public String getCric_bg() {
        return cric_bg;
    }

    public int getSquadNumber() {
        return squadNumber;
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public int getPitchNumber() {
        return pitchNumber;
    }

    public int getRuns() {
        return runs;
    }

    public double getBatAvg() {
        return batAvg;
    }

    public double getBatSR() {
        return batSR;
    }

    public int getBatHS() {
        return batHS;
    }

    public String getDebut() {
        return debut;
    }

    public String getRecent() {
        return recent;
    }

    public double getBowlAvg() {
        return bowlAvg;
    }

    public double getBowlSR() {
        return bowlSR;
    }

    public double getEcon() {
        return econ;
    }

    public String getBb() {
        return bb;
    }

    public int getWkCatches() {
        return wkCatches;
    }

    public int getWkStumps() {
        return wkStumps;
    }

    public int getWkRunOut() {
        return wkRunOut;
    }

    public int getfCatches() {
        return fCatches;
    }

    public int getfRunOut() {
        return fRunOut;
    }

    public int getMomAwards() {
        return momAwards;
    }

    public int getMosAwards() {
        return mosAwards;
    }

    public double getRating() {
        return rating;
    }

    public String getHomeGround() {
        return homeGround;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public boolean isApproved() {
        return isApproved;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", religion='" + religion + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", teamName='" + teamName + '\'' +
                ", isInTeam=" + isInTeam +
                ", batType='" + batType + '\'' +
                ", batStyle='" + batStyle + '\'' +
                ", bowlType='" + bowlType + '\'' +
                ", rank=" + rank +
                ", level='" + level + '\'' +
                ", matchesWon=" + matchesWon +
                ", matchesPlayed=" + matchesPlayed +
                ", matchesLose=" + matchesLose +
                ", matchesTie=" + matchesTie +
                ", matchesNR=" + matchesNR +
                ", registeredYear='" + registeredYear + '\'' +
                ", venue='" + venue + '\'' +
                ", tourWon=" + tourWon +
                ", tourPlayed=" + tourPlayed +
                ", tourLose=" + tourLose +
                ", tourTie=" + tourTie +
                ", tourNR=" + tourNR +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", info='" + info + '\'' +
                ", matchScored=" + matchScored +
                ", tour1='" + tour1 + '\'' +
                ", tour2='" + tour2 + '\'' +
                ", experience='" + experience + '\'' +
                ", preference='" + preference + '\'' +
                ", education='" + education + '\'' +
                ", courses='" + courses + '\'' +
                ", bowlStyle='" + bowlStyle + '\'' +
                ", cric_bg='" + cric_bg + '\'' +
                ", squadNumber=" + squadNumber +
                ", coachNumber=" + coachNumber +
                ", pitchNumber=" + pitchNumber +
                ", runs=" + runs +
                ", batAvg=" + batAvg +
                ", batSR=" + batSR +
                ", batHS=" + batHS +
                ", debut='" + debut + '\'' +
                ", recent='" + recent + '\'' +
                ", bowlAvg=" + bowlAvg +
                ", bowlSR=" + bowlSR +
                ", econ=" + econ +
                ", bb='" + bb + '\'' +
                ", wkCatches=" + wkCatches +
                ", wkStumps=" + wkStumps +
                ", wkRunOut=" + wkRunOut +
                ", fCatches=" + fCatches +
                ", fRunOut=" + fRunOut +
                ", momAwards=" + momAwards +
                ", mosAwards=" + mosAwards +
                ", rating=" + rating +
                ", homeGround='" + homeGround + '\'' +
                ", playerRole='" + playerRole + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}
