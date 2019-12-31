package com.josiahebhomenye.sound;

import java.time.Duration;

public class Switch {

    public final int duration;
    public final State state;

    public Switch(int duration, State state) {
        this.duration = duration;
        this.state = state;
    }

    public enum State{
        ON(1), OFF(0);

        private State(int value){
            this.value = value;
        }
        public int value;

        public State invert(){
            if(this == ON) return OFF;
            else return ON;
        }
    }
}
