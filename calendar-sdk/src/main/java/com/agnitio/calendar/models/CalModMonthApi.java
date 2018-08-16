package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModMonthApi {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("result")
    List<CalModMonthresults> monthresults;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<CalModMonthresults> getMonthresults() {
        return monthresults;
    }
}
