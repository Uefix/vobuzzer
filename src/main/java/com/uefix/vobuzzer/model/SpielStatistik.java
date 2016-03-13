package com.uefix.vobuzzer.model;

import javax.inject.Named;

/**
 * Created by Uefix on 20.02.2016.
 */
@Named
public class SpielStatistik extends ObservableModel<SpielStatistik.Event> {

    public static class Event implements ModelEvent{
        private int anzahlSpiele;

        public Event(int anzahlSpiele) {
            this.anzahlSpiele = anzahlSpiele;
        }

        public int getAnzahlSpiele() {
            return anzahlSpiele;
        }
    }


    private int anzahlSpiele;

    public int getAnzahlSpiele() {
        return anzahlSpiele;
    }

    public void setAnzahlSpiele(int anzahlSpiele) {
        this.anzahlSpiele = anzahlSpiele;
        notifyListeners(new Event(anzahlSpiele));
    }
}
