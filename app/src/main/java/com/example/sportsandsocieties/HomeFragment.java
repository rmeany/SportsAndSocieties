package com.example.sportsandsocieties;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by abcd on 29/01/2018.
 */

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        String [] array = {"Welcome"};

        ListView listView = myView.findViewById(R.id.homeLV);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                array
        );

        listView.setAdapter(listViewAdapter);

        return myView;
    }


}
