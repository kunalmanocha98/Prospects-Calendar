package com.pratiksymz.android.prospectscalendarnew.models;

import com.google.gson.annotations.SerializedName;

public class MonthWiseApi {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("result")
    MonthWiseDataResults monthWiseDataResults;

    public MonthWiseDataResults getMonthWiseDataResults() {
        return monthWiseDataResults;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
