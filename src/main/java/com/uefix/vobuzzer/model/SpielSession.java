package com.uefix.vobuzzer.model;

import javax.inject.Named;

/**
 * Created by Uefix on 17.03.2016.
 */
@Named
public class SpielSession {

    private boolean selektionMoeglich;

    private int anzahlGestellterFragen;

    private Frage frage;

    private AntwortSlot antwort;


    public Frage getFrage() {
        return frage;
    }

    public void setFrage(Frage frage) {
        this.frage = frage;
    }

    public AntwortSlot getAntwort() {
        return antwort;
    }

    public void setAntwort(AntwortSlot antwort) {
        this.antwort = antwort;
    }


    public void inkrementiereAnzahlGestellterFragen() {
        anzahlGestellterFragen++;
    }

    public int getAnzahlGestellterFragen() {
        return anzahlGestellterFragen;
    }

    public boolean isSelektionMoeglich() {
        return selektionMoeglich;
    }

    public void setSelektionMoeglich(boolean selektionMoeglich) {
        this.selektionMoeglich = selektionMoeglich;
    }


    public void reset() {
        selektionMoeglich = true;
        anzahlGestellterFragen = 0;
        antwort = null;
        frage = null;
    }
}
