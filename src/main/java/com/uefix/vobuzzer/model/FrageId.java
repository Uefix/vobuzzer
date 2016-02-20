package com.uefix.vobuzzer.model;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FrageId {

    private int idx = -1;
    private FragenKategorie kategorie = null;

    public FrageId(int idx, FragenKategorie kategorie) {
        this.idx = idx;
        this.kategorie = kategorie;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

        if (idx != frageId.idx) return false;
        return kategorie == frageId.kategorie;

    }

    @Override
    public int hashCode() {
        int result = idx;
        result = 31 * result + (kategorie != null ? kategorie.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "FrageId{" +
                "idx=" + idx +
                ", kategorie=" + kategorie +
                '}';
    }
}
