package com.agnitio.calendar.calendar;

import java.util.Date;

public class CalModCellEvent {
    /**
     * @id of the Calendar Cell
     */
    private int id;

    /**
     * @message of the Calendar Cell (represents total expenses that day)
     */
    private String message;

    /**
     * @date of the Calendar Cell
     */
    private Date date;

    /**
     * Public Constructor
     */
    public CalModCellEvent(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
