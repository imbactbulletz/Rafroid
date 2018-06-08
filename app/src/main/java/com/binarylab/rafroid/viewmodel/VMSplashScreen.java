package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.events.LoadingFinishedEvent;
import com.binarylab.rafroid.services.DatabaseUpdateService;
import com.binarylab.rafroid.services.DatabaseUpdateServiceMessage;

import org.greenrobot.eventbus.EventBus;

public class VMSplashScreen extends BaseObservable implements DatabaseUpdateServiceMessage {

    private String loadingMessage;
    private Context mContext;

    public VMSplashScreen(Context context){
        this.mContext = context;

        new DatabaseUpdateService(this, context).execute();
    }

    //Bindings//
    //--------------------------------------------------------------------------------------------//
    @Bindable
    public String getLoadingMessage(){
        return loadingMessage;
    }

    @Override
    public void setMessage(String message) {
        loadingMessage = message;
        notifyChange();
    }

    @Override
    public void notifyError() {
        Toast.makeText(mContext, R.string.error_connecting_to_server, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUpdate() {
        EventBus.getDefault().post(new LoadingFinishedEvent());
    }
}
