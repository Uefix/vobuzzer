package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.VOBuzzerMain;
import com.uefix.vobuzzer.model.AntwortSlot;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.service.FragenService;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by Uefix on 20.02.2016.
 */
public class VOBuzzerApplication extends Application {


    public static final Logger LOG = Logger.getLogger(VOBuzzerApplication.class);

    private KonfigurationScreen konfigurationScreen;
    private StartScreen startScreen;
    private SpielScreen spielScreen;


    public void launchApplication(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        konfigurationScreen = new KonfigurationScreen();
        konfigurationScreen.initialize();

        startScreen = new StartScreen();
        startScreen.initialize();

        spielScreen = new SpielScreen();
        spielScreen.initialize();

        stage.setTitle("VOBuzzer");
        stage.setScene(konfigurationScreen.getScene());
//        stage.setFullScreen(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                LOG.info("Byebye.");
                System.exit(0);
            }
        });
        stage.show();
    }
}
