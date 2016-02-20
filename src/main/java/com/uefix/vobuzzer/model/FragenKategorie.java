package com.uefix.vobuzzer.model;

/**
 * Created by Uefix on 20.02.2016.
 */
public enum FragenKategorie {

    ALLGEMEIN("Allgemein"),
    GS1("GS1"),
    GS2("GS2"),
    GS4("GS4"),
    GS5("GS5"),
    GS6("GS6"),
    GS7("GS7"),
    GS8("GS8"),
    GS10("GS10"),
    GS20("GS20"),
    GS21("GS21"),
    GS23("GS23"),
    GS26("GS26"),
    GS31("GS31"),
    GS32("GS32"),
    GS33_35("G33-35"),
    GS40("GS40"),
    GS43("GS43"),
    GS44("GS44"),
    GS45("GS45"),
    GS46("GS46"),
    GS47("GS47"),
    GS48("GS48"),
    GS51("GS51"),
    GS54("GS54"),
    JUGEND("Jugend");



    private final String sheetName;

    private FragenKategorie(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }
}
