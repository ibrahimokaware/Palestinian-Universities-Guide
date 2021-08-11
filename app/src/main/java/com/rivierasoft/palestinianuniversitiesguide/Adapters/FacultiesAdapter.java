package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rivierasoft.palestinianuniversitiesguide.Models.Faculty;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;


public class FacultiesAdapter extends RecyclerView.Adapter<FacultiesAdapter.ViewHolder> {

    private ArrayList<Faculty> list;
    private int faculty_id;
    private Context context;
    private OnRVIClickListener listener;

    public FacultiesAdapter(ArrayList<Faculty> list,  int faculty_id, Context context, OnRVIClickListener listener) {
        this.list = list;
        this.faculty_id = faculty_id;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Faculty faculty = list.get(position);
        holder.textView.setText(faculty.getName());
        if (faculty_id == faculty.getId())
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorSelected));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_faculty);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(getAdapterPosition());
                }
            });
        }
    }
}
