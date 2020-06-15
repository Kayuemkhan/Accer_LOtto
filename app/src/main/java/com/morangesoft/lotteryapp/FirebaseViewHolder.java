package com.morangesoft.lotteryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseViewHolder extends RecyclerView.Adapter<FirebaseViewHolder.ViewHolder>{
    Context co;
    public ArrayList<DatasetFireabase>values;

    public FirebaseViewHolder(ArrayList<DatasetFireabase> myDataset,Context context) {
        co = context;
        values = myDataset;
    }
    @NonNull
    @Override
    public FirebaseViewHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.samplelayout,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    public void add(int position, DatasetFireabase item){
        values.add(position,item);
       // notifyItemInserted(position);
    }
    public void remove (int position){
        values.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(@NonNull FirebaseViewHolder.ViewHolder holder, int position) {
        DatasetFireabase name = values.get(position);
        //add(position,name);
        holder.lottery.setText(name.getLotterynum());
        holder.pr.setText(name.getPr());
        holder.numero.setText(name.getNumero());
        holder.valor.setText(name.getValor());
    }
    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lottery,pr,numero,valor;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout =itemView;
            lottery = itemView.findViewById(R.id.loteria);
            pr = itemView.findViewById(R.id.pr);
            numero = itemView.findViewById(R.id.numero);
            valor = itemView.findViewById(R.id.valor);
        }
    }
}