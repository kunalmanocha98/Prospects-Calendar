package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModMonthData {

    @SerializedName("total")
    String total;

    public String getTotal() {
        return total;
    }
}
