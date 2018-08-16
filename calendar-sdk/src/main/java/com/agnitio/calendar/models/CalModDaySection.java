package com.agnitio.calendar.models;

public class CalModDaySection {
    private String sectionDate;
    private int sectionExpenseTotal;

    public CalModDaySection(String sectionDate, int sectionExpenseTotal) {
        this.sectionDate = sectionDate;
        this.sectionExpenseTotal = sectionExpenseTotal;
    }

    public String getSectionDate() {
        return sectionDate;
    }

    public int getSectionExpenseTotal() {
        return sectionExpenseTotal;
    }
}
