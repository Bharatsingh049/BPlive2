package com.Newsforest.android.Newsforest;

/**
 * Created by Bharat on 4/8/2017.
 */
public class Matches {
    public int unique_id;
    public String team2;
    public String team1;
    public String matchStarted;
    public String date;

    public Matches() {
    }

    public Matches(int unique_id, String team2 , String team1, String matchStarted, String date) {
        super();
        this.unique_id = unique_id;
        this.team2 = team2;
        this.team1 = team1;
        this.matchStarted = matchStarted;
        this.date=date;
    }

    public int getunique_id() {
        return unique_id;
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
