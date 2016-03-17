package com.uefix.vobuzzer.gui;

import com.uefix.vobuzzer.model.AntwortSlot;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


/**
 * Created by Uefix on 07.03.2016.
 */
public class AntwortBox extends TextBox {

    private static final double CIRCLE_TOP_MARGIN_EM = 3.5d;
    private static final double CIRCLE_RADIUS_EM = 2.1d;
    private static final double CIRCLE_BORDER_RADIUS_EM = 2.25d;
    private static final double LABEL_HEIGHT_EM = 3.7d;

    private final AntwortSlot antwortSlot;

    private Label circleLabel;

    public AntwortBox(AntwortSlot antwortSlot) {
        super("antwort", 30);
        this.antwortSlot = antwortSlot;
    }


    @Override
    public void initializeNodes() {
        super.initializeNodes();
        String slotQualifier = antwortSlot.name().toLowerCase();

        rootPane.setId("antwort-" + slotQualifier + "-pane");

        circleLabel = new Label(antwortSlot.name());
        circleLabel.setId("antwort-" + slotQualifier + "-box-label");
        circleLabel.getStyleClass().add("antwort-box-label");
        rootPane.getChildren().add(circleLabel);
        StackPane.setAlignment(circleLabel, Pos.TOP_CENTER);

        textLabel.getStyleClass().add("antwort-text");
    }


    @Override
    public void onSceneHeightChanged(double rootEmValue) {
        double radius = CIRCLE_RADIUS_EM * rootEmValue;
        circle.setRadius(radius);
        StackPane.setMargin(circle, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radius, 0, 0, 0));

        double magicCircleNumber = 0.015d;

        double radiusBorder = CIRCLE_BORDER_RADIUS_EM * rootEmValue;
        circleBorder.setRadius(radiusBorder);
        StackPane.setMargin(circleBorder, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radiusBorder - magicCircleNumber * rootEmValue, 0, 0, 0));

        double logoHeight = LABEL_HEIGHT_EM * rootEmValue;
        StackPane.setMargin(circleLabel, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - (logoHeight / 2) - magicCircleNumber * rootEmValue, 0, 0, 0));
    }

    @Override
    public void setOnCircleClickedHandler(EventHandler<MouseEvent> eventHandler) {
        circleLabel.setOnMouseClicked(eventHandler);
    }
}
