package com.uefix.vobuzzer.model.observable;

import com.uefix.vobuzzer.model.AntwortSlot;
import com.uefix.vobuzzer.model.Frage;

import javax.inject.Named;

/**
 * Created by Uefix on 17.03.2016.
 */
@Named
public class SpielSession extends ObservableModel<SpielSession.Event> {

    public enum EventType {
        NEUE_FRAGE,
        ANTWORT_AUSGEWAEHLT,
        FRAGE_VERWORFEN,
        ANTWORT_PRUEFEN
    }


    public static class Event implements ModelEvent {
        private final EventType type;
        private String neueFrage;
        private AntwortSlot ausgewaehlteAntwort;

        public Event(EventType type) {
            this.type = type;
        }

        public EventType getType() {
            return type;
        }

        public String getNeueFrage() {
            return neueFrage;
        }

        public AntwortSlot getAusgewaehlteAntwort() {
            return ausgewaehlteAntwort;
        }
    }


    private int anzahlGestellterFragen;

    private Frage frage;


    public Frage getFrage() {
        return frage;
    }

    public void setFrage(Frage frage) {
        this.frage = frage;
    }

    public int getAnzahlGestellterFragen() {
        return anzahlGestellterFragen;
    }

    public void setAnzahlGestellterFragen(int anzahlGestellterFragen) {
        this.anzahlGestellterFragen = anzahlGestellterFragen;
    }

    public void reset() {
        anzahlGestellterFragen = 0;
    }
}
