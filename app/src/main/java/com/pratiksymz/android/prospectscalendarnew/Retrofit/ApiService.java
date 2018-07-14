package com.pratiksymz.android.prospectscalendarnew.Retrofit;

import com.pratiksymz.android.prospectscalendarnew.model.DayApi;
import com.pratiksymz.android.prospectscalendarnew.model.HomeApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("home.php")
    Call<HomeApi> homeResults();

    @GET("day.php")
    Call<DayApi> dayResults();
}
