package com.example.sportsandsocieties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SportEventActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private String date;
    private String location;
    private String time;
    private String description;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String sportsEvent;
    private String sportsClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_event);

        //get the society that was clicked in the previous activity from list of societies
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                sportsEvent = null;
                sportsClub = null;
            } else {
                sportsEvent = extras.getString("sportsEvent");
                sportsClub = extras.getString("sportsClub");
            }
        } else {
            sportsEvent = (String) savedInstanceState.getSerializable("sportsEvent");
            sportsClub = (String) savedInstanceState.getSerializable("sportsClub");
        }

        getSupportActionBar().setTitle(sportsClub);

        //display the event details
        dbRef = fbdb.getReference().child("Sports Clubs").child(sportsClub).child("events").child(sportsEvent);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    date = dataSnapshot.child("date").getValue(String.class);
                    location = dataSnapshot.child("location").getValue(String.class);
                    time = dataSnapshot.child("time").getValue(String.class);
                    description = dataSnapshot.child("description").getValue(String.class);
                }
                TextView dateText = findViewById(R.id.whenContent);
                dateText.setText(date + " at " + time);
                TextView locationText = findViewById(R.id.whereContent);
                locationText.setText(location);
                TextView descriptionText = findViewById(R.id.descriptionContent);
                descriptionText.setText(description);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbRef.addListenerForSingleValueEvent(eventListener);
    }
}
