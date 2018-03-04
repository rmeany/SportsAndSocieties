package com.example.sportsandsocieties;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SocietiesActivity extends AppCompatActivity {

    private static MainActivity m;
    private DatabaseReference dbRef;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String email;
    private String societyName;
    private String societyEvent;
    private String date;
    private String description;
    private ArrayList<String> SocietyEventListDate = new ArrayList<String >();
    private ArrayList<String> SocietyEventListName = new ArrayList<String >();
    private ArrayList<String> SocietyEventName = new ArrayList<String >();
    private ArrayList<String> SocietyEventDate = new ArrayList<String>();
    private AlertDialog.Builder errorAlert;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_society);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    //get the society that was clicked in the previous activity from list of societies
    if (savedInstanceState == null) {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            societyName = null;
        } else {
            societyName = extras.getString("societyName");
        }
    } else {
        societyName = (String) savedInstanceState.getSerializable("societyName");
    }

    getSupportActionBar().setTitle(societyName);

    //display the email address for every society
    dbRef = fbdb.getReference().child("Societies").child(societyName).child("email").child("address");
    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            email = dataSnapshot.getValue(String.class);
            TextView emailText = findViewById(R.id.contactEmail);
            emailText.setText(email);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };
    dbRef.addListenerForSingleValueEvent(eventListener);

        dbRef = fbdb.getReference().child("Societies").child(societyName).child("events");
        ValueEventListener eventEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event e = ds.getValue(Event.class);
                    date = e.date;
                    SocietyEventListDate.add(date);
                    description = e.description;
                    SocietyEventListName.add(description);
                }

                if (description.equals("N/A")) {
                    errorAlert = new AlertDialog.Builder(SocietiesActivity.this);
                    errorAlert.setTitle("No Event Information");
                    errorAlert.setMessage("No events for " + societyName + " available.");
                    errorAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    errorAlert.show();
                }
                else {
                    ListView listView = findViewById(R.id.eventLV);

                    HashMap<String, String> nameDate = new HashMap<>();
                    for (int i = 0; i < SocietyEventListName.size(); i++) {
                        nameDate.put(SocietyEventListName.get(i), SocietyEventListDate.get(i));
                    }
                    List<HashMap<String, String>> listItems = new ArrayList<>();
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listItems, R.layout.list_item,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.Name, R.id.Date});
                    Iterator it = nameDate.entrySet().iterator();
                    while (it.hasNext()) {
                        HashMap<String, String> resultsMap = new HashMap<>();
                        Map.Entry pair = (Map.Entry) it.next();
                        resultsMap.put("First Line", pair.getKey().toString());
                        SocietyEventName.add(pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        SocietyEventDate.add(pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(getApplicationContext(), SocietyEventActivity.class);
                            societyEvent = SocietyEventName.get(position) + " " + SocietyEventDate.get(position);
                            intent.putExtra("societyName", societyName);
                            intent.putExtra("societyEvent", societyEvent);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addListenerForSingleValueEvent(eventEventListener);
}
}
