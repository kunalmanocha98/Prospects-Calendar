package com.agnitio.calendar.models;

import com.google.gson.annotations.SerializedName;

public class CalModDayItem {


    @SerializedName("id")
    private String id;

    @SerializedName("time")
    private long time;

    @SerializedName("category")
    private String category;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("amount")
    private int amount;

    @SerializedName("colorcode")
    private String colorcode;

    @SerializedName("contents")
    private String contents;

    @SerializedName("receipt_url")
    private String receiptUrl;


    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getColorcode() {
        return colorcode;
    }

    public String getCategory() {
        return category;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getAmount() {
        return amount;
    }

    public String getContents() {
        return contents;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }
}
