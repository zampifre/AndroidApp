package com.example.gestionale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/** DataBase SQLite per memorizzare ogni singola attività:
 * L'attività verrà identificata da un id univoco (con autoincremento),
 * da una descrizione, da una data e un'orario (sotto forma di stringhe).
 * **/

public class DbPriorità extends SQLiteOpenHelper {

    private Context context;
    private static final String NOME_DATABASE = "Priorita.db";
    private static final int VERSIONE_DATABASE = 2;
    private static final String NOME_TABELLA = "MiePriorità";
    private static final String ID = "id";
    private static final String DESCRIZIONE = "task";
    private static final String ORA = "ora";
    private static final String DATA = "data";

    //Costruttore che prende in imput un Context
    DbPriorità(@Nullable Context context) {
        super(context, NOME_DATABASE, null, VERSIONE_DATABASE);
        this.context = context;
    }

    //Metodo OnCreate che esegue la query di creazione del DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOME_TABELLA +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRIZIONE + " TEXT, " + ORA + " TEXT, " + DATA + " TEXT);";
        db.execSQL(query);
    }

    //Metodo di aggiornamento del DB, nel caso di aggiornamento viene cancellata,
    //se esiste, la tabella e invocato nuovamente il metodo OnCreate
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELLA);
        onCreate(db);
    }

    //Metodo per aggiungere una priorità all'interno del DB
    void aggiungiPriorita(String Descrizione, String ora, String data){
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues per raccogliere e passare i dati
        ContentValues cv = new ContentValues();
        cv.put(DESCRIZIONE, Descrizione);
        cv.put(ORA, ora);
        cv.put(DATA, data);
        long result = db.insert(NOME_TABELLA, null, cv);
        //Controllo se l'inserimento è avvenuto correttamente oppure no
        if(result == -1){
            Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aggiunto Correttamente!", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo per leggere tutti i dati del DB: ritorna un oggetto Cursor con all'interno
    //il risultato della query SELECT ALL...
    Cursor leggiTutto(){
        String query = "SELECT * FROM " + NOME_TABELLA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Metodo che aggiorna un'attività già presente all'interno del DB sempre passando
    //un ContentValues
    void aggiornaDati(String id, String descrizione, String ora, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DESCRIZIONE, descrizione);
        cv.put(ORA, ora);
        cv.put(DATA, data);

        long res = db.update(NOME_TABELLA, cv, "id=?", new String[]{id});
        //Controllo dell'aggiornamento corretto
        if(res == -1){
            Toast.makeText(context, "Errore nell'aggiornamento dei dati", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Aggiornamento effettuato!", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo che cancella una Singola attività (da id ricevuto) dal DB
    void cancellaRiga(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        //Esecuzione della query per cancellare la riga corrispondente all'id preso come imput
        long res = db.delete(NOME_TABELLA, "id=?", new String[]{id});
        //Controllo
        if(res == -1){
            Toast.makeText(context, "Errore nel cancellare l'attività.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cancellato Correttamente!", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo per cancellare tutti i dati sul DB
    void cancellaTutto(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + NOME_TABELLA);
    }
}
