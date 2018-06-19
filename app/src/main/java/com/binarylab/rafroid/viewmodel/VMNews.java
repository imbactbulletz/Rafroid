package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.adapters.NewsAdapter;
import com.binarylab.rafroid.dao.NewsDAO;
import com.binarylab.rafroid.model.News;
import com.binarylab.rafroid.services.DatabaseUpdateService;
import com.binarylab.rafroid.services.DatabaseUpdateServiceMessage;

import java.util.List;

public class VMNews extends BaseObservable implements DatabaseUpdateServiceMessage{

    private List<News> mNewsList;
    private NewsAdapter mNewsAdapter;
    private Context mContext;

    private ObservableBoolean isLoading = new ObservableBoolean(false);
    public VMNews(Context context){
        this.mContext = context;
        NewsDAO dao = NewsDAO.getInstance();
        mNewsList = dao.getAll();
        mNewsAdapter = new NewsAdapter(mNewsList, context);
    }


    //Bindings//
    //--------------------------------------------------------------------------------------------//
    @Bindable
    public RecyclerView.Adapter getAdapter(){
        return mNewsAdapter;
    }

    @Bindable
    public boolean getNoDataVisible() {
        return mNewsAdapter.getItemCount() <= 0;
    }

    @Bindable
    public ObservableBoolean getIsLoading(){
        return isLoading;
    }


    //Actions//
    //--------------------------------------------------------------------------------------------//
    public void onRefresh(){
        DatabaseUpdateService service = new DatabaseUpdateService(this, mContext);
        service.execute();
        isLoading.set(true);
    }

    @Override
    public void setMessage(String message) {
        //Ignore
    }

    @Override
    public void notifyError() {
        Toast.makeText(mContext, mContext.getString(R.string.error_connecting_to_server), Toast.LENGTH_SHORT).show();
        isLoading.set(false);
    }

    @Override
    public void onPostUpdate() {
        NewsDAO dao = NewsDAO.getInstance();
        mNewsList = dao.getAll();
        mNewsAdapter.notifyDataSetChanged();
        isLoading.set(false);
    }
}
