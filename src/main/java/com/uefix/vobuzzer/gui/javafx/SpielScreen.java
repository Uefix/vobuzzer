package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.AntwortSlot;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.observable.ApplicationStateModel;
import com.uefix.vobuzzer.model.observable.RootEmModel;
import com.uefix.vobuzzer.service.FragenService;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class SpielScreen {

    public static final Logger LOG = Logger.getLogger(VOBuzzerGui.class);

    @Inject
    private FragenService fragenService;

    @Inject
    private ApplicationStateModel applicationStateModel;

    @Inject
    private RootEmModel rootEmModel;

    private FrageBox frageBox;
    private Map<AntwortSlot, AntwortBox> antwortBoxen;

    private StackPane rootSpielPane;

    @PostConstruct
    public void initialize() {
        rootEmModel.addListener(((model, event) -> {
            double rootEmValue = event.getNewRootEmValue();
            rootSpielPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;", rootEmValue));
            frageBox.onSceneHeightChanged(rootEmValue);
            antwortBoxen.forEach((antwortSlot, antwortBox) -> antwortBox.onSceneHeightChanged(rootEmValue));
        }));
    }


    public void initializeNodes() {
        frageBox = new FrageBox();
        frageBox.initializeNodes();

        antwortBoxen = new HashMap<>(4);
        antwortBoxen.put(AntwortSlot.A, new AntwortBox(AntwortSlot.A));
        antwortBoxen.put(AntwortSlot.B, new AntwortBox(AntwortSlot.B));
        antwortBoxen.put(AntwortSlot.C, new AntwortBox(AntwortSlot.C));
        antwortBoxen.put(AntwortSlot.D, new AntwortBox(AntwortSlot.D));
        antwortBoxen.forEach((antwortSlot, antwortBox) -> antwortBox.initializeNodes());

        rootSpielPane = new StackPane();
        rootSpielPane.setId("root-spielpane");
        rootSpielPane.getChildren().add(buildFragenPane());
    }

    public Parent getRoot() {
        return rootSpielPane;
    }


    private GridPane buildFragenPane() {
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
        buttonAntwortD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                applicationStateModel.fireNewState(ApplicationStateModel.State.SPENDENUHR);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.add(frageBox.getRootPane(), 0, 0, 2, 1);
        gridPane.add(antwortBoxen.get(AntwortSlot.A).getRootPane(), 0, 1, 1, 1);
        gridPane.add(antwortBoxen.get(AntwortSlot.B).getRootPane(), 1, 1, 1, 1);
        gridPane.add(antwortBoxen.get(AntwortSlot.C).getRootPane(), 0, 2, 1, 1);
        gridPane.add(antwortBoxen.get(AntwortSlot.D).getRootPane(), 1, 2, 1, 1);
        gridPane.add(new HBox(), 0, 3, 2, 1);

        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();

        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.CENTER).percentHeight(40).fillHeight(true).vgrow(Priority.ALWAYS).build(),
                rowConstraintsBuilder.percentHeight(26).build(),
                rowConstraintsBuilder.build(),
                rowConstraintsBuilder.percentHeight(8).build()
        );

        gridPaneBuilder.applyTo(gridPane);
        return gridPane;
    }
}
