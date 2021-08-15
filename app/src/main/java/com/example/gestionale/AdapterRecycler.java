package com.example.gestionale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id_array, descrizione_array, ora, data;

    AdapterRecycler(Activity activity, Context context, ArrayList id, ArrayList descrizione, ArrayList ora, ArrayList data){
        this.activity = activity;
        this.context = context;
        this.id_array = id;
        this.descrizione_array = descrizione;
        this.ora = ora;
        this.data = data;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, descrizione2, ora2, data2;
        CheckBox checkBox;
        LinearLayout mainLayout;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            descrizione2 = itemView.findViewById(R.id.descrizione);
            ora2 = itemView.findViewById(R.id.ora);
            data2 = itemView.findViewById(R.id.data);
            checkBox = itemView.findViewById(R.id.checkBox);
            mainLayout = itemView.findViewById(R.id.mainlayout);
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
    public void onBindViewHolder(@NonNull AdapterRecycler.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(String.valueOf(id_array.get(position)));
        holder.descrizione2.setText(String.valueOf(descrizione_array.get(position)));
        holder.ora2.setText(String.valueOf(ora.get(position)));
        holder.data2.setText(String.valueOf(data.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AggiornaActivity.class);
                intent.putExtra("id", String.valueOf(id_array.get(position)));
                intent.putExtra("descrizione", String.valueOf(descrizione_array.get(position)));
                intent.putExtra("ora", String.valueOf(ora.get(position)));
                intent.putExtra("data", String.valueOf(data.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_array.size();
    }


}
