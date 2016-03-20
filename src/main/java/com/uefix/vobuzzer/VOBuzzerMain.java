package com.uefix.vobuzzer;

import com.uefix.vobuzzer.gui.VOBuzzerGui;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Created by Uefix on 20.02.2016.
 */
@Named
public class VOBuzzerMain extends Application {

    private static Logger LOG = Logger.getLogger(VOBuzzerMain.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        LOG.info("Starting...\n" + loadAsciiArt());

        applicationContext = new ClassPathXmlApplicationContext("vobuzzer-application-context.xml");

        VOBuzzerMain buzzerMain = applicationContext.getBean(VOBuzzerMain.class);
        buzzerMain.evaluateArguments(args);
    }


    @Inject
    private FragenService fragenService;


    private void evaluateArguments(String[] args) {
        if (args.length > 1 && args[0].equals("-checkexcel")) {
            fragenService.checkExcel(args[1]);
        } else if (args.length == 1) {
            fragenService.loadFragenKatalog(args[0]);
            Application.launch(args);
        } else {
            LOG.error("Unerwartete Anzahl an Parametern.");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VOBuzzerGui voBuzzerGui = applicationContext.getBean(VOBuzzerGui.class);
        voBuzzerGui.start(primaryStage);
    }


    private static String loadAsciiArt() {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new ClassPathResource("vobuzzer_asciiart.txt").getInputStream()));
            return IOUtils.toString(reader);
        } catch (IOException ioe) {
            LOG.error("Failed to load asciiart", ioe);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return "";
    }
}
