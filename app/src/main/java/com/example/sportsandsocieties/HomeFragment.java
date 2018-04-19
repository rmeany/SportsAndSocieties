package com.example.sportsandsocieties;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by abcd on 29/01/2018.
 */

public class HomeFragment extends Fragment {

    private DatabaseReference dbRef;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String generalEvent;
    private String date;
    private String description;
    private ArrayList<String> GeneralEventListDate = new ArrayList<String >();
    private ArrayList<String> GeneralEventListName = new ArrayList<String >();
    private ArrayList<String> GeneralEventName = new ArrayList<String >();
    private ArrayList<String> GeneralEventDate = new ArrayList<String>();

    public HomeFragment() {}

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);


        dbRef = fbdb.getReference().child("General").child("general").child("events");
        dbRef.keepSynced(true);
        ValueEventListener eventEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event e = ds.getValue(Event.class);
                    date = e.date;
                    GeneralEventListDate.add(date);
                    description = e.description;
                    GeneralEventListName.add(description);
                }

                    ListView listView = myView.findViewById(R.id.generalLV);

                    HashMap<String, String> nameDate = new HashMap<>();
                    for (int i = 0; i < GeneralEventListName.size(); i++) {
                        nameDate.put(GeneralEventListName.get(i), GeneralEventListDate.get(i));
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
                        GeneralEventName.add(pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        GeneralEventDate.add(pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(getActivity(), GeneralEventActivity.class);
                            generalEvent = GeneralEventName.get(position) + " " + GeneralEventDate.get(position);
                            intent.putExtra("generalEvent", generalEvent);
                            startActivity(intent);
                        }
                    });
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addListenerForSingleValueEvent(eventEventListener);


        return myView;
    }




}
