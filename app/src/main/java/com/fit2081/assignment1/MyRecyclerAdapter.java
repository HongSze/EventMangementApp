package com.fit2081.assignment1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    ArrayList<EventCategory> data = new ArrayList<EventCategory>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCatId.setText(String.valueOf(data.get(position).getCategoryID()));
        holder.tvCatName.setText(String.valueOf(data.get(position).getCategoryName()));
        holder.tvCatCount.setText(String.valueOf(data.get(position).getEventCount()));
        if (data.get(position).isActive()) {
            holder.tvIsActive.setText("Active");
        }else {
            holder.tvIsActive.setText("Inactive");
        }

        holder.cardView.setOnClickListener(v -> {
            String selectedCountry = data.get(position).getEventLocation();

            Context context = holder.cardView.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("CATEGORY_LOCATION", selectedCountry);
            context.startActivity(intent);
        });
    }


    public void setData(ArrayList<EventCategory> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }
        // else return zero if data is null
        return 0;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCatName;
        public TextView tvCatId;
        public TextView tvIsActive;
        public TextView tvCatCount;
        public CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatId = itemView.findViewById(R.id.tv_id);
            tvCatName = itemView.findViewById(R.id.tv_name);
            tvCatCount = itemView.findViewById(R.id.tv_count);
            tvIsActive = itemView.findViewById(R.id.tv_active);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
