package com.example.gestionale;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class GestionePrioritaActivity extends AppCompatActivity {

    FloatingActionButton fltbutton;
    RecyclerView recyclerView;

    DatabasePriorità db;
    ArrayList<String> id, descrizione;
    AdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionepriorita);

        recyclerView = findViewById(R.id.recyclerview);
        fltbutton = findViewById(R.id.fltbutton);
        fltbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestionePrioritaActivity.this, NuovaPriorita.class);
                startActivity(intent);
            }
        });

        db = new DatabasePriorità(GestionePrioritaActivity.this);
        id = new ArrayList<String>();
        descrizione = new ArrayList<String>();

        salvaDatiArray();

        adapterRecycler = new AdapterRecycler(GestionePrioritaActivity.this, id, descrizione);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(GestionePrioritaActivity.this));
    }

    void salvaDatiArray(){
        Cursor cursor = db.leggiTutto();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Non ci sono dati.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                descrizione.add(cursor.getString(1));
            }
        }
    }

}
