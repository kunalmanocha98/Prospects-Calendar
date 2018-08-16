package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModMonthresults {
    @SerializedName("month")
    String month;

    @SerializedName("data")
    CalModMonthData calModMonthData;

    public String getMonth() {
        return month;
    }

    public CalModMonthData getCalModMonthData() {
        return calModMonthData;
    }
}
