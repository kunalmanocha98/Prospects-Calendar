package com.pratiksymz.android.prospectscalendarnew.OkHttpClient;

import com.pratiksymz.android.prospectscalendarnew.model.HomeApi;
import com.pratiksymz.android.prospectscalendarnew.model.DaySection;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("home.php")
    Call<HomeApi> homeResults();

    @GET("day.php")
    Call<DaySection> dayResults();
}
