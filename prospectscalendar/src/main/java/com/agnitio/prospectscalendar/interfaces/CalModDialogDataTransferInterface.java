package com.agnitio.prospectscalendar.interfaces;

/**
 * @interface to send data from CalModDialogAdapter to the CalModActivityAddEvent
 */
public interface CalModDialogDataTransferInterface {
    void sendData(String data, boolean isPaymentMethod);
}
