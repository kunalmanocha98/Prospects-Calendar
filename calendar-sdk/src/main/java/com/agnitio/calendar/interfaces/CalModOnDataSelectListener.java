package com.agnitio.calendar.interfaces;

/**
 * @interface to send data from CalModCategoryDialogAdapter to the CalModActivityAddEvent
 */
public interface CalModOnDataSelectListener {
    void onSelect(int position,String categoryname);
}
