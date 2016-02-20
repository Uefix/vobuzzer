package com.uefix.vobuzzer.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Uefix on 20.02.2016.
 */
public class Frage {

    private FrageId id;
    private String text;
    private Timestamp zuletztGestellt;
    private Map<AntwortSlot, Antwort> antworten = new HashMap<>();
    private AntwortSlot richtigeAntwort;


    public FrageId getId() {
        return id;
    }

    public void setId(FrageId id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getZuletztGestellt() {
        return zuletztGestellt;
    }

    public void setZuletztGestellt(Timestamp zuletztGestellt) {
        this.zuletztGestellt = zuletztGestellt;
    }


    public Map<AntwortSlot, Antwort> getAntworten() {
        return antworten;
    }

    public void setAntworten(Map<AntwortSlot, Antwort> antworten) {
        this.antworten = antworten;
    }

    public void addAntwort(Antwort antwort) {
        antworten.put(antwort.getSlot(), antwort);
        if (antwort.isRichtig()) {
            richtigeAntwort = antwort.getSlot();
        }
    }



    public AntwortSlot getRichtigeAntwort() {
        return richtigeAntwort;
    }

    public void setRichtigeAntwort(AntwortSlot richtigeAntwort) {
        this.richtigeAntwort = richtigeAntwort;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frage frage = (Frage) o;

        if (id != null ? !id.equals(frage.id) : frage.id != null) return false;
        if (text != null ? !text.equals(frage.text) : frage.text != null) return false;
        if (zuletztGestellt != null ? !zuletztGestellt.equals(frage.zuletztGestellt) : frage.zuletztGestellt != null)
            return false;
        if (antworten != null ? !antworten.equals(frage.antworten) : frage.antworten != null) return false;
        return richtigeAntwort == frage.richtigeAntwort;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (zuletztGestellt != null ? zuletztGestellt.hashCode() : 0);
        result = 31 * result + (antworten != null ? antworten.hashCode() : 0);
        result = 31 * result + (richtigeAntwort != null ? richtigeAntwort.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Frage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", zuletztGestellt=" + zuletztGestellt +
                ", antworten=" + antworten +
                ", richtigeAntwort=" + richtigeAntwort +
                '}';
    }
}
