package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.FragenKategorie;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Uefix on 13.03.2016.
 */
public class KonfigurationScreen {

    public static final Logger LOG = Logger.getLogger(StartScreen.class);

    private Scene scene;

    private Button startButton;


    public void initialize() {
        final GridPane gridPane = new GridPane();
        gridPane.setId("root-konfigurationpane");

        Label iconLabel = new Label();
        iconLabel.setText("Konfiguration");

        Label gsLabel = new Label("Geschäftsstelle");
        ComboBox<FragenKategorie> gsComboBox = new ComboBox<>();

        final List<FragenKategorie> gsKategorien = new ArrayList<>();
        gsKategorien.addAll(Arrays.asList(FragenKategorie.values()));
        gsKategorien.remove(FragenKategorie.ALLGEMEIN);
        gsComboBox.getItems().addAll(gsKategorien);
        gsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            LOG.debug("Eintrag selektiert: " + newValue);
            startButton.setDisable(newValue == null);
        });

        Label anzahlSpieleLabel = new Label("Anzahl Spiele");
        TextField anzahlSpieleTextField = new TextField();
        anzahlSpieleTextField.setText("0");

        startButton = new Button("Start");
        startButton.setDisable(true);

        gridPane.add(iconLabel, 0, 0, 2, 1);
        gridPane.add(gsLabel, 0, 1, 1, 1);
        gridPane.add(gsComboBox, 1, 1, 1, 1);
        gridPane.add(anzahlSpieleLabel, 0, 2, 1, 1);
        gridPane.add(anzahlSpieleTextField, 1, 2, 1, 1);
        gridPane.add(startButton, 0, 3, 2, 1);

        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();

//        gridPaneBuilder.gridLinesVisible(true);
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.CENTER).percentHeight(25).fillHeight(true).vgrow(Priority.ALWAYS).build(),
                rowConstraintsBuilder.build(),
                rowConstraintsBuilder.build(),
                rowConstraintsBuilder.build()
        );

        gridPaneBuilder.applyTo(gridPane);


        scene = new Scene(gridPane);
        scene.getStylesheets().add("css/stylesheet.css");
    }


    public Scene getScene() {
        return scene;
    }
}
