package com.uefix.vobuzzer.gui;

import com.uefix.vobuzzer.SpielController;
import com.uefix.vobuzzer.model.observable.RootEmModel;
import com.uefix.vobuzzer.model.observable.SpielStatistik;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;
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
    private SpielController spielController;

    @Inject
    private RootEmModel rootEmModel;

    @Inject
    private SpielStatistik spielStatistik;


    private Label anzahlSpieleLabel;

    private GridPane rootStartPane;


    @PostConstruct
    public void initialize() {
        spielStatistik.addListener((model, event) -> {
            anzahlSpieleLabel.setText(String.valueOf(event.getAnzahlSpiele()));
        });

        rootEmModel.addListener((model, event) -> {
            rootStartPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;", event.getNewRootEmValue()));
        });
    }


    public void initializeNodes() {
        anzahlSpieleLabel = new Label();
        anzahlSpieleLabel.setId("startpane-anzahlspiele");
        anzahlSpieleLabel.setText("keine");
        anzahlSpieleLabel.setOnMouseClicked(event ->
                        spielController.neuesSpiel()
        );

        rootStartPane = new GridPane();
        rootStartPane.setId("root-startpane");

        rootStartPane.add(new HBox(), 0, 0, 3, 1);
        rootStartPane.add(new HBox(), 0, 1, 1, 1);
        rootStartPane.add(anzahlSpieleLabel, 1, 1, 1, 1);
        rootStartPane.add(new HBox(), 2, 1, 1, 1);

        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();

        gridPaneBuilder.gridLinesVisible(false);
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(46).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.percentWidth(40).build(),
                columnConstraintsBuilder.percentWidth(14).build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.TOP).percentHeight(28).fillHeight(true).vgrow(Priority.ALWAYS).build(),
                rowConstraintsBuilder.percentHeight(72).build()
        );

        gridPaneBuilder.applyTo(rootStartPane);
    }


    public Parent getRoot() {
        return rootStartPane;
    }
}
