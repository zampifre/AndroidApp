package com.example.gestionale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbPriorità extends SQLiteOpenHelper {

    private Context context;
    private static final String NOME_DATABASE = "Priorita.db";
    private static final int VERSIONE_DATABASE = 2;
    private static final String NOME_TABELLA = "MiePriorità";
    private static final String ID = "id";
    private static final String DESCRIZIONE = "task";
    private static final String ORA = "ora";
    private static final String DATA = "data";

    DbPriorità(@Nullable Context context) {
        super(context, NOME_DATABASE, null, VERSIONE_DATABASE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOME_TABELLA +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRIZIONE + " TEXT, " + ORA + " TEXT, " + DATA + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELLA);
        onCreate(db);
    }

    void aggiungiPriorita(String Descrizione, String ora, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DESCRIZIONE, Descrizione);
        cv.put(ORA, ora);
        cv.put(DATA, data);
        long result = db.insert(NOME_TABELLA, null, cv);
        if(result == -1){
            Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aggiunto Correttamente!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor leggiTutto(){
        String query = "SELECT * FROM " + NOME_TABELLA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void aggiornaDati(String id, String descrizione, String ora, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DESCRIZIONE, descrizione);
        cv.put(ORA, ora);
        cv.put(DATA, data);

        long res = db.update(NOME_TABELLA, cv, "id=?", new String[]{id});
        if(res == -1){
            Toast.makeText(context, "Errore nell'aggiornamento dei dati", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Aggiornamento effettuato!", Toast.LENGTH_SHORT).show();
        }
    }

    void cancellaRiga(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(NOME_TABELLA, "id=?", new String[]{id});
        if(res == -1){
            Toast.makeText(context, "Errore nel cancellare l'attività.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cancellato Correttamente!", Toast.LENGTH_SHORT).show();
        }
    }

    void cancellaTutto(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + NOME_TABELLA);
    }
}
