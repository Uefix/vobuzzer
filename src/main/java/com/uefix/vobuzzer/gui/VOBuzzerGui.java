package com.uefix.vobuzzer.gui;

import com.uefix.vobuzzer.model.observable.ApplicationStateModel;
import com.uefix.vobuzzer.model.observable.ModelListener;
import com.uefix.vobuzzer.model.observable.ObservableModel;
import com.uefix.vobuzzer.model.observable.RootEmModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Uefix on 20.02.2016.
 */
@Named
public class VOBuzzerGui implements ModelListener<ApplicationStateModel.Event> {

    public static final Logger LOG = Logger.getLogger(VOBuzzerGui.class);

    @Inject
    private ApplicationStateModel applicationStateModel;

    @Inject
    private RootEmModel rootEmModel;

    @Inject
    private KonfigurationScreen konfigurationScreen;

    @Inject
    private SpendenUhrScreen spendenUhrScreen;

    @Inject
    private SpielScreen spielScreen;


    private Stage stage;

    private Scene gameScene;

    @PostConstruct
    public void initialize() {
        applicationStateModel.addListener(this);
    }

    @Override
    public void onEvent(ObservableModel<ApplicationStateModel.Event> model, ApplicationStateModel.Event event) {
        switch (event.getNewState()) {
            case KONFIGURATION:
                stage.setScene(konfigurationScreen.getScene());
                stage.setFullScreen(false);
                break;

            case SPENDENUHR:
                if (stage.getScene() != gameScene) {
                    stage.setScene(gameScene);
                    stage.setFullScreen(true);
                }
                gameScene.setRoot(spendenUhrScreen.getRoot());
                break;

            case SPIEL:
                gameScene.setRoot(spielScreen.getRoot());
                break;
        }
    }

    public void start(Stage primaryStage) throws Exception {
        konfigurationScreen.initializeNodes();
        spendenUhrScreen.initializeNodes();
        spielScreen.initializeNodes();

        Parent dummyRootNode = new HBox();
        gameScene = new Scene(dummyRootNode);
        gameScene.getStylesheets().add("css/stylesheet.css");
        gameScene.heightProperty().addListener(
                (observableValue, oldSceneHeight, newSceneHeight) -> {
                    double rootEmValue = newSceneHeight.doubleValue() / 40;
                    rootEmModel.fireNewRootEmValue(rootEmValue);
                });

        this.stage = primaryStage;

        primaryStage.setTitle("VOBuzzer");
        primaryStage.setScene(konfigurationScreen.getScene());
        primaryStage.setOnCloseRequest(windowEvent -> {
            LOG.info("Byebye.");
            System.exit(0);
        });
        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            primaryStage.setFullScreen(true);
        });
        primaryStage.show();
    }
}
