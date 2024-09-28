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

public class MyRecyclerAdapterEvent extends RecyclerView.Adapter<MyRecyclerAdapterEvent.CustomViewHolder> {
    ArrayList<Event> data = new ArrayList<Event>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_event, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapterEvent.CustomViewHolder holder, int position) {
        holder.tvEventName.setText(String.valueOf(data.get(position).getEventName()));
        holder.tvEventId.setText(String.valueOf(data.get(position).getEventId()));
        holder.tvCatId.setText(String.valueOf(data.get(position).getCatId()));
        holder.tvTixAvai.setText(String.valueOf(data.get(position).getTixAvailable()));
        if (data.get(position).isActive()) {
            holder.tvIsActive1.setText("Active");
        }else { holder.tvIsActive1.setText("Inactive");
        }

        holder.cardView.setOnClickListener(v -> {
            String eventName = data.get(position).getEventName();

            Context context = holder.cardView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("EVENT_NAME", eventName);
            context.startActivity(intent);
        });

    }

    public void setData(ArrayList<Event> data) {
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

        public TextView tvEventId;
        public TextView tvEventName;
        public TextView tvCatId;
        public TextView tvTixAvai;
        public TextView tvIsActive1;
        public CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.EventName);
            tvEventId = itemView.findViewById(R.id.EventId);
            tvCatId = itemView.findViewById(R.id.CatId);
            tvTixAvai = itemView.findViewById(R.id.TixAvai);
            tvIsActive1 = itemView.findViewById(R.id.isActive5);
            cardView = itemView.findViewById(R.id.CardView2);
        }
    }
}
