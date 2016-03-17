package com.uefix.vobuzzer.service;

import com.uefix.vobuzzer.excel.FragenKatalogExcelLoader;
import com.uefix.vobuzzer.excel.exception.FragenKatalogLoaderException;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Uefix on 23.02.2016.
 */
@Named
public class FragenService {

    public static final Logger LOG = Logger.getLogger(FragenService.class);

    @Inject
    private FragenKatalogExcelLoader excelLoader;


    private Random random = new Random();

    private LinkedList<Frage> zufaelligeFragen = new LinkedList<>();

    private FragenKatalog fragenKatalog;


    public void loadFragenKatalog(String pfadZuExcel) {
        LOG.info("Lade Fragenkatalog-Excel '" + pfadZuExcel + "'...");
        File file = new File(pfadZuExcel);
        if (!file.exists()) {
            throw new IllegalArgumentException("Datei '" + file.getAbsolutePath() + "' existiert nicht.");
        }

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            this.fragenKatalog = excelLoader.loadFragen(is, FragenKategorie.values());
        } catch (IOException ioe) {
            throw new IllegalStateException("Fehler beim Lesen der Datei '" + file.getAbsolutePath() + "'.", ioe);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


    public void checkExcel(String pfadZuExcel)  {
        LOG.info("Pruefe Fragenkatalog-Excel '" + pfadZuExcel + "'...");
        File file = new File(pfadZuExcel);
        if (!file.exists()) {
            LOG.error("Datei '" + file.getAbsolutePath() + "' existiert nicht.");
            return;
        }

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            excelLoader.loadFragen(is, FragenKategorie.values());
        } catch (IOException ioe) {
            LOG.error("Fehler beim Lesen der Datei '" + file.getAbsolutePath() + "'.", ioe);
        } catch (FragenKatalogLoaderException fkle) {
            LOG.error("\nACHTUNG:\nExcel ist schrott:\n" + fkle.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
        }
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
