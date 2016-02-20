package com.uefix.vobuzzer;

import com.uefix.vobuzzer.excel.FragenKatalogExcelLoader;
import com.uefix.vobuzzer.exception.FragenKatalogLoaderException;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Uefix on 20.02.2016.
 */
public class VOBuzzerMain {

    public static final Logger LOG = Logger.getLogger(VOBuzzerMain.class);

    public static void main(String[] args) {
        VOBuzzerMain buzzerMain = new VOBuzzerMain();

        if (args.length > 1 && args[0].equals("-checkexcel")) {
            buzzerMain.checkExcel(args[1]);
        } else if (args.length == 1) {
            buzzerMain.initialize(args[0]);
        } else {
            LOG.error("Unerwartete Anzahl an Parametern.");
        }
    }


    private FragenKatalogExcelLoader excelLoader = new FragenKatalogExcelLoader();

    private void initialize(String pfadZuExcel) {
        FragenKatalog katalog = loadFragenKatalog(pfadZuExcel);

    }

    private FragenKatalog loadFragenKatalog(String pfadZuExcel) {
        LOG.info("Lade Fragenkatalog-Excel '" + pfadZuExcel + "'...");
        File file = new File(pfadZuExcel);
        if (!file.exists()) {
            throw new IllegalArgumentException("Datei '" + file.getAbsolutePath() + "' existiert nicht.");
        }

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return excelLoader.loadFragen(is, FragenKategorie.values());
        } catch (IOException ioe) {
            throw new IllegalStateException("Fehler beim Lesen der Datei '" + file.getAbsolutePath() + "'.", ioe);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private void checkExcel(String pfadZuExcel)  {
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

}
