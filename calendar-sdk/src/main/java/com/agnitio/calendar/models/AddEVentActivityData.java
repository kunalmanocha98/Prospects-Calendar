package com.agnitio.calendar.models;

public class AddEVentActivityData {

   public static String start_date="",end_date="",time="",amount="",method="",category="",subcategory="",description="";

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getDescription() {
        return description;
    }
}
