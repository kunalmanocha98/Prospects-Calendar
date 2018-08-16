package com.agnitio.calendar.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalModRetrofitClient {
    private static Retrofit mRetrofit;
    private static final String BASE_URL = "http://bydegreestest.agnitioworld.com/calendar_management/";

    public static Retrofit getRetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Build the Http Client
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .cache(null)
                .addInterceptor(loggingInterceptor);

        // Build the Retrofit Client
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
        }

        return mRetrofit;
    }
}
