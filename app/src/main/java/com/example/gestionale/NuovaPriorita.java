package com.example.gestionale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Classe che implementa l'activity "Nuova Priorità";
 * All'interno, la classe sviluppa inoltre un TimePicker per
 * l'inserimento dell'orario e un DatePicker per aggiungere la data di una priorità.
 * **/

public class NuovaPriorita extends AppCompatActivity{

    //TV che riceverà l'inserimento dell'ora
    TextView orario;
    //Variabili per il TimePicker
    int tore, tminuti, tsecondi;
    //TV che riceverà l'inserimento della data
    TextView seleziona;
    //EditText per l'inserimento di una descrizione
    EditText descrizione;
    //Bottone per salvare i dati sul DB
    Button salva;
    //Attributo DatePicker
    DatePickerDialog.OnDateSetListener setListener;

    public static NuovaPriorita nuovaPriorita(){
        return new NuovaPriorita();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Settaggio del Layout al relativo file XML
        setContentView(R.layout.nuova_priorita);
        //Linker tra Oggetti e Compomenti XML
        orario = findViewById(R.id.tvora);
        seleziona = findViewById(R.id.tvdata);

        //Metodo OnCLickListener per far si che dopo il click sopra
        //alla TextVIew orario, si apra il Picker per l'inserimento
        orario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NuovaPriorita.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            //Metodo per settare il tipo di orario (12H o 24H)
                            public void onTimeSet(TimePicker timePicker, int ore, int minuti) {
                                tore = ore;
                                tminuti = minuti;
                                String time = tore + ":" + tminuti;
                                SimpleDateFormat t24 = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = t24.parse(time);
                                    SimpleDateFormat t12 = new SimpleDateFormat(
                                           "hh:mm aa"
                                    );
                                    orario.setText(t12.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tore, tminuti);
                timePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int anno = calendar.get(Calendar.YEAR);
        final int mese = calendar.get(Calendar.MONTH);
        final int giorno = calendar.get(Calendar.DAY_OF_MONTH);

        //Metodo OnClick per far si che clickando sulla TextView data si apra il Date Picker
        seleziona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NuovaPriorita.this, android.R.style.Theme_Holo_Light_DarkActionBar, setListener, anno, mese, giorno);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                mese = mese + 1;
                String data = giorno + "/" + mese + "/" + anno;
                seleziona.setText(data);
            }
        };

        descrizione = findViewById(R.id.descrizione);
        salva = findViewById(R.id.salva);
        //Metodo OnCLick per salvare i dati inseriti sul DB
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creazione di un nuovo DataBase
                DbPriorità db = new DbPriorità(NuovaPriorita.this);
                db.aggiungiPriorita(descrizione.getText().toString().trim(), orario.getText().toString().trim(), seleziona.getText().toString().trim());
            }
        });
    }

}
