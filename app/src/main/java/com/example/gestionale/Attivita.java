package com.example.gestionale;

public class Attivita {

   private int id;
   private int stato;
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

    public boolean getStato() {
       if(this.stato == 0){
           return false;
       } else {
           return true;
       }
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
