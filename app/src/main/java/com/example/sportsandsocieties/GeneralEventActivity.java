package com.example.sportsandsocieties;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GeneralEventActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private String date;
    private String location;
    private String time;
    private String description;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String generalEvent;
    private String generalName;
    private EditText input;
    private String editedText;
    private String regexp;
    private AlertDialog.Builder errorAlert;
    private AlertDialog.Builder alert;
    private AlertDialog.Builder goingAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_event);

        findViewById(R.id.goingButton).setOnClickListener(new GeneralEventActivity.HandleClick());

        //get the event that was clicked in the previous activity from list of events
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                generalEvent = null;
            } else {
                generalEvent = extras.getString("generalEvent");
            }
        } else {
            generalEvent = (String) savedInstanceState.getSerializable("generalEvent");
        }

        //display the event details
        dbRef = fbdb.getReference().child("General").child("general").child("events").child(generalEvent);
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

    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) { showAlerts(); }
    }
    private void showAlerts(){

        alert = new AlertDialog.Builder(this);
        alert.setTitle(generalEvent);
        alert.setMessage(R.string.enterStudentNum);

        goingAlert = new AlertDialog.Builder(this);
        goingAlert.setTitle(R.string.seeYouThereEvent);

        errorAlert = new AlertDialog.Builder(this);
        errorAlert.setTitle(R.string.errorTitleEvent);
        errorAlert.setMessage(R.string.errorMessageEvent);

// Set an EditText view to get user input
        input = new EditText(this);
        input.setHint(R.string.studentNumHint);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                editedText = input.getText().toString();
                dbRef = fbdb.getReference().child("General").child("general").child("events").child(generalEvent).child("attendance").child(editedText);
                regexp = "[R]\\d{8}";
                if (editedText.length() == 9 && editedText.matches(regexp)) {
                    dbRef = fbdb.getReference().child("General").child("general").child("events").child(generalEvent).child("attendance").child(editedText);
                    dbRef.setValue("y");
                    dialog.dismiss();
                    goingAlert.setMessage(editedText + " is going to " + generalEvent);
                    goingAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }});
                    goingAlert.show();
                }
                else {
                    errorAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }});
                    errorAlert.show();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
