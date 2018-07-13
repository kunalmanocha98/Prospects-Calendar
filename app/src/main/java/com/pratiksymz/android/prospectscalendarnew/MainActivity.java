package com.pratiksymz.android.prospectscalendarnew;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mNavigationView;

    private Fragment mSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_home:
                mSelectedFragment = new HomeFragment();
                loadFragment(mSelectedFragment);
                return true;

            case R.id.item_day:
                mSelectedFragment = new DayFragment();
                loadFragment(mSelectedFragment);
                return true;

            case R.id.item_month:
                mSelectedFragment = new MonthFragment();
                loadFragment(mSelectedFragment);
                return true;

            case R.id.item_calendar:
                mSelectedFragment = new CalendarFragment();
                loadFragment(mSelectedFragment);
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!(mSelectedFragment.equals(new HomeFragment()))) {
            loadFragment(mSelectedFragment);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
