package com.example.sportsandsocieties;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class SportsClubActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private String sportsClubName;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_club);

        //get the sports club that was clicked in the previous activity from list of sports clubs
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                sportsClubName = null;
            } else {
                sportsClubName = extras.getString("sportsClubName");
            }
        } else {
            sportsClubName = (String) savedInstanceState.getSerializable("sportsClubName");
        }

        getSupportActionBar().setTitle(sportsClubName);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SportsClubFixturesFragment(), "Fixtures");
        adapter.addFragment(new SportsClubResultsFragment(), "Results");
        adapter.addFragment(new SportsClubEventsFragment(), "Events");
        viewPager.setAdapter(adapter);
    }

    public String getSportsClubName(){
        return sportsClubName;
    }
}
