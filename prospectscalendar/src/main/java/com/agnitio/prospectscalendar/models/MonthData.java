package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

public class MonthData {

    @SerializedName("total")
    String total;

    public String getTotal() {
        return total;
    }
}
