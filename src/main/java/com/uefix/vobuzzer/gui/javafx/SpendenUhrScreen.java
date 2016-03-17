package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.observable.ApplicationStateModel;
import com.uefix.vobuzzer.model.observable.RootEmModel;
import com.uefix.vobuzzer.model.observable.SpielStatistik;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
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
public class SpendenUhrScreen {

    public static final Logger LOG = Logger.getLogger(SpendenUhrScreen.class);

    @Inject
    private ApplicationStateModel applicationStateModel;

    @Inject
    private RootEmModel rootEmModel;

    @Inject
    private SpielStatistik spielStatistik;


    private Label anzahlSpieleLabel;

    private StackPane rootSpielPane;


    @PostConstruct
    public void initialize() {
        spielStatistik.addListener((model, event) -> {
            anzahlSpieleLabel.setText(String.valueOf(event.getAnzahlSpiele()));
        });

        rootEmModel.addListener((model, event) -> {
            rootSpielPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;", event.getNewRootEmValue()));
        });
    }


    public void setupGui() {
        anzahlSpieleLabel = new Label();
        anzahlSpieleLabel.setId("startpane-anzahlspiele");
        anzahlSpieleLabel.setText("keine");
        anzahlSpieleLabel.setOnMouseClicked(event ->
                        applicationStateModel.setNewState(ApplicationStateModel.State.SPIEL)
        );

        rootSpielPane = new StackPane();
        rootSpielPane.setId("root-startpane");
        rootSpielPane.getChildren().add(anzahlSpieleLabel);
    }


    public Parent getRoot() {
        return rootSpielPane;
    }
}
