package com.uefix.vobuzzer.model.observable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Uefix on 13.03.2016.
 */
public abstract class ObservableModel<EVENT extends ModelEvent> {

    private Set<ModelListener<EVENT>> listeners = new HashSet<ModelListener<EVENT>>();

    public void addListener(ModelListener<EVENT> listener) {
        listeners.add(listener);
    }

    public void notifyListeners(EVENT event) {
        for (ModelListener<EVENT> listener : listeners) {
            listener.onEvent(this, event);
        }
    }
}
