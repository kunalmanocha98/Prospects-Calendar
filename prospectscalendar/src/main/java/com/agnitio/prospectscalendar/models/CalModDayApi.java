package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModDayApi {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private List<CalModDayResult> calModDayResults;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<CalModDayResult> getCalModDayResults() {
        return calModDayResults;
    }
}
