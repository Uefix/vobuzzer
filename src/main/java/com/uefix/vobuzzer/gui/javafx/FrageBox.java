package com.uefix.vobuzzer.gui.javafx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import org.apache.log4j.Logger;

/**
 * Created by Uefix on 07.03.2016.
 */
public class FrageBox {

    private static final double CIRCLE_TOP_MARGIN_EM = 5.2d;
    private static final double CIRCLE_RADIUS_EM = 3.2d;
    private static final double CIRCLE_BORDER_RADIUS_EM = 3.5d;
    private static final double LOGO_HEIGHT_EM = 5.4d;

    private StackPane rootPane;
    private StackPane contentPane;
    private Circle circle;
    private Circle circleBorder;
    private StackPane xBorderPane;
    private StackPane yBorderPane;
    private ImageView logoImage;
    private Label textLabel;


    public void initComponents() {
        rootPane = new StackPane();
        rootPane.setId("frage-pane");

        textLabel = new Label("...");
        textLabel.setId("frage-text");
        textLabel.setWrapText(true);
        textLabel.setTextAlignment(TextAlignment.CENTER);

        contentPane = new StackPane();
        contentPane.getStyleClass().add("text-box-pane-content");
        contentPane.getChildren().add(textLabel);
        rootPane.getChildren().add(contentPane);

        xBorderPane = new StackPane();
        xBorderPane.getStyleClass().add("text-box-pane-xborder");
        rootPane.getChildren().add(xBorderPane);

        yBorderPane = new StackPane();
        yBorderPane.getStyleClass().add("text-box-pane-yborder");
        rootPane.getChildren().add(yBorderPane);

        circle = new Circle();
        circle.getStyleClass().add("text-box-circle");
        rootPane.getChildren().add(circle);
        StackPane.setAlignment(circle, Pos.TOP_CENTER);

        circleBorder = new Circle();
        circleBorder.getStyleClass().add("text-box-circle-border");
        rootPane.getChildren().add(circleBorder);
        StackPane.setAlignment(circleBorder, Pos.TOP_CENTER);

        logoImage = new ImageView();
        logoImage.setId("frage-box-logo");
        logoImage.setPreserveRatio(true);
        logoImage.setSmooth(true);
        rootPane.getChildren().add(logoImage);
        StackPane.setAlignment(logoImage, Pos.TOP_CENTER);
    }


    public void setText(String text) {
        ObservableList<String> styleClass = textLabel.getStyleClass();
        if (text.length() > 80) {
            styleClass.remove("frage-text-gross");
            styleClass.add("frage-text-klein");
        } else {
            styleClass.remove("frage-text-klein");
            styleClass.add("frage-text-gross");
        }
        textLabel.setText(text);
    }


    public Pane getRootPane() {
        return rootPane;
    }


    public void onSceneHeightChanged(double rootEmValue) {
        double radius = CIRCLE_RADIUS_EM * rootEmValue;
        circle.setRadius(radius);
        StackPane.setMargin(circle, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radius, 0, 0, 0));

        double radiusBorder = CIRCLE_BORDER_RADIUS_EM * rootEmValue;
        circleBorder.setRadius(radiusBorder);
        StackPane.setMargin(circleBorder, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - radiusBorder - 0.08 * rootEmValue, 0, 0, 0));

        double logoHeight = LOGO_HEIGHT_EM * rootEmValue;
        logoImage.setFitHeight(logoHeight);
        StackPane.setMargin(logoImage, new Insets(CIRCLE_TOP_MARGIN_EM * rootEmValue - (logoHeight / 2) - 0.08 * rootEmValue, 0, 0, 0));
    }
}
