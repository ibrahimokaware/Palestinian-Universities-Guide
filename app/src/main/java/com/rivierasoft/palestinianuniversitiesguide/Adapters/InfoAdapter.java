package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private ArrayList<Info> infoList;
    private String type;
    private Context context;
    private OnRVIClickListener listener;

    public InfoAdapter(ArrayList<Info> infoList, String type, Context context, OnRVIClickListener listener) {
        this.infoList = infoList;
        this.type = type;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Info info = infoList.get(position);
        holder.iconIV.setImageResource(info.getIcon());
        holder.titleTV.setText(info.getTitle());
        holder.valueTV.setText(info.getValue());
        if (type.equals("c"))
            if (info.getTitle().equals(context.getString(R.string.email)) || info.getTitle().equals(context.getString(R.string.website)))
                holder.valueTV.setPaintFlags(holder.valueTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (type.equals("cs"))
            holder.valueTV.setPaintFlags(holder.valueTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (position+1 == infoList.size())
            holder.view.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        ImageView iconIV;
        TextView titleTV, valueTV;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.ll_info);
            iconIV = itemView.findViewById(R.id.iv_info_icon);
            titleTV = itemView.findViewById(R.id.tv_info_title);
            valueTV = itemView.findViewById(R.id.tv_info_value);
            view = itemView.findViewById(R.id.view_info);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(getAdapterPosition());
                }
            });
        }
    }
}
