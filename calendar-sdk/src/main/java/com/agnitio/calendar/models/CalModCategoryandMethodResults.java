package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModCategoryandMethodResults {
    @SerializedName("method")
    public List<CalModListItem> itemList;

    @SerializedName("category")
    public List<CalModCategoryList> categoryList;

    public List<CalModListItem> getItemList() {
        return itemList;
    }

    public List<CalModCategoryList> getCategoryList() {
        return categoryList;
    }
}
