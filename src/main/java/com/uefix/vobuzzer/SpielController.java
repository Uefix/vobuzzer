package com.uefix.vobuzzer;

import com.uefix.vobuzzer.gui.SpielScreen;
import com.uefix.vobuzzer.model.AntwortSlot;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.observable.ApplicationStateModel;
import com.uefix.vobuzzer.model.SpielSession;
import com.uefix.vobuzzer.model.observable.SpielStatistik;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Uefix on 17.03.2016.
 */
@Named
public class SpielController {

    private static final int MAXIMALE_ANZAHL_SPIELE = 5;

    @Inject
    private ApplicationStateModel applicationStateModel;

    @Inject
    private SpielStatistik spielStatistik;

    @Inject
    private SpielScreen spielScreen;

    @Inject
    private FragenService fragenService;

    @Inject
    private SpielSession spielSession;


    public void neuesSpiel() {
        spielSession.reset();
        applicationStateModel.fireNewState(ApplicationStateModel.State.SPIEL);
        neueFrage();
    }


    public void neueFrage() {
        int anzahlGestellterFragen = spielSession.getAnzahlGestellterFragen();
        if (anzahlGestellterFragen == MAXIMALE_ANZAHL_SPIELE) {
            spielStatistik.inkrementiereAnzahlSpiele();
            applicationStateModel.fireNewState(ApplicationStateModel.State.SPENDENUHR);
        } else {
            Frage frage = fragenService.getZufaelligeFrage();
            spielSession.setFrage(frage);
            spielSession.setSelektionMoeglich(true);
            spielSession.setAntwort(null);
            spielScreen.setFrage(frage);
        }
    }

    public void antwortSelektiert(AntwortSlot antwortSlot) {

        if (spielSession.isSelektionMoeglich()) {
            Frage frage = spielSession.getFrage();
            AntwortSlot aktuelleAntwort = spielSession.getAntwort();

            if (aktuelleAntwort != antwortSlot) {
                spielSession.setAntwort(antwortSlot);
                spielScreen.renderSelektierteAntwort(antwortSlot);
            } else {
                AntwortSlot richtigeAntwort = frage.getRichtigeAntwort();
                if (richtigeAntwort != aktuelleAntwort) {
                    spielScreen.renderFalscheAntwort(aktuelleAntwort);
                }
                spielScreen.renderRichtigeAntwort(richtigeAntwort);
                spielSession.setSelektionMoeglich(false);
                int anzahlGestellterFragen = spielSession.getAnzahlGestellterFragen();
                anzahlGestellterFragen++;
                spielSession.setAnzahlGestellterFragen(anzahlGestellterFragen);

            }
        }
    }
}
