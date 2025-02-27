package com.uefix.vobuzzer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalog {

    private Map<FrageId, Frage> fragen = new HashMap<>();

    public Map<FrageId, Frage> getFragen() {
        return fragen;
    }

    public void addFrage(Frage frage) {
        fragen.put(frage.getId(), frage);
    }

    public Frage getFrage(FrageId frageId) {
        return fragen.get(frageId);
    }
}
