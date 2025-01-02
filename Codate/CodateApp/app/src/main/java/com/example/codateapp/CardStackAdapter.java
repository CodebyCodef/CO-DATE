package com.example.codateapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<CardItem> items;

    public CardStackAdapter(List<CardItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem item = items.get(position);
        holder.titleTextView.setText(item.getAdSoyad());
        holder.yasTextView.setText(item.getYas());
        holder.imageView.setImageResource(item.getImageResId());
        holder.biographyTextView.setText(item.getBiography());


        holder.itemView.setOnClickListener(v -> {
            if (holder.biographyTextView.getVisibility() == View.VISIBLE) {
                holder.biographyTextView.setVisibility(View.GONE);
            } else {
                holder.biographyTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, yasTextView, biographyTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleText);
            yasTextView = itemView.findViewById(R.id.yasText);
            imageView = itemView.findViewById(R.id.imageView);
            biographyTextView = itemView.findViewById(R.id.biographyText);
        }
    }
}
