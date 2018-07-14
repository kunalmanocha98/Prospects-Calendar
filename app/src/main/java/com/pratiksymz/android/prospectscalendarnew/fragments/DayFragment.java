package com.pratiksymz.android.prospectscalendarnew.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiService;
import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiUtils;
import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.adapters.DaySectionAdapter;
import com.pratiksymz.android.prospectscalendarnew.model.DayApi;
import com.pratiksymz.android.prospectscalendarnew.model.DayResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {
    private ApiService apiService;


    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        // Get the Day Api Data
        apiService = ApiUtils.getApiService();
        apiService.dayResults().enqueue(new Callback<DayApi>() {
            @Override
            public void onResponse(Call<DayApi> call, Response<DayApi> response) {
                DayApi dayApi = response.body();
                if (dayApi.getStatusCode() == 101 && dayApi.getStatusMessage().equals("success")) {
                    List<DayResult> dayResults = dayApi.getDayResults();

                    // Call the DaySection Adapter
                    DaySectionAdapter adapter = new DaySectionAdapter(getActivity(), dayResults);
                    RecyclerView recyclerView = rootView.findViewById(R.id.day_section_recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("No Result",
                            dayApi.getStatusCode() + " " + dayApi.getStatusMessage()
                    );
                }
            }

            @Override
            public void onFailure(Call<DayApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
            }
        });

        return rootView;
    }

}
