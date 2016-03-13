package com.uefix.vobuzzer;

import com.uefix.vobuzzer.gui.javafx.VOBuzzerGui;
import com.uefix.vobuzzer.service.FragenService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Uefix on 20.02.2016.
 */
@Named
public class VOBuzzerMain extends Application {

    private static Logger LOG = Logger.getLogger(VOBuzzerMain.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
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
}
