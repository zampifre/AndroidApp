package com.example.gestionale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Creazione della classe Attività contenente id, descrizione, data, ora **/

public class Attivita implements Comparable<Attivita> {

   private int id;
   private String descrizione;
   private String data;
   private String ora;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    //Metodo per prelevare la data di un'attività
    public Date dammiData(String data) {
        Date res = null;
        try {
            res = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    //Implementazione del metodo compareTo
    @Override
    public int compareTo(Attivita attivita) {
        if (this.dammiData(this.data).compareTo(attivita.dammiData(attivita.data)) > 0) {
            return 1;
        } else if (this.dammiData(this.data).compareTo(attivita.dammiData(attivita.data)) < 0) {
            return -1;
        } else {
            if (this.ora.compareTo(attivita.ora) > 0) {
                return 1;
            } else if (this.ora.compareTo(attivita.ora) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
