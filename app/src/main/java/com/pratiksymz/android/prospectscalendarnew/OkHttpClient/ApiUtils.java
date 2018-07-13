package com.pratiksymz.android.prospectscalendarnew.OkHttpClient;

public class ApiUtils {
    /**
     * Empty Constructor
     */
    private ApiUtils() {}

    public static ApiService getApiService() {
        return RetrofitClient.getRetrofitClient().create(ApiService.class);
    }
}
