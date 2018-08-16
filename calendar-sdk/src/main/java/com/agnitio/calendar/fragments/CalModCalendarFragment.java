package com.agnitio.calendar.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnitio.calendar.R;
import com.agnitio.calendar.calendar.CalModMyCalendarView;
import com.agnitio.calendar.interfaces.CalModConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalModCalendarFragment extends Fragment {


    private String themeColor;

    public CalModCalendarFragment() {
        // Required empty public constructor
    }
    public static CalModCalendarFragment newInstance(Bundle bundle) {
    CalModCalendarFragment calModCalendarFragment =new CalModCalendarFragment();
    calModCalendarFragment.setArguments(bundle);
    return calModCalendarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cal_mod_fragment_calendar, container, false);
        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)) {
                themeColor = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
            }

        }
        // Initialize the Custom Calendar View
        CalModMyCalendarView mView = rootView.findViewById(R.id.custom_calendar);
        mView.setmContext(getActivity(), Color.parseColor(themeColor));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resumed----->","calendar");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("paused----->","calendar");
    }
}
