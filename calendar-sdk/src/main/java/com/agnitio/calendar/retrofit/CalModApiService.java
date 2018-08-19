package com.agnitio.calendar.retrofit;


import com.agnitio.calendar.models.CalModCategoryListApi;
import com.agnitio.calendar.models.CalModDayApi;
import com.agnitio.calendar.models.CalModHomeApi;
import com.agnitio.calendar.models.CalModMonthApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CalModApiService {
    @GET("home.php")
    Call<CalModHomeApi> homeResults();

    @GET("day.php")
    Call<CalModDayApi> dayResults();

    @GET("month.php")
    Call<CalModMonthApi> monthresults();

    @GET("categoryList.php")
    Call<CalModCategoryListApi> categoryresults();

    @POST("monthWiseData.php")
    @FormUrlEncoded
    Call<CalModDayApi> monthwisedataresults(@Field("month") String month);
}
