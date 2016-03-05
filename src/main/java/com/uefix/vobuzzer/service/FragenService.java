package com.uefix.vobuzzer.service;

import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.FragenKatalog;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Uefix on 23.02.2016.
 */
public class FragenService {

    public static final Logger LOG = Logger.getLogger(FragenService.class);

    private Random random = new Random();

    private LinkedList<Frage> zufaelligeFragen = new LinkedList<>();

    private FragenKatalog fragenKatalog;


    public FragenService(FragenKatalog fragenKatalog) {
        this.fragenKatalog = fragenKatalog;
    }


    private void initializeZufaelligeFragen() {
        LOG.info("Initialisiere zufaellige Fragen...");
        Collection<Frage> fragen = fragenKatalog.getFragen().values();
        for (Frage frage : fragen) {
            int einfuegeIndex = zufaelligeFragen.isEmpty() ? 0 : random.nextInt(zufaelligeFragen.size());
            zufaelligeFragen.add(einfuegeIndex, frage);
        }
    }


    public Frage getZufaelligeFrage() {
        if (zufaelligeFragen.isEmpty()) {
            initializeZufaelligeFragen();
        }
        Frage frage = zufaelligeFragen.removeFirst();
        frage.setZuletztGestellt(erzeugeJetztZeitstempel());

        LOG.debug("Zufaellige Frage: " + frage.getText());

        return frage;
    }


    Timestamp erzeugeJetztZeitstempel() {
        return new Timestamp(System.currentTimeMillis());
    }
}
