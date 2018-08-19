package com.agnitio.calendar.interfaces;

/**
 * @interface to send data from CalModDialogAdapter to the CalModActivityAddEvent
 */
public interface CalModOnDataSelectListener {
    void onSelect(String data, boolean ischecked);
}
