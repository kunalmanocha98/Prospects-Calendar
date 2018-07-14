package com.pratiksymz.android.prospectscalendarnew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayApi {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private List<DayResult> dayResults;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<DayResult> getDayResults() {
        return dayResults;
    }
}
