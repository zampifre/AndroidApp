package com.example.gestionale;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NuovaPriorita extends AppCompatActivity{

    EditText descrizione;
    Button salva;

    public static NuovaPriorita nuovaPriorita(){
        return new NuovaPriorita();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuova_priorita);

        descrizione = findViewById(R.id.descrizione);
        salva = findViewById(R.id.salva);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabasePriorità db = new DatabasePriorità(NuovaPriorita.this);
                db.aggiungiPriorita(descrizione.getText().toString().trim());
            }
        });

    }

}
