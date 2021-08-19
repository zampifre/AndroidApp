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
import java.util.Date;
import java.util.Locale;

/** Classe per implementare l'AdapterRecycler e il MyViewHolder, permette di prelevare i dati,
 * posizionari all'interno di un layout specifico, rappresentarli all'interno del loro Contenitore
 * e visualizzarli nel RecyclerView.
 * **/

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id_array, descrizione_array, ora, data;

    Animation animazione;

    //Costruttore che prende in imput un'attività, un Content e le strutture dati
    AdapterRecycler(Activity activity, Context context, ArrayList id, ArrayList descrizione, ArrayList ora, ArrayList data){
        this.activity = activity;
        this.context = context;
        this.id_array = id;
        this.descrizione_array = descrizione;
        this.ora = ora;
        this.data = data;
    }

    //Classe per la creazione del MyViewHolder, ovvero del "contenitore" che conterrà i dati
    //alla visualizzazione di essi
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //TextView per visualizzare la descrizione, l'ora e la data di ogni attività
        TextView idt, descrizione2, ora2, data2;
        //CheckBox per spuntare un'attività terminata
        CheckBox checkBox;
        //LinearLayout per settare l'animazione
        LinearLayout mainLayout;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //idt = itemView.findViewById(R.id.id);
            //Linker tra gli oggetti Java e i componenti XML
            descrizione2 = itemView.findViewById(R.id.descrizione);
            ora2 = itemView.findViewById(R.id.ora);
            data2 = itemView.findViewById(R.id.data);
            checkBox = itemView.findViewById(R.id.checkBox);
            mainLayout = itemView.findViewById(R.id.mainlayout);
            animazione = AnimationUtils.loadAnimation(context, R.anim.mia_animazione);
            mainLayout.setAnimation(animazione);
        }
    }

    //Metodo OnCreate che permette di creare un layout a partire dal MyViewHolder
    //linkandolo con il layout XML assegnato a ogni singola attività.
    @NonNull
    @Override
    public AdapterRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Settaggio del layout da "singola_priorita.xml"
        View view = inflater.inflate(R.layout.singola_priorita, parent, false);
        //ritorna un elemento di tipo MyViewHolder
        return new MyViewHolder(view);
    }

    //Collegamento tra il MyViewHolder e l'AdapterRecycler (RecyclerView); non è altro che il terzo
    // e ultimo step della visualizzazione; permette di unire i dati alla componente grafica
    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.idt.setText(String.valueOf(id_array.get(position)));
        //per prima cosa ordino le attività per data
        bubbleSort();

        //setto i dati delle varie componenti prelevandoli dagli array specifici
        holder.descrizione2.setText(String.valueOf(descrizione_array.get(position)));
        holder.ora2.setText(String.valueOf(ora.get(position)));
        holder.data2.setText(String.valueOf(data.get(position)));
        //metodo per la CheckBox: se un'attività viene settata come Checked allora viene cancellata
        //dal DB e rimossa dal RecyclerView
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //se è checked allora cancella
                if(compoundButton.isChecked()){
                    DbPriorità db = new DbPriorità(context);
                    db.cancellaRiga((String) id_array.get(position));
                    activity.recreate();
                } else{
                    ;
                }
            }
        });
        //metodo OnCLick per aprire l'activity AggiornaActivity per modificare i dati di un'attività
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent per invocare la classe passando i dati specifici di quell'attività
                Intent intent = new Intent(context, AggiornaActivity.class);
                intent.putExtra("id", String.valueOf(id_array.get(position)));
                intent.putExtra("descrizione", String.valueOf(descrizione_array.get(position)));
                intent.putExtra("ora", String.valueOf(ora.get(position)));
                intent.putExtra("data", String.valueOf(data.get(position)));
                //codice di richiesta per lanciare l'attività
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    //metodo per ritornare il numero di elementi degli array
    @Override
    public int getItemCount() {
        return id_array.size();
    }

    //BubbleSort applicato a quattro Array paralleli: id, data, descrizione e ora
    //Ordinamento dati per data e ora
    public void bubbleSort() {
        //per prima cosa devo convertire l'array delle date (sottoforma di stringhe) in formato Date
        ArrayList <Date> tmp = daStringADate(data);
        ArrayList <Date> tmp_ore = daStringAOre(ora);
        boolean sorted = false;
        Object temp2;
        Date temp1;
        String temp3;
        Date temp4;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < tmp.size() - 1; i++) {
                if (tmp.get(i).compareTo(tmp.get(i + 1)) > 0){
                    temp1 = tmp.get(i);
                    temp2 = id_array.get(i);
                    temp3 = (String) descrizione_array.get(i);
                    temp4 = tmp_ore.get(i);
                    tmp.set(i, tmp.get(i + 1));
                    id_array.set(i, id_array.get(i + 1));
                    descrizione_array.set(i, descrizione_array.get(i+1));
                    tmp_ore.set(i, tmp_ore.get(i+1));
                    tmp.set((i + 1), temp1);
                    id_array.set(i+1, temp2);
                    descrizione_array.set(i + 1, temp3);
                    tmp_ore.set(i+1, temp4);

                    sorted = false;
                } else if(tmp.get(i).compareTo(tmp.get(i + 1)) == 0){
                    if(tmp_ore.get(i).compareTo(tmp_ore.get(i + 1)) > 0){
                        temp1 = tmp.get(i);
                        temp2 = id_array.get(i);
                        temp3 = (String) descrizione_array.get(i);
                        temp4 = tmp_ore.get(i);
                        tmp.set(i, tmp.get(i + 1));
                        id_array.set(i, id_array.get(i + 1));
                        descrizione_array.set(i, descrizione_array.get(i+1));
                        tmp_ore.set(i, tmp_ore.get(i+1));
                        tmp.set((i + 1), temp1);
                        id_array.set(i+1, temp2);
                        descrizione_array.set(i + 1, temp3);
                        tmp_ore.set(i+1, temp4);

                        sorted = false;
                    }
                }
            }
        }
        //devo riconvertire l'array di Date in stringhe
        data = daDateAString(tmp);
        //devo riconvertire l'array di Ore in Stringhe
        ora = daDateAOre(tmp_ore);
    }

    //metodo che converte da Stringa a Date ogni singolo elemento dell'array e ritorna
    //un ArrayList <Date>
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

    public ArrayList <Date> daStringAOre(ArrayList ore){
        ArrayList <Date> res = new ArrayList<Date>();
        for(int i = 0; i < ore.size(); ++i){
            SimpleDateFormat f = new SimpleDateFormat("hh:mm aa");
            try {
                res.add(f.parse(String.valueOf(ore.get(i))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    //metodo che converte da Date a Stringa per far tornare un ArrayList come quello originario
    //una volta che è stato ordinato
    public ArrayList daDateAString(ArrayList <Date> data){
        ArrayList stringhe = new ArrayList();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for(int i = 0; i < data.size(); ++i){
            stringhe.add(df.format(data.get(i)));
        }
        return stringhe;
    }

    public ArrayList daDateAOre(ArrayList <Date> ore){
        ArrayList stringhe = new ArrayList();
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        for(int i = 0; i < ore.size(); ++i){
            stringhe.add(df.format(ore.get(i)));
        }
        return stringhe;
    }

}
