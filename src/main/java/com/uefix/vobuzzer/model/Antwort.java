package com.uefix.vobuzzer.model;

/**
 * Created by Uefix on 20.02.2016.
 */
public class Antwort {

    private String text;
    private boolean richtig;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRichtig() {
        return richtig;
    }

    public void setRichtig(boolean richtig) {
        this.richtig = richtig;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Antwort antwort = (Antwort) o;

        if (richtig != antwort.richtig) return false;
        return !(text != null ? !text.equals(antwort.text) : antwort.text != null);

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (richtig ? 1 : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Antwort{" +
                "text='" + text + '\'' +
                ", richtig=" + richtig +
                '}';
    }
}
