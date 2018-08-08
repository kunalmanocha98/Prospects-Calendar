package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModHomeApi {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private CalModHomeResult calModHomeResult;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public CalModHomeResult getCalModHomeResult() {
        return calModHomeResult;
    }
}
