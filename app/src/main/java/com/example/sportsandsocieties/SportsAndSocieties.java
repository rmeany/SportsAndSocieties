package com.example.sportsandsocieties;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by abcd on 19/04/2018.
 */

public class SportsAndSocieties extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
