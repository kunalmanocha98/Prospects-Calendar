package com.agnitio.prospectscalendar.retrofit;

public class ApiUtils {
    /**
     * Empty Constructor
     */
    private ApiUtils() {}

    public static ApiService getApiService() {
        return RetrofitClient.getRetrofitClient().create(ApiService.class);
    }
}
