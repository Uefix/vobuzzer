package com.uefix.vobuzzer.gui.javafx;

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
import javax.inject.Named;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class StartScreen {

    public static final Logger LOG = Logger.getLogger(StartScreen.class);

    private Scene scene;

    protected Label anzahlSpieleLabel;

    public void initialize() {
        anzahlSpieleLabel = new Label();
        anzahlSpieleLabel.setId("startpane-anzahlspiele");
        anzahlSpieleLabel.setText("7");
        anzahlSpieleLabel.setOnMouseClicked(event -> LOG.debug("ich wurde geklickt!"));

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
