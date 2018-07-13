package com.pratiksymz.android.prospectscalendarnew;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pratiksymz.android.prospectscalendarnew.adapters.DaySectionAdapter;
import com.pratiksymz.android.prospectscalendarnew.model.DaySection;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {


    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        // Create the ArrayList for the day
        List<DaySection> day = new ArrayList<>();
        day.add(new DaySection("13th Jul, 18", 560));

        DaySectionAdapter adapter = new DaySectionAdapter(getActivity(), day);
        RecyclerView recyclerView = rootView.findViewById(R.id.day_section_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
