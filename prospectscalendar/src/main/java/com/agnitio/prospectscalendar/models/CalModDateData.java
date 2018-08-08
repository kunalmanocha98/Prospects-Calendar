package com.agnitio.prospectscalendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModDateData {
    @SerializedName("total")
    private int dayTotal;

    @SerializedName("day_items")
    private List<CalModDayItem> calModDayItems;

    public int getDayTotal() {
        return dayTotal;
    }

    public List<CalModDayItem> getCalModDayItems() {
        return calModDayItems;
    }
}
