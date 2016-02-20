package com.uefix.vobuzzer.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uefix on 20.02.2016.
 */
public class Frage {

    private String text;
    private FragenKategorie kategorie;
    private Timestamp zuletztGestellt;
    private List<Antwort> antworten = new ArrayList<>();
}
