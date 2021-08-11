package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.content.Context;
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
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.adaptervh> {

    private ArrayList<University> universities;
    private Context context;
    private OnRVIClickListener listener;

    public UniversityAdapter(ArrayList<University> universities, Context context, OnRVIClickListener listener) {
        this.universities = universities;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public adaptervh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.university, parent,false);
        adaptervh vh = new adaptervh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptervh holder, int position) {
        University u = universities.get(position);
        Picasso.with(context)
                .load(u.getLogo())
                .placeholder(R.drawable.app_icon)
                //.error(R.drawable.ic_broken_image)
                .into(holder.imageView);
        //holder.imageView.setImageResource(u.getImg());
        holder.textView.setText(u.getName());
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    class adaptervh extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        CardView cardView;

        public adaptervh(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ui);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cv);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(getAdapterPosition());
                }
            });

        }
    }
}
