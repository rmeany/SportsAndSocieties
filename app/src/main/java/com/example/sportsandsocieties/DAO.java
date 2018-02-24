package com.example.sportsandsocieties;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by abcd on 14/02/2018.
 */

class DAO {
    private static final DAO ourInstance = new DAO();
    private DatabaseReference dbRef;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    ArrayList<String> SportsList = new ArrayList<String>();
    ArrayList<String> SocietiesList = new ArrayList<String>();

    static DAO getInstance() {
        return ourInstance;
    }

    private DAO() {
        getSportsClubs();
        getSocieties();
    }

    private void getSportsClubs() {
        dbRef = fbdb.getReference().child("Sports Clubs");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    SportsList.add(name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbRef.addListenerForSingleValueEvent(eventListener);
    }

    public ArrayList<String> getSportsList() {return SportsList;}

    private void getSocieties() {
        dbRef = fbdb.getReference().child("Societies");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    SocietiesList.add(name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbRef.addListenerForSingleValueEvent(eventListener);
    }

    public ArrayList<String> getSocietiesList() {return SocietiesList;}
}
