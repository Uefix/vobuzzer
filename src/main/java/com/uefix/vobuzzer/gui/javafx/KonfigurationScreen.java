package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.FragenKategorie;
import com.uefix.vobuzzer.model.VOBuzzerStateMachine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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

    public static final Logger LOG = Logger.getLogger(StartScreen.class);


    @Inject
    private VOBuzzerStateMachine voBuzzerStateMachine;

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
        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                voBuzzerStateMachine.setNewState(VOBuzzerStateMachine.State.START);
            }
        });

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
