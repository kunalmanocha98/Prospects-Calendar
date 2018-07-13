package com.pratiksymz.android.prospectscalendarnew.interfaces;

/**
 * @interface to send data from DialogAdapter to the EditorActivity
 */
public interface DataTransferInterface {
    void sendData(String data, boolean isPaymentMethod);
}
