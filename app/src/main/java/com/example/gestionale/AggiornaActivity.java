package com.example.gestionale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/** Classe Aggiorna Priorità, creata nel momento in cui viene selezionata
 * un'attività specifica; permette di modificare gli attributi dell'Attivitò e aggiornare
 * i dati reciproci sul DB
 * **/

public class AggiornaActivity extends AppCompatActivity {

    //Componenti del Layout
    TextView tvdata2, tvora2;
    EditText descrizione2;
    Button salva2, cancella;
    int t2ore, t2minuti;
    String id, descrizione, ora, data;
    DatePickerDialog.OnDateSetListener slistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Settaggio del relativo Layout XML
        setContentView(R.layout.activity_aggiorna);

        //Linker tra Oggetti Java e componenti XML
        tvdata2 = findViewById(R.id.tvdata2);
        tvora2 = findViewById(R.id.tvora2);
        descrizione2 = findViewById(R.id.descrizione2);
        salva2 = findViewById(R.id.salva2);
        cancella = findViewById(R.id.cancella);

        getAndSetIntentData();

        //Creazione dell'action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(descrizione);
        }

        //Crazione dello stesso TimePicker della classe NuovaPriorà, verrà usato
        //per modificare e quindi inserire i nuovi dati sull'orario
        tvora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AggiornaActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int ore, int minuti) {
                                t2ore = ore;
                                t2minuti = minuti;
                                String time = t2ore + ":" + t2minuti;
                                SimpleDateFormat t24 = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = t24.parse(time);
                                    SimpleDateFormat t12 = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    tvora2.setText(t12.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2ore, t2minuti);
                timePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int anno = calendar.get(Calendar.YEAR);
        final int mese = calendar.get(Calendar.MONTH);
        final int giorno = calendar.get(Calendar.DAY_OF_MONTH);

        //Creazione dello stesso DatePicker della classe NuovaPriorità per modificare e re-inserire
        //la data dell'attività
        tvdata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AggiornaActivity.this, android.R.style.Theme_Holo_Light_DarkActionBar, slistener, anno, mese, giorno);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        slistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                mese = mese + 1;
                String data = giorno + "/" + mese + "/" + anno;
                tvdata2.setText(data);
            }
        };

        //Metodo OnClick sul bottone AGGIORNA, permette di modificare i dati appena inseriti
        //al posto dei reciproci presenti in precedenza sul DB
        salva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbPriorità mydb = new DbPriorità(AggiornaActivity.this);
                descrizione = descrizione2.getText().toString().trim();
                ora = tvora2.getText().toString().trim();
                data = tvdata2.getText().toString().trim();
                mydb.aggiornaDati(id, descrizione, ora, data);
            }
        });

        //Metodo OnClick sul bottone CANCELLA, permette di cancellare la singola
        //attività dal DB
        cancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Invocazione del ConfirmDialog di richiesta conferma
                confermaDialog();
            }
        });
    }

    //Metodo che permette di recuperare i dati dell'attività scelta e renderli visibili
    //nel momento in cui si vogliano modificare; permette di visualizzare i dati che erano
    //stati settati in precedenza
    void getAndSetIntentData(){
        //controllo se ci sono dati relativi all'attività scelta
        if(getIntent().hasExtra("id") && getIntent().hasExtra("descrizione") && getIntent().hasExtra("ora") &&
        getIntent().hasExtra("data")) {
            //get dei dati dalle colonne
            id = getIntent().getStringExtra("id");
            descrizione = getIntent().getStringExtra("descrizione");
            ora = getIntent().getStringExtra("ora");
            data = getIntent().getStringExtra("data");

            //Set dei dati prelevati dalla TextView di questa Activity,
            //dal DatePicker e il TimePicker
            descrizione2.setText(descrizione);
            tvora2.setText(ora);
            tvdata2.setText(data);
        } else {
            //In caso contrario
            Toast.makeText(this, "Non ci sono dati", Toast.LENGTH_SHORT).show();
        }
    }

    //Creazione del ConfirmDialog usato per richiedere se si vuole cancellare
    //l'attività selezionata con tale descrizione
    void confermaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancellare " + descrizione + " ?");
        builder.setMessage("Confermi di voler cancellare?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            //OnClick (positivo - Cancella)
            public void onClick(DialogInterface dialogInterface, int i) {
                DbPriorità db = new DbPriorità(AggiornaActivity.this);
                //chiamata al metodo cancella riga del DB
                db.cancellaRiga(id);
                finish();
            }
        });

        //OnClick (negativo - non fa nulla)
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}