package com.uefix.vobuzzer.model;

import javax.inject.Named;

/**
 * Created by Uefix on 13.03.2016.
 */
@Named
public class VOBuzzerStateMachine extends ObservableModel<VOBuzzerStateMachine.Event> {

    public enum State {
        CONFIGURATION,
        START,
        GAME
    }


    private State state = State.CONFIGURATION;

    public void setNewState(State newState) {
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
