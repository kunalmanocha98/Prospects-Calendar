package com.pratiksymz.android.prospectscalendarnew.model;

import com.google.gson.annotations.SerializedName;

public class DayApi {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private DayResult dayResult;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public DayResult getDayResult() {
        return dayResult;
    }
}
