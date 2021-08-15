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

public class NuovaPriorita extends AppCompatActivity{

    TextView orario;
    int tore, tminuti, tsecondi;
    TextView seleziona;
    EditText descrizione;
    Button salva;
    DatePickerDialog.OnDateSetListener setListener;

    public static NuovaPriorita nuovaPriorita(){
        return new NuovaPriorita();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuova_priorita);

        orario = findViewById(R.id.tvora);
        seleziona = findViewById(R.id.tvdata);

        orario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NuovaPriorita.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
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
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbPriorità db = new DbPriorità(NuovaPriorita.this);
                db.aggiungiPriorita(descrizione.getText().toString().trim(), orario.getText().toString().trim(), seleziona.getText().toString().trim());
            }
        });
    }

}
