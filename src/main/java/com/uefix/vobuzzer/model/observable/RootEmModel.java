package com.uefix.vobuzzer.model.observable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javax.inject.Named;

/**
 * Created by Uefix on 17.03.2016.
 */
@Named
public class RootEmModel extends ObservableModel<RootEmModel.Event> {

    public void fireNewRootEmValue(double rootEmValue) {
        notifyListeners(new Event(rootEmValue));
    }


    public static class Event implements ModelEvent {
        private final double newRootEmValue;

        public Event(double newRootEmValue) {
            this.newRootEmValue = newRootEmValue;
        }

        public double getNewRootEmValue() {
            return newRootEmValue;
        }
    }
}
