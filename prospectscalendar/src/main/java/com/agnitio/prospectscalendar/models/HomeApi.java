package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class HomeApi {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private HomeResult homeResult;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public HomeResult getHomeResult() {
        return homeResult;
    }
}
