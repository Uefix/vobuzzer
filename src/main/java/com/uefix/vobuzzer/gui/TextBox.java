package com.uefix.vobuzzer.gui;

import com.uefix.vobuzzer.model.SpielSession;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Uefix on 07.03.2016.
 */
public abstract class TextBox {

    protected StackPane rootPane;
    protected StackPane contentPane;
    protected Circle circle;
    protected Circle circleBorder;
    protected StackPane xBorderPane;
    protected StackPane yBorderPane;

    protected Label textLabel;

    protected ImageCursor imageCursor;

    protected final String styleClassPrefix;
    protected final int wordCountFontSizeThreshold;

    protected final SpielSession spielSession;

    public TextBox(SpielSession spielSession, String styleClassPrefix, int wordCharacterCountFontSizeThreshold) {
        this.spielSession = spielSession;
        this.styleClassPrefix = styleClassPrefix;
        this.wordCountFontSizeThreshold = wordCharacterCountFontSizeThreshold;
    }

    public void initializeNodes() {
        rootPane = new StackPane();

        textLabel = new Label("");
        textLabel.getStyleClass().add(styleClassPrefix + "-text");
        textLabel.setWrapText(true);
        textLabel.setTextAlignment(TextAlignment.CENTER);

        contentPane = new StackPane();
        contentPane.getStyleClass().add(styleClassPrefix + "-text-box-pane-content");
        contentPane.getChildren().add(textLabel);

        rootPane.getChildren().add(contentPane);

        xBorderPane = new StackPane();
        xBorderPane.getStyleClass().add(styleClassPrefix + "-text-box-pane-xborder");
        rootPane.getChildren().add(xBorderPane);

        yBorderPane = new StackPane();
        yBorderPane.getStyleClass().add(styleClassPrefix + "-text-box-pane-yborder");
        rootPane.getChildren().add(yBorderPane);

        circle = new Circle();
        circle.getStyleClass().add(styleClassPrefix + "-text-box-circle");
        rootPane.getChildren().add(circle);
        StackPane.setAlignment(circle, Pos.TOP_CENTER);

        circleBorder = new Circle();
        circleBorder.getStyleClass().add(styleClassPrefix + "-text-box-circle-border");
        rootPane.getChildren().add(circleBorder);
        StackPane.setAlignment(circleBorder, Pos.TOP_CENTER);


        try {
            Image image = new Image(new ClassPathResource("images/cursor.png").getInputStream());
            imageCursor = new ImageCursor(image);
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to load cursor", ioe);
        }
    }


    public void setText(String text) {
        ObservableList<String> styleClass = textLabel.getStyleClass();
        if (text.length() > wordCountFontSizeThreshold) {
            styleClass.remove(styleClassPrefix + "-text-gross");
            styleClass.add(styleClassPrefix + "-text-klein");
        } else {
            styleClass.remove(styleClassPrefix + "-text-klein");
            styleClass.add(styleClassPrefix + "-text-gross");
        }
        textLabel.setText(text);
    }


    public Pane getRootPane() {
        return rootPane;
    }


    //---- Event callbacks ----//

    public void onSceneHeightChanged(double rootEmValue) {
    }


    public abstract void setOnCircleClickedHandler(EventHandler<MouseEvent> eventHandler);
}
