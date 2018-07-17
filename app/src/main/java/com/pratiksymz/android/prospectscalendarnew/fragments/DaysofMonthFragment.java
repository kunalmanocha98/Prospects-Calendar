package com.pratiksymz.android.prospectscalendarnew.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.models.MonthWiseApi;
import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiService;
import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaysofMonthFragment extends Fragment {

    ApiService apiService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_dayofmonth,container,false);
        apiService= ApiUtils.getApiService();
        apiService.monthwisedataresults().enqueue(new Callback<MonthWiseApi>() {
            @Override
            public void onResponse(Call<MonthWiseApi> call, Response<MonthWiseApi> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("success")){

                        Log.e("response","successfull");
                    }
                }else{
                    Log.e("response","unsuccessfull");
                }
            }
            @Override
            public void onFailure(Call<MonthWiseApi> call, Throwable t) {
                Log.e("response","failed due to -"+t.getLocalizedMessage());
            }
        });
        return v;
    }
}
