package com.pratiksymz.android.prospectscalendarnew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MonthWiseDataResults {
    @SerializedName("month")
    String monthname;

    @SerializedName("data")
    List<DayResult> dayResults;

    public List<DayResult> getDayResults() {
        return dayResults;
    }

    public String getMonthname() {
        return monthname;
    }
}
