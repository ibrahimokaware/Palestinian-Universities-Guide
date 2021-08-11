package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivierasoft.palestinianuniversitiesguide.Models.FacultyModel;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ProgramAdapterViewHolder> {

    ArrayList<FacultyModel> faculties;
    Context context;

    public FacultyAdapter(ArrayList<FacultyModel> faculties, Context context) {
        this.faculties = faculties;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgramAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty,parent,false);
        ProgramAdapterViewHolder adapterViewHolder = new ProgramAdapterViewHolder(view);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapterViewHolder holder, int position) {
        FacultyModel facultyModel = faculties.get(position);
        holder.textViewFaculty.setText(facultyModel.getFaculty());
        holder.recyclerViewProgram.setAdapter(facultyModel.getAdapter());
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    class ProgramAdapterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutFaculty;
        TextView textViewFaculty;
        RecyclerView recyclerViewProgram;

        public ProgramAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutFaculty = itemView.findViewById(R.id.linearLayoutFaculty);
            textViewFaculty = itemView.findViewById(R.id.textViewFaculty);
            recyclerViewProgram = itemView.findViewById(R.id.recyclerViewProgram);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

            recyclerViewProgram.setHasFixedSize(true);
            recyclerViewProgram.setLayoutManager(layoutManager);

        }
    }
}

