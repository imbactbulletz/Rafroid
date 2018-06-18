package com.binarylab.rafroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.model.Consultation;

import java.text.SimpleDateFormat;
import java.util.List;

public class ConsultationsAdapter extends RecyclerView.Adapter<ConsultationsAdapter.ConsultationsViewHolder> {

    private static int currentPosition = -1;
    private static int lastPosition = -1;

    private List<Consultation> mConsultationsList;
    private Context context;

    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");


    public ConsultationsAdapter(List<Consultation> consultationsList, Context context){
        this.mConsultationsList = consultationsList;
        this.context = context;
    }


    @Override
    public ConsultationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_consultations, parent, false);
        return new ConsultationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationsViewHolder holder, int position) {
        Consultation consultation = mConsultationsList.get(position);

        holder.tvConsultationsName.setText(consultation.getClassName());
        holder.tvConsultationsLecturer.setText(consultation.getLecturer());
        holder.tvConsultationsDayOfWeek.setText(consultation.getDay());
        holder.tvConsultationsTime.setText(mTimeFormat.format(consultation.getStartTime()) + "-" + mTimeFormat.format(consultation.getEndTime()));
        holder.tvConsultationsClassroom.setText(consultation.getClassroom());
    }

    @Override
    public int getItemCount() {
        return mConsultationsList.size();
    }


    class ConsultationsViewHolder extends RecyclerView.ViewHolder {
        TextView tvConsultationsName, tvConsultationsLecturer, tvConsultationsDayOfWeek, tvConsultationsTime, tvConsultationsClassroom;

        ConstraintLayout clRoot;

        ConsultationsViewHolder(View itemView){
            super(itemView);

            tvConsultationsName = itemView.findViewById(R.id.consultations_name);
            tvConsultationsLecturer = itemView.findViewById(R.id.consultations_lecturer);
            tvConsultationsDayOfWeek = itemView.findViewById(R.id.consultations_dayofweek);
            tvConsultationsTime = itemView.findViewById(R.id.consultations_time);
            tvConsultationsClassroom = itemView.findViewById(R.id.consultations_classroom);

            clRoot = itemView.findViewById(R.id.consultations_schedule_item);
        }

    }
}
