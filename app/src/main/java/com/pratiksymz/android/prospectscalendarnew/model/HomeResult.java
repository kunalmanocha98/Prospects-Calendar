package com.pratiksymz.android.prospectscalendarnew.model;

import com.google.gson.annotations.SerializedName;

public class HomeResult {
    @SerializedName("year")
    private int yearTotal;

    @SerializedName("year_name")
    private String yearName;

    @SerializedName("month")
    private int monthTotal;

    @SerializedName("month_name")
    private String monthName;

    @SerializedName("status_month")
    private String monthStatus;

    @SerializedName("date_value")
    private int todayTotal;

    @SerializedName("date")
    private String todayDate;

    @SerializedName("status_day")
    private String todayStatus;

    public int getYearTotal() {
        return yearTotal;
    }

    public int getMonthTotal() {
        return monthTotal;
    }

    public String getMonthStatus() {
        return monthStatus;
    }

    public int getTodayTotal() {
        return todayTotal;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public String getTodayStatus() {
        return todayStatus;
    }
}
