package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalModCategoryListApi {
    @SerializedName("code")
    private Integer code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private CategoryListResults categoryListResults;

}
