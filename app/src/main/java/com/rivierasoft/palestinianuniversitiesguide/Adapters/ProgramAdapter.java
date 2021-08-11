package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rivierasoft.palestinianuniversitiesguide.Models.Program;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.AdapterViewHolder> {

    private ArrayList<Program> programs;
    private OnRVIClickListener listener;

    public ProgramAdapter(ArrayList<Program> programs, OnRVIClickListener listener) {
        this.programs = programs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program, parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Program program = programs.get(position);
        holder.textViewProgram.setText(program.getName());
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProgram;
        LinearLayout linearLayoutProgram;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewProgram = itemView.findViewById(R.id.textViewProgram);
            linearLayoutProgram = itemView.findViewById(R.id.linearLayoutProgram);

            linearLayoutProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(getAdapterPosition());
                }
            });
        }
    }
}
