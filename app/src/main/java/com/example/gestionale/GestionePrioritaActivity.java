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

/** Home della funzionalità Gestione Priorità;
 *  All'interno ci sarà un RelativeLayout che contiene una RecyclerView
 *  nella quale verranno rappresentate le singole attività giornaliere.
 *  In fondo all'activity vi sarà un bottone per l'invocazione dell'activity "Nuova Priorità"
 *  **/

public class GestionePrioritaActivity extends AppCompatActivity {

    //Bottone per "Nuova Priorità"
    FloatingActionButton fltbutton;
    RecyclerView recyclerView;
    //Immagine che verrà visualizzata in caso di assenza dati
    ImageView empty;
    //DataBase SQLite
    DbPriorità db;
    //Array usati per il prelevamento dei dati per la visualizzazione
    ArrayList<String> id, descrizione, ora, data;
    AdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Creazione del layout dal file XML corrispondente
        setContentView(R.layout.activity_gestionepriorita);
        //Linker tra componenti XML e oggetti Java
        recyclerView = findViewById(R.id.recyclerview);
        fltbutton = findViewById(R.id.fltbutton);
        empty = findViewById(R.id.empty);

        fltbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Metodo OnClick del bottone per l'invocazione dell'activity "Nuova Priorità"
            public void onClick(View view) {
                Intent intent = new Intent(GestionePrioritaActivity.this, NuovaPriorita.class);
                startActivity(intent);
            }
        });

        //Creazione dei vari Array e del DB
        db = new DbPriorità(GestionePrioritaActivity.this);
        id = new ArrayList<String>();
        descrizione = new ArrayList<String>();
        ora = new ArrayList<String>();
        data = new ArrayList<String>();

        salvaDatiArray();

        //Linker tra i dati e il RecyclerView dell'activity corrente
        adapterRecycler = new AdapterRecycler(GestionePrioritaActivity.this, this, id, descrizione, ora, data);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(GestionePrioritaActivity.this));
    }

    //Metodo che controlla con quale codice di richiesta viene avviata l'activity:
    //nel caso in cui il codice sia 1 viene effettuato un refresh della pagina (per esempio
    //nel caso in cui si voglia eliminare un'attività o più).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //Metodo che scorrendo con un Cursore tutto il DataBase,
    //aggiunge man mano i dati sugli array
    void salvaDatiArray(){
        Cursor cursor = db.leggiTutto();
        //Se il cursore non ha trovato nulla setta a VISIBLE l'immagine relativa
        //a indicare il caricamento dei dati
        if(cursor.getCount() == 0){
            empty.setVisibility(View.VISIBLE);
        } else {
            //Finchè il cursore scorre, aggiungi i dati
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                descrizione.add(cursor.getString(1));
                ora.add(cursor.getString(2));
                data.add(cursor.getString(3));
            }
            empty.setVisibility(View.GONE);
        }
    }

    //Metodo per la creazione del menù con i tre puntini in alto
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mio_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Metodo per creare un ConfirmDialog nel caso in cui venga selezionato il menù
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cancel_button){
            confermaDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //Creazione di un ConfirmDialog per la richiesta di cancellazione di tutte le attivtà
    //presenti nel DB.
    void confermaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancellare tutto?");
        builder.setMessage("Confermi di voler cancellare tutti i dati?");
        //Listener sul click (positivo - Cancella Tutto)
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbPriorità db = new DbPriorità(GestionePrioritaActivity.this);
                //Invocazione del metodo per cancellare tutti i dati
                db.cancellaTutto();
                //Intent per richiamare e quindi ricreare l'actitivty corrente dopo la cancellazione.
                Intent intent = new Intent(GestionePrioritaActivity.this,
                        GestionePrioritaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Listener sul click (negativo - Non effettua nulla)
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
