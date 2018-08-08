package com.agnitio.prospectscalendar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.adapters.CalModDaySectionAdapter;
import com.agnitio.prospectscalendar.models.CalModDayApi;
import com.agnitio.prospectscalendar.retrofit.CalModApiService;
import com.agnitio.prospectscalendar.retrofit.CalModApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalModDaysofMonthFragment extends Fragment {

    CalModApiService calModApiService;
    TextView txt_monthname;
    String month,themeColor="#212121";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cal_mod_fragment_dayofmonth, container, false);
        txt_monthname = v.findViewById(R.id.txt_month_name);
        calModApiService = CalModApiUtils.getApiService();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            month = bundle.getString("month");
        }

        themeColor=(String) getActivity().findViewById(R.id.main_container).getTag();
        calModApiService.monthwisedataresults(month).enqueue(new Callback<CalModDayApi>() {
            @Override
            public void onResponse(Call<CalModDayApi> call, Response<CalModDayApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusMessage().equals("success")) {
                        Log.e("response", "successfull");
                        txt_monthname.setText(month);
                        CalModDaySectionAdapter adapter = new CalModDaySectionAdapter(getActivity(), response.body().getCalModDayResults(),themeColor);
                        RecyclerView recyclerView = v.findViewById(R.id.rv_daysofmonthrecycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                    }

                } else {
                    Log.e("response", "unsuccessfull");
                }

            }

            @Override
            public void onFailure(Call<CalModDayApi> call, Throwable t) {
                Log.e("response","failed due to -"+t.getLocalizedMessage());
            }
        });
        return v;
    }
}
