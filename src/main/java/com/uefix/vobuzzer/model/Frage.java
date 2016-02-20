package com.uefix.vobuzzer.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uefix on 20.02.2016.
 */
public class Frage {

    private FrageId id;
    private String text;
    private FragenKategorie kategorie;
    private Timestamp zuletztGestellt;
    private List<Antwort> antworten = new ArrayList<>();

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

    public FragenKategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(FragenKategorie kategorie) {
        this.kategorie = kategorie;
    }

    public Timestamp getZuletztGestellt() {
        return zuletztGestellt;
    }

    public void setZuletztGestellt(Timestamp zuletztGestellt) {
        this.zuletztGestellt = zuletztGestellt;
    }

    public List<Antwort> getAntworten() {
        return antworten;
    }

    public void setAntworten(List<Antwort> antworten) {
        this.antworten = antworten;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frage frage = (Frage) o;

        if (id != null ? !id.equals(frage.id) : frage.id != null) return false;
        if (text != null ? !text.equals(frage.text) : frage.text != null) return false;
        if (kategorie != frage.kategorie) return false;
        if (zuletztGestellt != null ? !zuletztGestellt.equals(frage.zuletztGestellt) : frage.zuletztGestellt != null)
            return false;
        return !(antworten != null ? !antworten.equals(frage.antworten) : frage.antworten != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (kategorie != null ? kategorie.hashCode() : 0);
        result = 31 * result + (zuletztGestellt != null ? zuletztGestellt.hashCode() : 0);
        result = 31 * result + (antworten != null ? antworten.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Frage{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", kategorie=" + kategorie +
                ", zuletztGestellt=" + zuletztGestellt +
                ", antworten=" + antworten +
                '}';
    }
}
