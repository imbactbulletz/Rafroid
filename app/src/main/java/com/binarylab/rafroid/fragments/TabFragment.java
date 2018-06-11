package com.binarylab.rafroid.fragments;


import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

public abstract class TabFragment extends Fragment {

    private String mTitle;
    private Drawable mIcon;

    public Drawable getIcon() {
        return mIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }
}
