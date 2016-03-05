package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.VOBuzzerMain;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.service.FragenService;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

/**
 * Created by Uefix on 20.02.2016.
 */
public class VOBuzzerApplication extends Application {


    public static final Logger LOG = Logger.getLogger(VOBuzzerApplication.class);


    public void launchApplication(String[] args) {
        Application.launch(args);
    }

    private FragenService fragenService;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("VOBuzzer");

        fragenService = new FragenService(VOBuzzerMain.fragenKatalog);


        StackPane root = new StackPane();
        root.getChildren().add(buildFragenPane());
        // ScalableContentPane root = new ScalableContentPane();
        // root.getContentPane().getChildren().add(buildFragenPane());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/stylesheet.css");
        // scene.getStylesheets().add("file:///d:/var/work.css");

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                LOG.info("Byebye.");
                System.exit(0);
            }
        });

        stage.show();
    }


    public GridPane buildFragenPane() {
        final Label frageText = new Label("Wie wollen wir die GUI implementieren?");
        frageText.setId("frage-text");
        frageText.setWrapText(true);
        frageText.setTextAlignment(TextAlignment.CENTER);
        frageText.getStyleClass().add("frage-text-gross");
        

        /*
        frageText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                frageText.setPrefHeight(1);
                frageText.setPrefWidth(frageText.getText().length() * 7);
            }
        });
        */

        HBox fragePane = new HBox();
        fragePane.setId("frage-pane");
//        fragePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        fragePane.getChildren().add(frageText);

        Button buttonAntwortA = new Button();
        buttonAntwortA.setText("Antwort A");
        buttonAntwortA.getStyleClass().add("antwort-button");
        buttonAntwortA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                frageText.setText("Wie wollen wir die GUI implementieren?");
            }
        });

        Button buttonAntwortB = new Button();
        buttonAntwortB.setText("Antwort B");
        buttonAntwortB.getStyleClass().add("antwort-button");
        buttonAntwortB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                frageText.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
            }
        });

        Button buttonAntwortC = new Button();
        buttonAntwortC.setText("Antwort C");
        buttonAntwortC.getStyleClass().add("antwort-button");
        buttonAntwortC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Frage frage = fragenService.getZufaelligeFrage();

                if (frageText.getText().length() > 20) {
                    frageText.getStyleClass().add("frage-text-klein");
                } else {
                    frageText.getStyleClass().add("frage-text-gross");
                }

                frageText.setText(frage.getText());
            }
        });

        Button buttonAntwortD = new Button();
        buttonAntwortD.setText("Antwort D");
        buttonAntwortD.getStyleClass().add("antwort-button");

        GridPane gridPane = new GridPane();
        gridPane.add(fragePane, 0, 0, 2, 1);
        gridPane.add(buttonAntwortA, 0, 1, 1, 1);
        gridPane.add(buttonAntwortB, 1, 1, 1, 1);
        gridPane.add(buttonAntwortC, 0, 2, 1, 1);
        gridPane.add(buttonAntwortD, 1, 2, 1, 1);


        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();

        gridPaneBuilder.gridLinesVisible(true);
        gridPaneBuilder.hgap(20);
        gridPaneBuilder.vgap(20);
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).hgrow(Priority.ALWAYS).build(),
                columnConstraintsBuilder.build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.CENTER).percentHeight(50).fillHeight(true).vgrow(Priority.ALWAYS).build(),
                rowConstraintsBuilder.percentHeight(25).build(),
                rowConstraintsBuilder.build()
        );

        gridPaneBuilder.applyTo(gridPane);


        return gridPane;
    }
}
