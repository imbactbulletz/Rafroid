package com.binarylab.rafroid.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.model.News;
import com.binarylab.rafroid.util.ViewAnimationUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private List<News> newsLis;
    private Context context;

    private static int currentPosition = 0;
    private static int lastPosition = -1;
    private SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy");

    public NewsAdapter(List<News> newsList, Context context) {
        this.newsLis = newsList;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        News news = newsLis.get(position);
        holder.tvNewsTitle.setText(news.getTitle());
        holder.tvDate.setText(mFormat.format(news.getDate()));
        holder.tvNewsText.setText(news.getText());
        holder.tvNewsText.setVisibility(View.GONE);
        holder.icon_expand.animate().rotation(0).start();

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            holder.tvNewsText.setVisibility(View.VISIBLE);

            //adding sliding effect
            holder.tvNewsText.startAnimation(slideDown);

            holder.icon_expand.animate().rotation(180).start();
        }

        holder.icon_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                if(currentPosition != position) {
                    lastPosition = currentPosition;
                    currentPosition = position;
                    //reloding the list
                    notifyDataSetChanged();
                }else{
                    currentPosition = -1;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsLis.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsTitle, tvDate, tvNewsText;
        ConstraintLayout clRoot;
        ImageView icon_expand;

        NewsViewHolder(View itemView) {
            super(itemView);

            tvNewsTitle = itemView.findViewById(R.id.news_title);
            tvDate = itemView.findViewById(R.id.date_text);
            tvNewsText = itemView.findViewById(R.id.news_text);
            icon_expand = itemView.findViewById(R.id.icon_expand);
            clRoot = itemView.findViewById(R.id.news_feed_item);

        }
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight * 10000/ v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}