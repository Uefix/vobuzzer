package com.uefix.vobuzzer.gui;

import com.uefix.vobuzzer.controller.SpielController;
import com.uefix.vobuzzer.model.FragenKategorie;
import com.uefix.vobuzzer.model.observable.ApplicationStateModel;
import com.uefix.vobuzzer.model.observable.SpielStatistik;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class KonfigurationScreen {

    public static final Logger LOG = Logger.getLogger(SpendenUhrScreen.class);


    @Inject
    private SpielController spielController;

    private Scene scene;

    private Button startButton;

    private TextField anzahlSpieleTextField;


    public void initializeNodes() {
        final GridPane gridPane = new GridPane();
        gridPane.setId("root-konfigurationpane");

        Label iconLabel = new Label();
        iconLabel.setText("Konfiguration");
        iconLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label gsLabel = new Label("Geschäftsstelle");
        gsLabel.setPrefWidth(100);
        gsLabel.setTextAlignment(TextAlignment.LEFT);
        ComboBox<FragenKategorie> gsComboBox = new ComboBox<>();

        final List<FragenKategorie> gsKategorien = new ArrayList<>();
        gsKategorien.addAll(Arrays.asList(FragenKategorie.values()));
        gsKategorien.remove(FragenKategorie.ALLGEMEIN);
        gsComboBox.getItems().addAll(gsKategorien);
        gsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            LOG.debug("Eintrag selektiert: " + newValue);
            try {
                Integer.parseInt(anzahlSpieleTextField.getText());
                startButton.setDisable(gsComboBox.getSelectionModel().getSelectedItem() == null);
            } catch (Exception e) {
                startButton.setDisable(true);
            }

        });

        Label anzahlSpieleLabel = new Label("Anzahl Spiele");
        anzahlSpieleLabel.setPrefWidth(100);
        anzahlSpieleLabel.setTextAlignment(TextAlignment.LEFT);
        anzahlSpieleTextField = new TextField();
        anzahlSpieleTextField.setText("0");
        anzahlSpieleTextField.setOnKeyTyped(event -> {
            try {
                Integer.parseInt(anzahlSpieleTextField.getText());
                startButton.setDisable(gsComboBox.getSelectionModel().getSelectedItem() == null);
            } catch (Exception e) {
                startButton.setDisable(true);
            }
        });

        startButton = new Button("Start");
        startButton.setDisable(true);
        startButton.setOnAction(event -> {
            int anzahlSpiele = Integer.parseInt(anzahlSpieleTextField.getText());
            FragenKategorie fragenKategorie = gsComboBox.getSelectionModel().getSelectedItem();
            spielController.starte(anzahlSpiele, fragenKategorie);
        });

        gridPane.add(iconLabel, 0, 0, 2, 1);
        gridPane.add(gsLabel, 0, 1, 1, 1);
        gridPane.add(gsComboBox, 1, 1, 1, 1);
        gridPane.add(anzahlSpieleLabel, 0, 2, 1, 1);
        gridPane.add(anzahlSpieleTextField, 1, 2, 1, 1);
        gridPane.add(startButton, 0, 3, 2, 1);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.halignment(HPos.LEFT).build()
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
