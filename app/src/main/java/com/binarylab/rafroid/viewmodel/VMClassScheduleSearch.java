package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.binarylab.rafroid.BR;

public class VMClassScheduleSearch extends BaseObservable{

    private Context mContext;
    private boolean isInputVisible;

    public VMClassScheduleSearch(Context context){
        this.mContext = context;

        isInputVisible = true;
    }


    //Bindings//
    //--------------------------------------------------------------------------------------------//
    @Bindable
    public boolean isInputVisible() {
        return isInputVisible;
    }

    public void setInputVisible(boolean inputVisible) {
        isInputVisible = inputVisible;
        notifyPropertyChanged(BR.inputVisible);
    }
}
