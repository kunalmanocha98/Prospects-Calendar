package com.agnitio.prospectscalendar.interfaces;

/**
 * @interface to send data from DialogAdapter to the EditorActivity
 */
public interface DialogDataTransferInterface {
    void sendData(String data, boolean isPaymentMethod);
}
