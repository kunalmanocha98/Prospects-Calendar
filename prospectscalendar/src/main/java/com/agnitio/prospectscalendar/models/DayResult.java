package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class DayResult {
    @SerializedName("date")
    private String date;

    @SerializedName("data")
    private DateData dateData;

    public String getDate() {
        return date;
    }

    public DateData getDateData() {
        return dateData;
    }
}
