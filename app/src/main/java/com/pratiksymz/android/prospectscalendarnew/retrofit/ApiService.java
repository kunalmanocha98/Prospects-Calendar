package com.pratiksymz.android.prospectscalendarnew.retrofit;

import com.pratiksymz.android.prospectscalendarnew.models.DayApi;
import com.pratiksymz.android.prospectscalendarnew.models.HomeApi;
import com.pratiksymz.android.prospectscalendarnew.models.MonthApi;
import com.pratiksymz.android.prospectscalendarnew.models.MonthWiseApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("home.php")
    Call<HomeApi> homeResults();

    @GET("day.php")
    Call<DayApi> dayResults();

    @GET("month.php")
    Call<MonthApi> monthresults();

    @POST("monthWiseData.php")
    @FormUrlEncoded
    Call<DayApi> monthwisedataresults(@Field("month") String month);
}
