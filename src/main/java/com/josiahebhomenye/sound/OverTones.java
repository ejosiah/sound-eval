package com.josiahebhomenye.sound;

import java.time.Duration;

public class OverTones extends SoundGenerator implements WaveFunctions {

    private final float frequency;
    private final int count;

    public OverTones(float frequency, int count, Duration duration) {
        super(duration);
        this.frequency = frequency;
        this.count = count;
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            float a = 1;
            float f = frequency;
            float t = timeStep * i;
            float sample = 0;
            for(int j = 0; j < count; j++){
                sample += a * sin(f, t);
                a /= 2;
                f *=2;
            }
            channels.write(sample);
        }
    }
}
