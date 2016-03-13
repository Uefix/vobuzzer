package com.uefix.vobuzzer;

import com.uefix.vobuzzer.excel.FragenKatalogExcelLoader;
import com.uefix.vobuzzer.exception.FragenKatalogLoaderException;
import com.uefix.vobuzzer.gui.javafx.VOBuzzerApplication;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;

/**
 * Created by Uefix on 20.02.2016.
 */
public class VOBuzzerMain {

    public static final Logger LOG = Logger.getLogger(VOBuzzerMain.class);

    private static VOBuzzerMain buzzerMain = new VOBuzzerMain();


    public static void main(String[] args) {


        if (args.length > 1 && args[0].equals("-checkexcel")) {
            buzzerMain.checkExcel(args[1]);
        } else if (args.length == 1) {
            buzzerMain.initialize(args);
        } else {
            LOG.error("Unerwartete Anzahl an Parametern.");
        }
    }


    private FragenKatalogExcelLoader excelLoader = new FragenKatalogExcelLoader();

    public static FragenKatalog fragenKatalog;

    private VOBuzzerApplication application = new VOBuzzerApplication();

    private ClassPathXmlApplicationContext applicationContext;


    private void initialize(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("vobuzzer-application-context.xml");

        String pfadZuExcel = args[0];
        fragenKatalog = loadFragenKatalog(pfadZuExcel);
        application.launchApplication(args);
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
