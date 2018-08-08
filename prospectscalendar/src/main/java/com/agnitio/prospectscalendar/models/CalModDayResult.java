package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModDayResult {
    @SerializedName("date")
    private String date;

    @SerializedName("data")
    private CalModDateData calModDateData;

    public String getDate() {
        return date;
    }

    public CalModDateData getCalModDateData() {
        return calModDateData;
    }
}
