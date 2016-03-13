package com.uefix.vobuzzer.model;

/**
 * Created by Uefix on 13.03.2016.
 */
public interface ModelListener<EVENT extends ModelEvent> {

    void onEvent(ObservableModel<EVENT> model, EVENT event);
}
