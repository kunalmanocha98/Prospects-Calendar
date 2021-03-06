package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModChartResults {

    @SerializedName("month")
    String month_name;

    @SerializedName("data")
    Integer month_data;

    public String getMonth_name() {
        return month_name;
    }

    public Integer getMonth_data() {
        return month_data;
    }
}
