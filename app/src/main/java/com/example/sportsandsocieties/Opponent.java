package com.example.sportsandsocieties;

/**
 * Created by abcd on 21/02/2018.
 */

public class Opponent {

    String date;
    String opposition;
    String team;
    String score;

    public Opponent(String date, String opposition, String team, String score) {
        this.date = date;
        this.opposition = opposition;
        this.team = team;
        this.score = score;
    }

    public Opponent(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getOpposition() { return opposition; }

    public void setOpposition(String opposition) {
        this.opposition = opposition;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
