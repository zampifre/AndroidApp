package com.example.gestionale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id_array, descrizione_array, ora, data;

    Animation animazione;
    AdapterRecycler(Activity activity, Context context, ArrayList id, ArrayList descrizione, ArrayList ora, ArrayList data){
        this.activity = activity;
        this.context = context;
        this.id_array = id;
        this.descrizione_array = descrizione;
        this.ora = ora;
        this.data = data;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idt, descrizione2, ora2, data2;
        CheckBox checkBox;
        LinearLayout mainLayout;
        String id;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //idt = itemView.findViewById(R.id.id);
            descrizione2 = itemView.findViewById(R.id.descrizione);
            ora2 = itemView.findViewById(R.id.ora);
            data2 = itemView.findViewById(R.id.data);
            checkBox = itemView.findViewById(R.id.checkBox);
            mainLayout = itemView.findViewById(R.id.mainlayout);
            animazione = AnimationUtils.loadAnimation(context, R.anim.mia_animazione);
            mainLayout.setAnimation(animazione);
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
        //holder.idt.setText(String.valueOf(id_array.get(position)));
        bubbleSort();

        holder.descrizione2.setText(String.valueOf(descrizione_array.get(position)));
        holder.ora2.setText(String.valueOf(ora.get(position)));
        holder.data2.setText(String.valueOf(data.get(position)));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    DbPriorità db = new DbPriorità(context);
                    db.cancellaRiga((String) id_array.get(position));
                    activity.recreate();
                } else{
                    ;
                }
            }
        });
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

    public void bubbleSort() {
        ArrayList <Date> tmp = daStringADate(data);
        boolean sorted = false;
        Object temp2;
        Date temp1;
        String temp3;
        String temp4;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < tmp.size() - 1; i++) {
                if (tmp.get(i).compareTo(tmp.get(i + 1)) > 0){
                    temp1 = tmp.get(i);
                    temp2 = id_array.get(i);
                    temp3 = (String) descrizione_array.get(i);
                    temp4 = (String) ora.get(i);
                    tmp.set(i, tmp.get(i + 1));
                    id_array.set(i, id_array.get(i + 1));
                    descrizione_array.set(i, descrizione_array.get(i+1));
                    ora.set(i, ora.get(i+1));
                    tmp.set((i + 1), temp1);
                    id_array.set(i+1, temp2);
                    descrizione_array.set(i + 1, temp3);
                    ora.set(i+1,temp4);

                    sorted = false;
                }
            }
        }
        //devo riconvertire l'array di date in stringhe
        data = daDateAString(tmp);
    }

    public ArrayList <Date> daStringADate(ArrayList data){
        ArrayList <Date> res = new ArrayList<Date>();
        for(int i = 0; i < data.size(); ++i){
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy", Locale.ITALIAN);
            try {
                res.add(f.parse(String.valueOf(data.get(i))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public ArrayList daDateAString(ArrayList <Date> data){
        ArrayList stringhe = new ArrayList();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for(int i = 0; i < data.size(); ++i){
            stringhe.add(df.format(data.get(i)));
        }
        return stringhe;
    }
}
