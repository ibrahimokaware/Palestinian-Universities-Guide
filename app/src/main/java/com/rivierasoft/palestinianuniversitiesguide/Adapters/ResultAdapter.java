package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;
import com.rivierasoft.palestinianuniversitiesguide.Models.Result;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.AdapterViewHolder> {

    private ArrayList<Result> results;
    private int activityType;
    private int login;
    private OnRVIClickListener listener;
    private OnRVIClickListener listener2;


    public ResultAdapter(ArrayList<Result> results, int activityType, int login, OnRVIClickListener listener, OnRVIClickListener listener2) {
        this.results = results;
        this.activityType = activityType;
        this.login = login;
        this.listener = listener;
        this.listener2 = listener2;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Result result = results.get(position);
        holder.programTV.setText(result.getProgram());
        holder.degreeTV.setText(result.getDegree());
        holder.universityTV.setText(result.getUniversity());
        if (result.isSave())
            holder.saveUnSaveIV.setImageResource(R.drawable.ic_unsave);
        else holder.saveUnSaveIV.setImageResource(R.drawable.ic_save);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView programTV, degreeTV, universityTV;
        ImageView saveUnSaveIV;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_result);
            programTV = itemView.findViewById(R.id.tv_result_program);
            degreeTV = itemView.findViewById(R.id.tv_result_degree);
            universityTV = itemView.findViewById(R.id.tv_result_university);
            saveUnSaveIV = itemView.findViewById(R.id.iv_save_unsave);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(getAdapterPosition());
                }
            });

            saveUnSaveIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Result result = results.get(position);
                    listener2.OnClickListener(getAdapterPosition());
                    if (activityType == 1) {
                        if (result.isSave()) {
                            result.setSave(false);
                        } else if (!result.isSave()){
                            result.setSave(true);
                        }
                        notifyItemChanged(position);
                    } else {
                        results.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}
