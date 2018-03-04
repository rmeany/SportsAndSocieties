package com.example.sportsandsocieties;

/**
 * Created by abcd on 09/02/2018.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class SportsClubEventsFragment extends Fragment {

    private DatabaseReference dbRef;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String sportsClub;
    private String sportsEvent;
    private String date;
    private String description;
    private ArrayList<String> SportEventListDate = new ArrayList<String >();
    private ArrayList<String> SportEventListName = new ArrayList<String >();
    private ArrayList<String> SportEventName = new ArrayList<String >();
    private ArrayList<String> SportEventDate = new ArrayList<String >();
    View view;
    private AlertDialog.Builder errorAlert;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sports_club_events,container,false);

        SportsClubActivity sca = (SportsClubActivity) getActivity();
        sportsClub = sca.getSportsClubName();

        dbRef = fbdb.getReference().child("Sports Clubs").child(sportsClub).child("events");
        ValueEventListener eventEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event e = ds.getValue(Event.class);
                    date = e.date;
                    SportEventListDate.add(date);
                    description = e.description;
                    SportEventListName.add(description);
                }

                if (description.equals("N/A")) {
                    errorAlert = new AlertDialog.Builder(getActivity());
                    errorAlert.setTitle("No Event Information");
                    errorAlert.setMessage("No events for " + sportsClub + " available.");
                    errorAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    errorAlert.show();
                }
                else {
                    ListView listView = view.findViewById(R.id.eventLV);

                    HashMap<String, String> nameDate = new HashMap<>();
                    for (int i = 0; i < SportEventListName.size(); i++) {
                        nameDate.put(SportEventListName.get(i), SportEventListDate.get(i));
                    }
                    List<HashMap<String, String>> listItems = new ArrayList<>();
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems, R.layout.list_item,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.Name, R.id.Date});
                    Iterator it = nameDate.entrySet().iterator();
                    while (it.hasNext()) {
                        HashMap<String, String> resultsMap = new HashMap<>();
                        Map.Entry pair = (Map.Entry) it.next();
                        resultsMap.put("First Line", pair.getKey().toString());
                        SportEventName.add(pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        SportEventDate.add(pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(getActivity(), SportEventActivity.class);
                            sportsEvent = SportEventName.get(position) + " " + SportEventDate.get(position);
                            intent.putExtra("sportsClub", sportsClub);
                            intent.putExtra("sportsEvent", sportsEvent);
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        dbRef.addListenerForSingleValueEvent(eventEventListener);

        return view;
    }
}
