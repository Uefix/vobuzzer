package com.uefix.vobuzzer.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalog {

    private Map<FrageId, Frage> fragen = new HashMap<>();

    public Map<FrageId, Frage> getFragen() {
        return fragen;
    }

    public void setFragen(Map<FrageId, Frage> fragen) {
        this.fragen = fragen;
    }


    public Frage getFrage(FrageId frageId) {
        return null;
    }
}
