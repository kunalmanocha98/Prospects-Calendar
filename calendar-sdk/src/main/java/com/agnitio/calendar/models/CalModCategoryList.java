package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModCategoryList {
    @SerializedName("id")
    public int categoryid;

    @SerializedName("name")
    public String categoryname;

    @SerializedName("icon")
    public String categoryiconpath;

    @SerializedName("subcategory")
    public List<CalModListItem> subcategorylist;

    public int getCategoryid() {
        return categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public String getCategoryiconpath() {
        return categoryiconpath;
    }

    public List<CalModListItem> getSubcategorylist() {
        return subcategorylist;
    }
}
