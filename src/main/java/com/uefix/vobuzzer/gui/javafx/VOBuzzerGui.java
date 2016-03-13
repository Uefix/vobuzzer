package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.ObservableModel;
import com.uefix.vobuzzer.model.ModelListener;
import com.uefix.vobuzzer.model.ApplicationStateMachine;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Uefix on 20.02.2016.
 */
@Named
public class VOBuzzerGui implements ModelListener<ApplicationStateMachine.Event> {

    public static final Logger LOG = Logger.getLogger(VOBuzzerGui.class);

    @Inject
    private ApplicationStateMachine applicationStateMachine;

    @Inject
    private KonfigurationScreen konfigurationScreen;

    @Inject
    private StartScreen startScreen;

    @Inject
    private SpielScreen spielScreen;


    private Stage stage;

    @PostConstruct
    public void initialize() {
        applicationStateMachine.addListener(this);
    }

    @Override
    public void onEvent(ObservableModel<ApplicationStateMachine.Event> model, ApplicationStateMachine.Event event) {
        switch (event.getNewState()) {
            case CONFIGURATION:
                stage.setScene(konfigurationScreen.getScene());
                stage.setFullScreen(false);
                break;

            case START:
                stage.setScene(startScreen.getScene());
                stage.setFullScreen(true);
                break;

            case GAME:
                stage.setScene(spielScreen.getScene());
                stage.setFullScreen(true);
                break;
        }
    }

    public void start(Stage stage) throws Exception {
        konfigurationScreen.setupGui();
        startScreen.setupGui();
        spielScreen.setupGui();

        this.stage = stage;

        stage.setTitle("VOBuzzer");
        stage.setScene(startScreen.getScene());
        stage.setScene(spielScreen.getScene());
        stage.setScene(konfigurationScreen.getScene());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                LOG.info("Byebye.");
                System.exit(0);
            }
        });
        stage.show();
    }
}
