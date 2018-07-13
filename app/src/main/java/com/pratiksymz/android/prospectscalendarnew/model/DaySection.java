package com.pratiksymz.android.prospectscalendarnew.model;

public class DaySection {
    private String sectionDate;
    private int sectionExpenseTotal;

    public DaySection(String sectionDate, int sectionExpenseTotal) {
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
