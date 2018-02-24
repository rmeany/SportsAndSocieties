package com.example.sportsandsocieties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SocietyEventActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private String date;
    private String location;
    private String time;
    private String description;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String societyEvent;
    private String societyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_event);

        //get the society that was clicked in the previous activity from list of societies
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                societyEvent = null;
                societyName = null;
            } else {
                societyEvent = extras.getString("societyEvent");
                societyName = extras.getString("societyName");
            }
        } else {
            societyEvent = (String) savedInstanceState.getSerializable("societyEvent");
            societyName = (String) savedInstanceState.getSerializable("societyName");
        }

        getSupportActionBar().setTitle(societyName);

        //display the event details
        dbRef = fbdb.getReference().child("Societies").child(societyName).child("events").child(societyEvent);
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
