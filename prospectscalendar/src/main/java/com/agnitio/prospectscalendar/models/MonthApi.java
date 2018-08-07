package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MonthApi {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("result")
    List<Monthresults> monthresults;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Monthresults> getMonthresults() {
        return monthresults;
    }
}
