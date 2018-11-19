package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModCategoryListApi {
    @SerializedName("code")
    public Integer code;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public CalModCategoryandMethodResults calModCategoryandMethodResults;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public CalModCategoryandMethodResults getCalModCategoryandMethodResults() {
        return calModCategoryandMethodResults;
    }
}
