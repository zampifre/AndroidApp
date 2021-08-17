package com.example.gestionale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BubbleSort {

    public void bubbleSort(ArrayList array_data, ArrayList array_id, ArrayList array_descrizione, ArrayList array_ora) {
        ArrayList <Date> tmp = fromStringToDate(array_data);
        boolean sorted = false;
        Object temp2;
        Date temp1;
        String temp3;
        String temp4;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < tmp.size() - 1; i++) {
                if (tmp.get(i).compareTo(tmp.get(i + 1)) > 0){
                    temp1 = tmp.get(i);
                    temp2 = array_id.get(i);
                    temp3 = (String) array_descrizione.get(i);
                    temp4 = (String) array_ora.get(i);
                    tmp.set(i, tmp.get(i + 1));
                    array_id.set(i, array_id.get(i + 1));
                    array_descrizione.set(i, array_descrizione.get(i+1));
                    array_ora.set(i, array_ora.get(i+1));
                    tmp.set((i + 1), temp1);
                    array_id.set(i+1, temp2);
                    array_descrizione.set(i + 1, temp3);
                    array_ora.set(i+1,temp4);

                    sorted = false;
                }
            }
        }
        array_data = tmp;
    }

    public ArrayList <Date> fromStringToDate(ArrayList data){
        ArrayList <Date> res = new ArrayList<Date>();
        for(int i = 0; i < data.size(); ++i){
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy", Locale.ITALIAN);
            try {
                res.add(f.parse(String.valueOf(data.get(i))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
