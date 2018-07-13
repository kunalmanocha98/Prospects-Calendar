package com.pratiksymz.android.prospectscalendarnew.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DateData {
    @SerializedName("total")
    private int dayTotal;

    @SerializedName("expenses")
    private List<DayItem> dayItems;
}
