package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class Monthresults {
    @SerializedName("month")
    String month;

    @SerializedName("data")
    MonthData monthData;

    public String getMonth() {
        return month;
    }

    public MonthData getMonthData() {
        return monthData;
    }
}
