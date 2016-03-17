package com.uefix.vobuzzer.model.observable;

import javax.inject.Named;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class ApplicationStateModel extends ObservableModel<ApplicationStateModel.Event> {

    public enum State {
        KONFIGURATION,
        SPENDENUHR,
        SPIEL
    }


    private State state = State.KONFIGURATION;


    public void fireNewState(State newState) {
        if (newState != this.state) {
            State oldState = this.state;
            this.state = newState;
            notifyListeners(new Event(oldState, newState));
        }
    }

    public static class Event implements ModelEvent {

        private State oldState;
        private State newState;

        public Event(State oldState, State newState) {
            this.oldState = oldState;
            this.newState = newState;
        }

        public State getOldState() {
            return oldState;
        }

        public void setOldState(State oldState) {
            this.oldState = oldState;
        }

        public State getNewState() {
            return newState;
        }

        public void setNewState(State newState) {
            this.newState = newState;
        }
    }
}
