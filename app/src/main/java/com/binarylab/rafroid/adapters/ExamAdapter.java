package com.binarylab.rafroid.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.model.Exam;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.NewsViewHolder> {


    private static int currentPosition = -1;
    private static int lastPosition = -1;
    private List<Exam> mExamList;
    private Context context;
    private SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    public ExamAdapter(List<Exam> newsList, Context context) {
        this.mExamList = newsList;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_exam, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        Exam exam = mExamList.get(position);
        holder.tvClassName.setText(exam.getTestName());
        holder.tvClassLecturer.setText(exam.getProfessor());
        ;
        holder.tvclassDayofweek.setText(mFormat.format(exam.getStartTime()));
        holder.tvClassType.setText(exam.getType().toString());
        holder.tvClassTime.setText(mTimeFormat.format(exam.getStartTime()) + "-" + mTimeFormat.format(exam.getEndTime()));
        holder.tvClassClassroom.setText(exam.getClassroom());
    }

    @Override
    public int getItemCount() {
        return mExamList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvClassType, tvClassLecturer, tvclassDayofweek,
                tvClassTime, tvClassClassroom;
        ConstraintLayout clRoot;

        NewsViewHolder(View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.exam_name);
            tvClassType = itemView.findViewById(R.id.exam_type);
            tvClassLecturer = itemView.findViewById(R.id.exam_lecturer);
            tvclassDayofweek = itemView.findViewById(R.id.exam_dayofweek);
            tvClassTime = itemView.findViewById(R.id.exam_time);
            tvClassClassroom = itemView.findViewById(R.id.exam_classroom);
            clRoot = itemView.findViewById(R.id.news_feed_item);

        }
    }
}