package com.agnitio.calendar.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.agnitio.calendar.R;
import com.agnitio.calendar.fragments.CalModCalendarFragment;
import com.agnitio.calendar.fragments.CalModDayFragment;
import com.agnitio.calendar.fragments.CalModHomeFragment;
import com.agnitio.calendar.fragments.CalModMonthFragment;
import com.agnitio.calendar.interfaces.CalModConstants;

import java.util.List;


public class CalModActivityMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView mNavigationView;

    private String FRAGMENT_SELECTED_CLASS;
    String themecolorprimary, themecolorprimarydark, themecoloraccent, themeType, clientid, logintype, userid, usertype;

    FloatingActionButton fab_event;
    private Bundle bundle;
    private String currentFragment;

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
            loadFragment(CalModHomeFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_HOME);

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
            loadFragment(CalModHomeFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_HOME);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                List<Fragment> f = getSupportFragmentManager().getFragments();
                //List<Fragment> f only returns one value
                if (f.size()>0) {
                    Fragment frag = f.get(0);
                    currentFragment = frag.getClass().getSimpleName();
                    changebottomnavigationselection(currentFragment);
                }else if (f.size()==0){
                    finish();
                }
            }
        });
    }
    public void changebottomnavigationselection(String fragment_name){
        switch (fragment_name){
            case CalModConstants.FragmentConstants.FRAGMENT_NAME_HOME:{
                fab_event.hide();
                mNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            }
            case CalModConstants.FragmentConstants.FRAGMENT_NAME_DAY:{
                fab_event.show();
                mNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            }
            case CalModConstants.FragmentConstants.FRAGMENT_NAME_MONTH:{
                fab_event.show();
                mNavigationView.getMenu().getItem(2).setChecked(true);
                break;
            }
            case CalModConstants.FragmentConstants.FRAGMENT_NAME_CALENDAR:{
                fab_event.show();
                mNavigationView.getMenu().getItem(3).setChecked(true);
                break;
            }
            default:{
                break;
            }
        }
//        mNavigationView.setSelectedItemId(R.id.item_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getTitle().toString()) {
            case CalModConstants.MenuConstants.MENU_TITLE_HOME:
                FRAGMENT_SELECTED_CLASS = CalModHomeFragment.class.getName();
//                mSelectedFragment = new CalModHomeFragment();
                loadFragment(CalModHomeFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_HOME);
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_DAY:
                FRAGMENT_SELECTED_CLASS = CalModDayFragment.class.getName();
//                mSelectedFragment = new CalModDayFragment();
                loadFragment(CalModDayFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_DAY);
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_MONTH:
                FRAGMENT_SELECTED_CLASS = CalModMonthFragment.class.getName();
//                mSelectedFragment = new CalModMonthFragment();
                loadFragment(CalModMonthFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_MONTH);
                return true;

            case CalModConstants.MenuConstants.MENU_TITLE_CALENDAR:
                FRAGMENT_SELECTED_CLASS = CalModCalendarFragment.class.getName();
//                mSelectedFragment = new CalModCalendarFragment();
                loadFragment(CalModCalendarFragment.newInstance(bundle), CalModConstants.FragmentConstants.FRAGMENT_TAG_CALENDAR);
                return true;
        }

        return false;
    }

    
    private void loadFragment(Fragment fragment, String FRAGMENT_TAG) {
        if (fragment instanceof CalModHomeFragment) {
            fab_event.hide();
        } else {
            fab_event.show();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
       if (view.getId()==R.id.fab_add_event){
           Intent i = new Intent(this, CalModActivityAddEvent.class);
           startActivity(i);
       }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
