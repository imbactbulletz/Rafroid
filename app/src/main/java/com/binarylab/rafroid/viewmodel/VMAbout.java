package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.binarylab.rafroid.R;

public class VMAbout extends BaseObservable {
    private Context context;


    private String aboutContent;
    private String aboutPhoneTextView;
    private String aboutMailTextView;
    private String aboutApplicationMadeBy;
    private String aboutDejanNameTextView;
    private String aboutStefanNameTextView;

    public VMAbout(Context context){
        this.context = context;

        aboutContent = context.getString(R.string.aboutContent);
        aboutPhoneTextView = context.getString(R.string.aboutPhoneTextView);
        aboutMailTextView = context.getString(R.string.aboutMailTextView);
        aboutApplicationMadeBy = context.getString(R.string.aboutApplicationMadeBy);
        aboutDejanNameTextView = context.getString(R.string.aboutDejanNameTextView);
        aboutStefanNameTextView = context.getString(R.string.aboutStefanNameTextView);
    }

    @Bindable
    public String getAboutContent(){
        return aboutContent;
    }

    @Bindable
    public String getAboutPhoneTextView() {
        return aboutPhoneTextView;
    }

    @Bindable
    public String getAboutMailTextView() {
        return aboutMailTextView;
    }

    @Bindable
    public String getAboutApplicationMadeBy() {
        return aboutApplicationMadeBy;
    }

    @Bindable
    public String getAboutDejanNameTextView() {
        return aboutDejanNameTextView;
    }

    @Bindable
    public String getAboutStefanNameTextView() {
        return aboutStefanNameTextView;
    }
}
