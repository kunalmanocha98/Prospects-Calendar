package com.pratiksymz.android.prospectscalendarnew.retrofit;

import com.pratiksymz.android.prospectscalendarnew.models.DayApi;
import com.pratiksymz.android.prospectscalendarnew.models.HomeApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("home.php")
    Call<HomeApi> homeResults();

    @GET("day.php")
    Call<DayApi> dayResults();
}
