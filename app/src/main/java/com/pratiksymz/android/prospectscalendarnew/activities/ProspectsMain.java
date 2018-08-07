package com.pratiksymz.android.prospectscalendarnew.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.agnitio.prospectscalendar.activities.ProspectsCalendarActivity;
import com.pratiksymz.android.prospectscalendarnew.R;

public class ProspectsMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_prospects_activity);
        Intent i = new Intent(ProspectsMain.this, ProspectsCalendarActivity.class);
        startActivity(i);
    }

}