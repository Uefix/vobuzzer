package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.ApplicationStateMachine;
import com.uefix.vobuzzer.model.ModelListener;
import com.uefix.vobuzzer.model.ObservableModel;
import com.uefix.vobuzzer.model.SpielStatistik;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class StartScreen implements ModelListener<SpielStatistik.Event> {

    public static final Logger LOG = Logger.getLogger(StartScreen.class);

    @Inject
    private ApplicationStateMachine applicationStateMachine;

    @Inject
    private SpielStatistik spielStatistik;

    private Scene scene;

    private Label anzahlSpieleLabel;


    @PostConstruct
    public void initialize() {
        spielStatistik.addListener(this);
    }

    @Override
    public void onEvent(ObservableModel<SpielStatistik.Event> model, SpielStatistik.Event event) {
        anzahlSpieleLabel.setText(String.valueOf(event.getAnzahlSpiele()));
    }

    public void setupGui() {
        anzahlSpieleLabel = new Label();
        anzahlSpieleLabel.setId("startpane-anzahlspiele");
        anzahlSpieleLabel.setText("keine");
        anzahlSpieleLabel.setOnMouseClicked(event ->
                        applicationStateMachine.setNewState(ApplicationStateMachine.State.GAME)
        );

        final StackPane rootSpielPane = new StackPane();
        rootSpielPane.setId("root-startpane");
        rootSpielPane.getChildren().add(anzahlSpieleLabel);

        scene = new Scene(rootSpielPane);
        scene.getStylesheets().add("css/stylesheet.css");
        scene.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                        double rootEmValue = newSceneHeight.doubleValue() / 40;

                        DoubleProperty fontSize = new SimpleDoubleProperty(rootEmValue); // font size in pt
                        rootSpielPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;", fontSize));

//                        frageBox.onSceneHeightChanged(rootEmValue);
                    }
                }
        );
    }

    public Scene getScene() {
        return scene;
    }
}
