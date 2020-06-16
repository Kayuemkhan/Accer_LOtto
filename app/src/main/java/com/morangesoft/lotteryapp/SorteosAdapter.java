package com.morangesoft.lotteryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SorteosAdapter extends RecyclerView.Adapter<SorteosAdapter.ViewHolder> {
    Context context;
    ArrayList<String> primeraArray = new ArrayList<>();
    ArrayList<String> segundaArray = new ArrayList<>();
    ArrayList<String> terceraArray = new ArrayList<>();

    public SorteosAdapter(Context context, ArrayList<String> primeraArray, ArrayList<String> segundaArray, ArrayList<String> terceraArray) {
        this.context = context;
        this.primeraArray = primeraArray;
        this.segundaArray = segundaArray;
        this.terceraArray = terceraArray;
    }

    @NonNull
    @Override
    public SorteosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_sorteos_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SorteosAdapter.ViewHolder holder, int position) {
        holder.primera.setText(primeraArray.get(position));
        holder.segunda.setText(segundaArray.get(position));
        holder.tercera.setText(terceraArray.get(position));

    }

    @Override
    public int getItemCount() {
        return primeraArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loteria, primera, segunda, tercera;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loteria = itemView.findViewById(R.id.LoteriaID);
            primera = itemView.findViewById(R.id.PrimeraID);
            segunda = itemView.findViewById(R.id.SegundaID);
            tercera = itemView.findViewById(R.id.TerceraID);
        }
    }
}
