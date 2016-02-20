package com.uefix.vobuzzer.gui.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("VOBuzzer");
        Button button = new Button();
        button.setText("Say 'Hello World'");
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                LOG.info("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(addFragenPane());

        stage.setScene(new Scene(root));
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


    public GridPane addFragenPane() {
        Font font = Font.font("Arial", FontWeight.BOLD, 20);

        Text frageText = new Text("Wie wollen wir die GUI implementieren?");
        frageText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button buttonAntwortA = new Button();
        buttonAntwortA.setText("Antwort A");
        buttonAntwortA.setFont(font);

        Button buttonAntwortB = new Button();
        buttonAntwortB.setText("Antwort B");
        buttonAntwortB.setFont(font);

        Button buttonAntwortC = new Button();
        buttonAntwortC.setText("Antwort C");
        buttonAntwortC.setFont(font);

        Button buttonAntwortD = new Button();
        buttonAntwortD.setText("Antwort D");
        buttonAntwortD.setFont(font);

        GridPane gridPane = new GridPane();
//        gridPane.add(frageText, 1, 1, 2, 1);
//        gridPane.add(buttonAntwortA, 1, 2, 1, 1);
//        gridPane.add(buttonAntwortB, 2, 2, 1, 1);
//        gridPane.add(buttonAntwortC, 1, 3, 1, 1);
//        gridPane.add(buttonAntwortD, 2, 3, 1, 1);

        gridPane.add(frageText, 0, 0, 2, 1);
        gridPane.add(buttonAntwortA, 0, 1, 1, 1);
        gridPane.add(buttonAntwortB, 1, 1, 1, 1);
        gridPane.add(buttonAntwortC, 0, 2, 1, 1);
        gridPane.add(buttonAntwortD, 0, 2, 1, 1);


        ColumnConstraintsBuilder columnConstraintsBuilder = ColumnConstraintsBuilder.create();
        RowConstraintsBuilder rowConstraintsBuilder = RowConstraintsBuilder.create();

        GridPaneBuilder gridPaneBuilder = GridPaneBuilder.create();
//        gridPaneBuilder.vgap(1).hgap(1);
        gridPaneBuilder.gridLinesVisible(true);
        gridPaneBuilder.columnConstraints(
                columnConstraintsBuilder.halignment(HPos.CENTER).percentWidth(50).fillWidth(true).build(),
                columnConstraintsBuilder.build()
        );
        gridPaneBuilder.rowConstraints(
                rowConstraintsBuilder.valignment(VPos.CENTER).percentHeight(50).fillHeight(true).build(),
                rowConstraintsBuilder.percentHeight(25).build(),
                rowConstraintsBuilder.build()
        );

        gridPaneBuilder.applyTo(gridPane);


        return gridPane;
    }
}
