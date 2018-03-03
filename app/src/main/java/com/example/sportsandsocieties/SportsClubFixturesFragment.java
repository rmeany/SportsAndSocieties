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

public class SportsClubFixturesFragment extends Fragment {

    private DatabaseReference dbRef;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    private String sportsClub;
    private String sportsFixture;
    private String date;
    private String opposition;
    private String team;
    private ArrayList<String> SportFixtureListDate = new ArrayList<String >();
    private ArrayList<String> SportFixtureOpponentListName = new ArrayList<String >();
    private ArrayList<String> SportFixtureListCitTeam = new ArrayList<String >();
    private ArrayList<String> SportFixtureName = new ArrayList<String >();
    private ArrayList<String> SportFixtureDate = new ArrayList<String >();
    View view;
    private AlertDialog.Builder errorAlert;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sports_club_fixtures,container,false);

        SportsClubActivity sca = (SportsClubActivity) getActivity();
        sportsClub = sca.getSportsClubName();

        dbRef = fbdb.getReference().child("Sports Clubs").child(sportsClub).child("fixtures");
        ValueEventListener eventEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Opponent o = ds.getValue(Opponent.class);
                    date = o.date;
                    SportFixtureListDate.add(date);
                    opposition = o.opposition;
                    SportFixtureOpponentListName.add(opposition);
                    team = o.team;
                    SportFixtureListCitTeam.add(team);
                }

                if (opposition.equals("N/A")) {
                    errorAlert = new AlertDialog.Builder(getActivity());
                    errorAlert.setTitle("No Fixture Information");
                    errorAlert.setMessage("No fixtures for " + sportsClub + " available.");
                    errorAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    errorAlert.show();
                }
                else {
                    ListView listView = view.findViewById(R.id.fixtureLV);

                    HashMap<String, String> nameDate = new HashMap<>();
                    for (int i = 0; i < SportFixtureOpponentListName.size(); i++) {
                        nameDate.put(SportFixtureOpponentListName.get(i), SportFixtureListDate.get(i));
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
                        SportFixtureName.add(pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        SportFixtureDate.add(pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(getActivity(), SportFixtureActivity.class);
                            sportsFixture = SportFixtureName.get(position) + " " + SportFixtureDate.get(position);
                            intent.putExtra("sportsClub", sportsClub);
                            intent.putExtra("sportsFixture", sportsFixture);
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
