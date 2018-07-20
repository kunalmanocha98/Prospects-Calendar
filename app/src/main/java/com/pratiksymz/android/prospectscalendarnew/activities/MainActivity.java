package com.pratiksymz.android.prospectscalendarnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.fragments.CalendarFragment;
import com.pratiksymz.android.prospectscalendarnew.fragments.DayFragment;
import com.pratiksymz.android.prospectscalendarnew.fragments.HomeFragment;
import com.pratiksymz.android.prospectscalendarnew.fragments.MonthFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private BottomNavigationView mNavigationView;

    private Fragment mSelectedFragment;
    FloatingActionButton fab_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_event=findViewById(R.id.fab_add_event);
        fab_event.setOnClickListener(this);
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
//        if (!(mSelectedFragment.equals(new HomeFragment()))) {
//            loadFragment(mSelectedFragment);
//        }
    }

    private void loadFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment){
            fab_event.hide();
        }else{
            fab_event.show();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_event:{
                Intent i=new Intent(this,EditorActivity.class);
                startActivity(i);
                break;
            }
            default:{

            }
        }
    }
}
