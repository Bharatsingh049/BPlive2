package com.Newsforest.android.Newsforest;

import java.io.Serializable;

/**
 * Created by Bharat on 4/8/2017.
 */
public class Matches implements Serializable {
    private int unique_id;
    private String team2;
    private String team1;
    private String matchStarted;
    private String date;
    private String toss_Winner;
    private String type;
    private String time;
    private String Winner;

    public Matches() {
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public int getUnique_id() {
        return unique_id;
    }

    public String getToss_Winner() {
        return toss_Winner;
    }

    public void setToss_Winner(String toss_Winner) {
        this.toss_Winner = toss_Winner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Matches(int unique_id, String team2 , String team1, String matchStarted, String date) {
        super();
        this.unique_id = unique_id;
        this.team2 = team2;
        this.team1 = team1;
        this.matchStarted = matchStarted;
        this.date=date;
    }



    public void setUnique_id(int unique_id) {
        this.unique_id= unique_id;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1 ;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMatchStarted(){ return matchStarted;}

    public void setMatchStarted(String matchStarted){ this.matchStarted = matchStarted;}
}
