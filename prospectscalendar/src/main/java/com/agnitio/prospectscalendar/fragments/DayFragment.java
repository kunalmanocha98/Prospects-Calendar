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
import com.agnitio.prospectscalendar.adapters.DaySectionAdapter;
import com.agnitio.prospectscalendar.interfaces.Constants;
import com.agnitio.prospectscalendar.models.DayApi;
import com.agnitio.prospectscalendar.retrofit.ApiService;
import com.agnitio.prospectscalendar.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ApiService apiService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;
    String themeColor="E85B36",themeColordark="#020001";



    public DayFragment() {
        // Required empty public constructor
    }
    public static DayFragment newInstance(Bundle bundle) {
        DayFragment dayFragment=new DayFragment();
        dayFragment.setArguments(bundle);
        return dayFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_day, container, false);
        swipeRefreshLayout=rootView.findViewById(R.id.swiperefreshlayout_day_section);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Get the Day Api Data
        getdata();


        return rootView;
    }

    private void getdata() {
        if (getArguments() != null) {
            if (getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(Constants.PageParams.THEME_TYPE_APP)) {
                themeColor = getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
                themeColordark = getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
            }

        }
        swipeRefreshLayout.setRefreshing(true);
        apiService = ApiUtils.getApiService();
        apiService.dayResults().enqueue(new Callback<DayApi>() {
            @Override
            public void onResponse(Call<DayApi> call, Response<DayApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusMessage().equals("success")) {

                        // Call the DaySection Adapterhttp://bydegreestest.agnitioworld.com/calendar_management/day.php
                        DaySectionAdapter adapter = new DaySectionAdapter(getActivity(), response.body().getDayResults(),themeColor);
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
            public void onFailure(Call<DayApi> call, Throwable t) {
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
