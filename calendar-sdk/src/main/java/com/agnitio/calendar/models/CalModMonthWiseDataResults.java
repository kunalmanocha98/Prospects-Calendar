package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModMonthWiseDataResults {
    @SerializedName("month")
    String monthname;

    @SerializedName("data")
    List<CalModDayResult> calModDayResults;

    public List<CalModDayResult> getCalModDayResults() {
        return calModDayResults;
    }

    public String getMonthname() {
        return monthname;
    }
}
