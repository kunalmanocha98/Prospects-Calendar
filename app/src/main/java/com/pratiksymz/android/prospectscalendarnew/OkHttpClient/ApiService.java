package com.pratiksymz.android.prospectscalendarnew.OkHttpClient;

import com.pratiksymz.android.prospectscalendarnew.model.Dashboard;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("dashboard.php")
    Call<Dashboard> results();
}
