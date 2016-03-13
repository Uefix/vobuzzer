package com.uefix.vobuzzer.gui.javafx;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

/**
 * Created by Uefix on 07.03.2016.
 */
public class FrageBox extends TextBox {

    private static final double CIRCLE_TOP_MARGIN_EM = 5.2d;
    private static final double CIRCLE_RADIUS_EM = 3.2d;
    private static final double CIRCLE_BORDER_RADIUS_EM = 3.5d;
    private static final double LOGO_HEIGHT_EM = 5.4d;


    public FrageBox() {
        super("frage", 80);
    }


    @Override
    public void initComponents() {
        super.initComponents();
        rootPane.setId("frage-pane");
        textLabel.setId("frage-text");
        circleImage.setId("frage-box-logo");
    }


    @Override
    public void onSceneHeightChanged(double rootEmValue) {
        double radius = CIRCLE_RADIUS_EM * rootEmValue;
        circle.setRadius(radius);
        StackPane.setMargin(circle, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radius, 0, 0, 0));

        double radiusBorder = CIRCLE_BORDER_RADIUS_EM * rootEmValue;
        circleBorder.setRadius(radiusBorder);
        StackPane.setMargin(circleBorder, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radiusBorder - 0.08 * rootEmValue, 0, 0, 0));

        double logoHeight = LOGO_HEIGHT_EM * rootEmValue;
        circleImage.setFitHeight(logoHeight);
        StackPane.setMargin(circleImage, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - (logoHeight / 2) - 0.08 * rootEmValue, 0, 0, 0));
    }
}