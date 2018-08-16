package com.agnitio.calendar.retrofit;

public class CalModApiUtils {
    /**
     * Empty Constructor
     */
    private CalModApiUtils() {}

    public static CalModApiService getApiService() {
        return CalModRetrofitClient.getRetrofitClient().create(CalModApiService.class);
    }
}
