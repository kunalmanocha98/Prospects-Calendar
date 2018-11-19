package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModListItem {
    @SerializedName("name")
    public String itemname;

    @SerializedName("id")
    public int itemid;

    @SerializedName("icon")
    public String iconpath;

    public String getItemname() {
        return itemname;
    }

    public int getItemid() {
        return itemid;
    }

    public String getIconpath() {
        return iconpath;
    }
}
