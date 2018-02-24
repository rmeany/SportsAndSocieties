package com.example.sportsandsocieties;

/**
 * Created by abcd on 17/02/2018.
 */

public class Event {
    String date;
    String description;

    public Event(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public Event(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
