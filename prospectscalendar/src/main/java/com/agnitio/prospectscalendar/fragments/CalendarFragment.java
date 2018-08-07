package com.agnitio.prospectscalendar.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.calendar.MyCalendarView;
import com.agnitio.prospectscalendar.interfaces.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    private String themeColor;

    public CalendarFragment() {
        // Required empty public constructor
    }
    public static CalendarFragment newInstance(Bundle bundle) {
    CalendarFragment calendarFragment=new CalendarFragment();
    calendarFragment.setArguments(bundle);
    return calendarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        if (getArguments() != null) {
            if (getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(Constants.PageParams.THEME_TYPE_APP)) {
                themeColor = getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
            }

        }
        // Initialize the Custom Calendar View
        MyCalendarView mView = rootView.findViewById(R.id.custom_calendar);
        mView.setmContext(getActivity(), Color.parseColor(themeColor));

        return rootView;
    }


}
