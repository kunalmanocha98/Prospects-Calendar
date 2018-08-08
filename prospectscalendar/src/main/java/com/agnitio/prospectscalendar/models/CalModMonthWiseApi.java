package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModMonthWiseApi {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("result")
    CalModMonthWiseDataResults calModMonthWiseDataResults;

    public CalModMonthWiseDataResults getCalModMonthWiseDataResults() {
        return calModMonthWiseDataResults;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
