package com.example.sportsandsocieties;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setTitle("CIT Sports and Societies");
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame,
                                    new HomeFragment())
                            .commit();
                    return true;
                case R.id.navigation_sports:
                    getSupportActionBar().setTitle("Sports Clubs");
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame,
                                    new SportsFragment())
                            .commit();
                    return true;
                case R.id.navigation_societies:
                    getSupportActionBar().setTitle("Societies");
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame,
                                    new SocietiesFragment())
                            .commit();
                    return true;
                case R.id.navigation_admin:
                    getSupportActionBar().setTitle("Admin");
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame,
                                    new AdminFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment())
                    .commit();
        }

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
