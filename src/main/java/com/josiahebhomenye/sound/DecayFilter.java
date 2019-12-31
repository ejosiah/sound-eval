package com.josiahebhomenye.sound;

import java.time.Duration;

public class DecayFilter extends SoundFilter {


    protected DecayFilter(Duration duration) {
        super(duration);
    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        for(int i = offset; i < length; i++, index++){
            float t = index * timeStep;
            samples[i] *= Math.exp(-t);
        }
        index %= nSamples;
    }
}
