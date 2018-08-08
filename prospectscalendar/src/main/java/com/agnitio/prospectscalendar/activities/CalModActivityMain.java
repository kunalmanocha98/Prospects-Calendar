package com.agnitio.prospectscalendar.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.fragments.CalModCalendarFragment;
import com.agnitio.prospectscalendar.fragments.CalModDayFragment;
import com.agnitio.prospectscalendar.fragments.CalModHomeFragment;
import com.agnitio.prospectscalendar.fragments.CalModMonthFragment;
import com.agnitio.prospectscalendar.interfaces.CalModConstants;


public class CalModActivityMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView mNavigationView;

    private String FRAGMENT_SELECTED_CLASS;
    String themecolorprimary, themecolorprimarydark, themecoloraccent, themeType, clientid, logintype, userid, usertype;

    FloatingActionButton fab_event;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_activity_main);
        ConstraintLayout constraintLayout=findViewById(R.id.main_container);

        fab_event = findViewById(R.id.fab_add_event);
        fab_event.setOnClickListener(this);
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        FRAGMENT_SELECTED_CLASS = CalModHomeFragment.class.getName();

        bundle = getIntent().getExtras();

        if (bundle != null) {

            clientid = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_CLIENT_ID);
            logintype = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_LOGIN_TYPE);
            usertype = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_USER_TYPE);
            userid = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_USER_ID);
            themecolorprimary = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
            themeType = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE);
            themecolorprimarydark = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
            themecoloraccent = bundle.getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_ACCENT);
            getWindow().setStatusBarColor(Color.parseColor(themecolorprimarydark));

            int color = Color.parseColor(themecolorprimary);
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            mNavigationView.setItemIconTintList(colorStateList);
            mNavigationView.setItemTextColor(colorStateList);
//            fab_event.setBackgroundColor(color);
            fab_event.setBackgroundTintList(colorStateList);
            loadFragment(CalModHomeFragment.newInstance(bundle));

        } else {
            getWindow().setStatusBarColor(Color.parseColor("#020001"));
            bundle = new Bundle();
            bundle.putString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY, "#E85B36");
            bundle.putString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK, "#020001");
            bundle.putString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE, CalModConstants.PageParams.THEME_TYPE_APP);
            int color = Color.parseColor("#E85B36");

            constraintLayout.setTag("#E85B36");

            ColorStateList colorStateList = ColorStateList.valueOf(color);
            mNavigationView.setItemIconTintList(colorStateList);
            mNavigationView.setItemTextColor(colorStateList);
//            fab_event.setBackgroundColor(color);
            fab_event.setBackgroundTintList(colorStateList);
            loadFragment(CalModHomeFragment.newInstance(bundle));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getTitle().toString()) {
            case CalModConstants.MenuConstants.MENU_TITLE_HOME:
                FRAGMENT_SELECTED_CLASS = CalModHomeFragment.class.getName();
//                mSelectedFragment = new CalModHomeFragment();
                loadFragment(CalModHomeFragment.newInstance(bundle));
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_DAY:
                FRAGMENT_SELECTED_CLASS = CalModDayFragment.class.getName();
//                mSelectedFragment = new CalModDayFragment();
                loadFragment(CalModDayFragment.newInstance(bundle));
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_MONTH:
                FRAGMENT_SELECTED_CLASS = CalModMonthFragment.class.getName();
//                mSelectedFragment = new CalModMonthFragment();
                loadFragment(CalModMonthFragment.newInstance(bundle));
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_CALENDAR:
                FRAGMENT_SELECTED_CLASS = CalModCalendarFragment.class.getName();
//                mSelectedFragment = new CalModCalendarFragment();
                loadFragment(CalModCalendarFragment.newInstance(bundle));
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        if (FRAGMENT_SELECTED_CLASS.equals(CalModHomeFragment.class.getName())) {
            finish();
        } else {
            super.onBackPressed();
        }

//        if (!(mSelectedFragment.equals(new CalModHomeFragment()))) {
//            loadFragment(mSelectedFragment);
//        }
    }

    private void loadFragment(Fragment fragment) {
        if (fragment instanceof CalModHomeFragment) {
            fab_event.hide();
        } else {
            fab_event.show();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
       if (view.getId()==R.id.fab_add_event){
           Intent i = new Intent(this, CalModActivityAddEvent.class);
           startActivity(i);
       }
    }
}
