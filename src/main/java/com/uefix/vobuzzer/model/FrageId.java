package com.uefix.vobuzzer.model;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FrageId {

    private int index = -1;
    private FragenKategorie kategorie = null;

    public FrageId(int index, FragenKategorie kategorie) {
        this.index = index;
        this.kategorie = kategorie;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public FragenKategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(FragenKategorie kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrageId frageId = (FrageId) o;

        if (index != frageId.index) return false;
        return kategorie == frageId.kategorie;

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (kategorie != null ? kategorie.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "FrageId{" +
                "index=" + index +
                ", kategorie=" + kategorie +
                '}';
    }
}
