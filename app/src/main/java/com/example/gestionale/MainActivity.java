package com.example.gestionale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

/** Classe Padre nella quale avviene la scelta su una delle due funzionalità dell'app,
 *  Gestione Monetaria o Gestione Priorità
 *  **/

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGestionePriorita();
            }
        });
    }

    //Metodo che crea un Intent per invocare l'activity Gestione Priorità
    public void openGestionePriorita(){
        Intent intent = new Intent(this, GestionePrioritaActivity.class);
        startActivity(intent);
    }
}