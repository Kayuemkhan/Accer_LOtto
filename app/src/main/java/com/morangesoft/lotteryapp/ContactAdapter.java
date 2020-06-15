package com.morangesoft.lotteryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<String> name;
    ArrayList<String> phone;

    public ContactAdapter(Context context, ArrayList name, ArrayList phone) {
        this.context = context;
        this.name = name;
        this.phone = phone;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_contact_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.eName.setText(name.get(position));
        holder.ePhone.setText(phone.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eName, ePhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eName = itemView.findViewById(R.id.sName);
            ePhone = itemView.findViewById(R.id.sPhone);
        }
    }
}
