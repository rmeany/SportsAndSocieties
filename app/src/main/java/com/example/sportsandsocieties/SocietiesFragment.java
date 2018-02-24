package com.example.sportsandsocieties;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by abcd on 29/01/2018.
 */

public class SocietiesFragment extends Fragment {

    private DAO dao = DAO.getInstance();
    private String societyName;

    public SocietiesFragment() {}

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_societies, container, false);

        dao.getSocietiesList();

        ListView listView = myView.findViewById(R.id.societiesLV);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                dao.getSocietiesList()
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), SocietiesActivity.class);
                societyName = dao.getSocietiesList().get(position);
                intent.putExtra("societyName", societyName);
                startActivity(intent);
            }
        });

        return myView;
    }


}
