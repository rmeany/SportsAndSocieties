package com.example.sportsandsocieties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SportResultActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private String date;
    private String location;
    private String time;
    private String opposition;
    private String team;
    private String type;
    private String score;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String sportsResult;
    private String sportsClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_result);

        //get the society that was clicked in the previous activity from list of societies
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                sportsResult = null;
                sportsClub = null;
            } else {
                sportsResult = extras.getString("sportsResult");
                sportsClub = extras.getString("sportsClub");
            }
        } else {
            sportsResult = (String) savedInstanceState.getSerializable("sportsResult");
            sportsClub = (String) savedInstanceState.getSerializable("sportsClub");
        }

        getSupportActionBar().setTitle(sportsClub);

        //display the event details
        dbRef = fbdb.getReference().child("Sports Clubs").child(sportsClub).child("results").child(sportsResult);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    date = dataSnapshot.child("date").getValue(String.class);
                    location = dataSnapshot.child("location").getValue(String.class);
                    time = dataSnapshot.child("time").getValue(String.class);
                    opposition = dataSnapshot.child("opposition").getValue(String.class);
                    team = dataSnapshot.child("team").getValue(String.class);
                    type = dataSnapshot.child("type").getValue(String.class);
                    score = dataSnapshot.child("score").getValue(String.class);
                }
                TextView fixtureType = findViewById(R.id.fixtureType);
                fixtureType.setText(type);
                TextView citText = findViewById(R.id.citContent);
                citText.setText(team);
                TextView scoreText = findViewById(R.id.scoreContent);
                scoreText.setText(score);
                TextView opponentText = findViewById(R.id.opponentContent);
                opponentText.setText(opposition);
                TextView dateText = findViewById(R.id.whenContent);
                dateText.setText(date + " at " + time);
                TextView locationText = findViewById(R.id.locationContent);
                locationText.setText(location);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbRef.addListenerForSingleValueEvent(eventListener);
    }
}
