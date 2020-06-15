package com.morangesoft.lotteryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder>{

    Context context;
    ArrayList<String> amountArray;
    ArrayList<String> horaArray;
    ArrayList<String> ticketsArray;

    public TicketsAdapter(Context context, ArrayList<String> amountArray, ArrayList<String> horaArray, ArrayList<String> ticketsArray) {
        this.context = context;
        this.amountArray = amountArray;
        this.horaArray = horaArray;
        this.ticketsArray = ticketsArray;
    }

    @NonNull
    @Override
    public TicketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_tickets_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsAdapter.ViewHolder holder, int position) {
        holder.amount.setText(amountArray.get(position));
        holder.tickets.setText(ticketsArray.get(position));
        holder.hora.setText(horaArray.get(position));
    }

    @Override
    public int getItemCount() {
        return amountArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount, hora ,tickets;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amountTextviewID);
            hora = itemView.findViewById(R.id.horaTextviewID);
            tickets = itemView.findViewById(R.id.ticketTextviewID);
        }
    }
}
