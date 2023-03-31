package com.example.bookhotel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {
    private ArrayList<String> facilities;

    public FacilitiesAdapter(ArrayList<String> facilities) {
        this.facilities = facilities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.deleteButton.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            facilities.remove(position);
            notifyItemRemoved(position);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String facility = facilities.get(position);
        holder.facilityTextView.setText(facility);
        holder.deleteButton.setOnClickListener(v -> {
            facilities.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView facilityTextView;
        ImageButton deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            facilityTextView = itemView.findViewById(R.id.facilityTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}