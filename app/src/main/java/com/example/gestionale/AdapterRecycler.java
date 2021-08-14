package com.example.gestionale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    private Context context;
    private ArrayList id_array, descrizione;

    AdapterRecycler(Context context, ArrayList id, ArrayList descrizione){
        this.context = context;
        this.id_array = id;
        this.descrizione = descrizione;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView descrizione;
        CheckBox checkBox;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            descrizione = itemView.findViewById(R.id.Descrizione);
            id = itemView.findViewById(R.id.id);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    @NonNull
    @Override
    public AdapterRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singola_priorita, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.MyViewHolder holder, int position) {
        holder.descrizione.setText(String.valueOf(descrizione.get(position)));
        holder.id.setText(String.valueOf(id_array.get(position)));
    }

    @Override
    public int getItemCount() {
        return id_array.size();
    }


}
