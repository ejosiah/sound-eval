package com.josiahebhomenye.sound;

import java.time.Duration;

public class OverTones extends SoundGenerator implements WaveFunctions {

    private final double frequency;
    private final int octaves;

    public OverTones(double frequency, int octaves, Duration duration) {
        super(duration);
        this.frequency = frequency;
        this.octaves = octaves;
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            double a = 1;
            double f = frequency;
            double t = timeStep * i;
            double sample = 0;
            for(int j = 0; j < octaves; j++){
                sample += (a * sin(f, t));
                a /= 2;
                f *=2;
            }
            channels.write(sample/1.318);
        }
    }
}
