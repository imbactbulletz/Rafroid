package com.binarylab.rafroid.services;

public interface DatabaseUpdateServiceMessage {

    void setMessage(String message);
    void notifyError();
    void onPostUpdate();

}
