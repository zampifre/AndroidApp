package com.example.gestionale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabasePriorità extends SQLiteOpenHelper {

    private Context context;
    private static final String NOME_DATABASE = "ListaPriorita.db";
    private static final int VERSIONE_DATABASE = 1;
    private static final String NOME_TABELLA = "Mie_Priorita";
    private static final String ID = "id";
    private static final String DESCRIZIONE = "task";


    public DatabasePriorità(@Nullable Context context) {
        super(context, NOME_DATABASE, null, VERSIONE_DATABASE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOME_TABELLA +
                        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DESCRIZIONE + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELLA);
        onCreate(db);
    }

    void aggiungiPriorita(String Descrizione){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DESCRIZIONE, Descrizione);
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
}
