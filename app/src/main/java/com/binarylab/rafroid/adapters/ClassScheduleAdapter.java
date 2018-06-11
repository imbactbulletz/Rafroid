package com.binarylab.rafroid.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.model.ClassSchedule;
import com.binarylab.rafroid.model.News;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClassScheduleAdapter extends RecyclerView.Adapter<ClassScheduleAdapter.NewsViewHolder> {


    private List<ClassSchedule> mClassScheduleList;
    private Context context;

    private static int currentPosition = -1;
    private static int lastPosition = -1;
    private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");

    public ClassScheduleAdapter(List<ClassSchedule> newsList, Context context) {
        this.mClassScheduleList = newsList;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_class_schedule, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        ClassSchedule classSchedule = mClassScheduleList.get(position);
        holder.tvClassName.setText(classSchedule.getClassName());
        holder.tvClassType.setText(classSchedule.getType());
        holder.tvClassLecturer.setText(classSchedule.getLecturer());
        holder.tvClassGroup.setText(classSchedule.getStudentGroups());
        holder.tvclassDayofweek.setText(classSchedule.getDayOfWeek().toString());

        String time = mFormat.format(classSchedule.getStartTime()) + "-" + mFormat.format(classSchedule.getEndTime());
        holder.tvClassTime.setText(time);
        holder.tvClassClassroom.setText(classSchedule.getClassroom());
    }

    @Override
    public int getItemCount() {
        return mClassScheduleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvClassType, tvClassLecturer, tvClassGroup, tvclassDayofweek,
        tvClassTime, tvClassClassroom;
        ConstraintLayout clRoot;

        NewsViewHolder(View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.class_name);
            tvClassType = itemView.findViewById(R.id.class_type);
            tvClassLecturer = itemView.findViewById(R.id.class_lecturer);
            tvClassGroup = itemView.findViewById(R.id.class_group);
            tvclassDayofweek = itemView.findViewById(R.id.class_dayofweek);
            tvClassTime = itemView.findViewById(R.id.class_time);
            tvClassClassroom = itemView.findViewById(R.id.class_classroom);
            clRoot = itemView.findViewById(R.id.news_feed_item);

        }
    }
}