package com.agnitio.prospectscalendar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.adapters.CalModDaySectionAdapter;
import com.agnitio.prospectscalendar.interfaces.CalModConstants;
import com.agnitio.prospectscalendar.models.CalModDayApi;
import com.agnitio.prospectscalendar.retrofit.CalModApiService;
import com.agnitio.prospectscalendar.retrofit.CalModApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalModDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private CalModApiService calModApiService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;
    String themeColor="E85B36",themeColordark="#020001";



    public CalModDayFragment() {
        // Required empty public constructor
    }
    public static CalModDayFragment newInstance(Bundle bundle) {
        CalModDayFragment calModDayFragment =new CalModDayFragment();
        calModDayFragment.setArguments(bundle);
        return calModDayFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.cal_mod_fragment_day, container, false);
        swipeRefreshLayout=rootView.findViewById(R.id.swiperefreshlayout_day_section);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Get the Day Api Data
        getdata();


        return rootView;
    }

    private void getdata() {
        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)) {
                themeColor = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
                themeColordark = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
            }

        }
        swipeRefreshLayout.setRefreshing(true);
        calModApiService = CalModApiUtils.getApiService();
        calModApiService.dayResults().enqueue(new Callback<CalModDayApi>() {
            @Override
            public void onResponse(Call<CalModDayApi> call, Response<CalModDayApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusMessage().equals("success")) {

                        // Call the CalModDaySection Adapterhttp://bydegreestest.agnitioworld.com/calendar_management/day.php
                        CalModDaySectionAdapter adapter = new CalModDaySectionAdapter(getActivity(), response.body().getCalModDayResults(),themeColor);
                        RecyclerView recyclerView = rootView.findViewById(R.id.day_section_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    Log.d("No Result", "no result");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CalModDayApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getdata();
    }
}
