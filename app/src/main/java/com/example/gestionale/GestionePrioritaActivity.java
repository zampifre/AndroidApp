package com.example.gestionale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class GestionePrioritaActivity extends AppCompatActivity {

    FloatingActionButton fltbutton;
    RecyclerView recyclerView;
    ImageView empty;

    DbPriorità db;
    ArrayList<String> id, descrizione, ora, data;
    AdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionepriorita);

        recyclerView = findViewById(R.id.recyclerview);
        fltbutton = findViewById(R.id.fltbutton);
        empty = findViewById(R.id.empty);
        fltbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestionePrioritaActivity.this, NuovaPriorita.class);
                startActivity(intent);
            }
        });

        db = new DbPriorità(GestionePrioritaActivity.this);
        id = new ArrayList<String>();
        descrizione = new ArrayList<String>();
        ora = new ArrayList<String>();
        data = new ArrayList<String>();


        salvaDatiArray();

        adapterRecycler = new AdapterRecycler(GestionePrioritaActivity.this, this, id, descrizione, ora, data);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(GestionePrioritaActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void salvaDatiArray(){
        Cursor cursor = db.leggiTutto();
        if(cursor.getCount() == 0){
            empty.setVisibility(View.VISIBLE);
        } else {
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                descrizione.add(cursor.getString(1));
                ora.add(cursor.getString(2));
                data.add(cursor.getString(3));
            }
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mio_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cancel_button){
            confermaDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confermaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancellare tutto?");
        builder.setMessage("Confermi di voler cancellare tutti i dati?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbPriorità db = new DbPriorità(GestionePrioritaActivity.this);
                db.cancellaTutto();
                Intent intent = new Intent(GestionePrioritaActivity.this, GestionePrioritaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
