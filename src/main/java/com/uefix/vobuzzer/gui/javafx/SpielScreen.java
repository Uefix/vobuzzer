package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.VOBuzzerMain;
import com.uefix.vobuzzer.model.AntwortSlot;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.service.FragenService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uefix on 13.03.2016.
 */
public class SpielScreen {

    public static final Logger LOG = Logger.getLogger(VOBuzzerApplication.class);

    private FragenService fragenService;

    private FrageBox frageBox;
    private Map<AntwortSlot, AntwortBox> antwortBoxen;

    private Scene scene;

    public void initialize() {
        fragenService = new FragenService(VOBuzzerMain.fragenKatalog);

        frageBox = new FrageBox();
        frageBox.initComponents();

        antwortBoxen = new HashMap<>(4);
        antwortBoxen.put(AntwortSlot.A, new AntwortBox(AntwortSlot.A));
        antwortBoxen.put(AntwortSlot.B, new AntwortBox(AntwortSlot.B));
        antwortBoxen.put(AntwortSlot.C, new AntwortBox(AntwortSlot.C));
        antwortBoxen.put(AntwortSlot.D, new AntwortBox(AntwortSlot.D));
        antwortBoxen.forEach((antwortSlot, antwortBox) -> antwortBox.initComponents());


        final StackPane rootSpielPane = new StackPane();
        rootSpielPane.setId("root-spielpane");
        rootSpielPane.getChildren().add(buildFragenPane());

        scene = new Scene(rootSpielPane);
        scene.getStylesheets().add("css/stylesheet.css");
        scene.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                        LOG.debug("newSceneHeight=" + newSceneHeight);

                        double rootEmValue = newSceneHeight.doubleValue() / 40;

                        DoubleProperty fontSize = new SimpleDoubleProperty(rootEmValue); // font size in pt
                        rootSpielPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;", fontSize));

                        frageBox.onSceneHeightChanged(rootEmValue);
                    }
                }
        );
    }


    public Scene getScene() {
        return scene;
    }

    public GridPane buildFragenPane() {

        /*
        frageText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                frageText.setPrefHeight(1);
                frageText.setPrefWidth(frageText.getText().length() * 7);
            }
        });
        */
//        fragePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
//        fragePane.getChildren().add(frageText);

        Button buttonAntwortA = new Button();
        buttonAntwortA.setText("Antwort A");
        buttonAntwortA.getStyleClass().add("antwort-button");

        Button buttonAntwortB = new Button();
        buttonAntwortB.setText("Antwort B");
        buttonAntwortB.getStyleClass().add("antwort-button");
        buttonAntwortB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                frageBox.setText("1998 hat Twisteden am Wettbewerb „unser Dorf soll schöner werden“ teilgenommen. Welche Medaille erhielt sie?");
            }
        });

        Button buttonAntwortC = new Button();
        buttonAntwortC.setText("Antwort C");
        buttonAntwortC.getStyleClass().add("antwort-button");
        buttonAntwortC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Frage frage = fragenService.getZufaelligeFrage();
                frageBox.setText(frage.getText());
            }
        });

        Button buttonAntwortD = new Button();
        buttonAntwortD.setText("Antwort D");
        buttonAntwortD.getStyleClass().add("antwort-button");

        GridPane gridPane = new GridPane();
        gridPane.add(frageBox.getRootPane(), 0, 0, 2, 1);
//        gridPane.add(antwortBoxen.get(AntwortSlot.A).getRootPane(), 0, 1, 1, 1);
//        gridPane.add(antwortBoxen.get(AntwortSlot.B).getRootPane(), 1, 1, 1, 1);
//        gridPane.add(antwortBoxen.get(AntwortSlot.C).getRootPane(), 0, 2, 1, 1);
//        gridPane.add(antwortBoxen.get(AntwortSlot.D).getRootPane(), 1, 2, 1, 1);
        gridPane.add(buttonAntwortA, 0, 1, 1, 1);
        gridPane.add(buttonAntwortB, 1, 1, 1, 1);
        gridPane.add(buttonAntwortC, 0, 2, 1, 1);
        gridPane.add(buttonAntwortD, 1, 2, 1, 1);

        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();

//        gridPaneBuilder.gridLinesVisible(true);
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.CENTER).percentHeight(40).fillHeight(true).vgrow(Priority.ALWAYS).build(),
                rowConstraintsBuilder.percentHeight(25).percentHeight(30).build(),
                rowConstraintsBuilder.build()
        );

        gridPaneBuilder.applyTo(gridPane);


        return gridPane;
    }
}
