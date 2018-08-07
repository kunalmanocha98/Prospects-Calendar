package com.agnitio.prospectscalendar.retrofit;


import com.agnitio.prospectscalendar.models.DayApi;
import com.agnitio.prospectscalendar.models.HomeApi;
import com.agnitio.prospectscalendar.models.MonthApi;

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
