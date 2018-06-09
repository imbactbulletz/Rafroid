package com.binarylab.rafroid.events;

public class LoadingFinishedEvent {

    private boolean isFailed;

    public LoadingFinishedEvent(boolean isFailed){
        this.isFailed = isFailed;
    }

    public boolean isFailed() {
        return isFailed;
    }
}
