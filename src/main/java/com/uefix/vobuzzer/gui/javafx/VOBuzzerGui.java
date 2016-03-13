package com.uefix.vobuzzer.gui.javafx;

import com.uefix.vobuzzer.model.ObservableModel;
import com.uefix.vobuzzer.model.ModelListener;
import com.uefix.vobuzzer.model.VOBuzzerStateMachine;
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
public class VOBuzzerGui implements ModelListener<VOBuzzerStateMachine.Event> {

    public static final Logger LOG = Logger.getLogger(VOBuzzerGui.class);

    @Inject
    private VOBuzzerStateMachine voBuzzerStateMachine;

    @Inject
    private KonfigurationScreen konfigurationScreen;

    @Inject
    private StartScreen startScreen;

    @Inject
    private SpielScreen spielScreen;


    private Stage stage;

    @PostConstruct
    public void initialize() {
        voBuzzerStateMachine.addListener(this);
    }

    @Override
    public void onEvent(ObservableModel<VOBuzzerStateMachine.Event> model, VOBuzzerStateMachine.Event event) {
        switch (event.getNewState()) {
            case CONFIGURATION:
                stage.setScene(konfigurationScreen.getScene());
                stage.setFullScreen(false);
                break;

            case START:
                stage.setScene(startScreen.getScene());
                if (event.getOldState() == VOBuzzerStateMachine.State.CONFIGURATION) {
                    stage.setFullScreen(true);
                }
                break;

            case GAME:
                stage.setScene(spielScreen.getScene());
                break;
        }
    }

    public void start(Stage stage) throws Exception {
        konfigurationScreen.initialize();
        startScreen.initialize();
        spielScreen.initialize();

        this.stage = stage;

        stage.setTitle("VOBuzzer");
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
