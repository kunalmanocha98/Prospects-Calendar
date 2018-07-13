package com.pratiksymz.android.prospectscalendarnew.model;

import com.google.gson.annotations.SerializedName;

public class DashboardResult {
    @SerializedName("year")
    private int yearTotal;

    @SerializedName("year_name")
    private String yearName;

    @SerializedName("month")
    private int monthTotal;

    @SerializedName("month_name")
    private String monthName;

    @SerializedName("date_value")
    private int todayTotal;

    @SerializedName("date")
    private String todayDate;

    public int getYearTotal() {
        return yearTotal;
    }

    public int getMonthTotal() {
        return monthTotal;
    }

    public int getTodayTotal() {
        return todayTotal;
    }

    public String getTodayDate() {
        return todayDate;
    }
}
