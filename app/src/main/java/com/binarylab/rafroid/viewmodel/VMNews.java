package com.binarylab.rafroid.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;

import com.binarylab.rafroid.adapters.NewsAdapter;
import com.binarylab.rafroid.dao.NewsDAO;
import com.binarylab.rafroid.model.News;

import java.util.List;

public class VMNews extends BaseObservable{

    private List<News> mNewsList;
    private NewsAdapter mNewsAdapter;
    private Context mContext;

    public VMNews(Context context){
        this.mContext = context;
        NewsDAO dao = NewsDAO.getInstance();
        mNewsList = dao.getAll();
        mNewsAdapter = new NewsAdapter(mNewsList, context);
    }


    @Bindable
    public RecyclerView.Adapter getAdapter(){
        return mNewsAdapter;
    }

    @Bindable
    public boolean getNoDataVisible() {
        return mNewsAdapter.getItemCount() <= 0;
    }

}
