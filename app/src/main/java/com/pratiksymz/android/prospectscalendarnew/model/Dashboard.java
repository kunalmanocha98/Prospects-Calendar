package com.pratiksymz.android.prospectscalendarnew.model;

import com.google.gson.annotations.SerializedName;

public class Dashboard {
    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String statusMessage;

    @SerializedName("result")
    private DashboardResult dashboardResult;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public DashboardResult getDashboardResult() {
        return dashboardResult;
    }
}
