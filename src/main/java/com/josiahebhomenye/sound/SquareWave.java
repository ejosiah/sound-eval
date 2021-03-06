package com.josiahebhomenye.sound;

import java.time.Duration;

public class SquareWave extends SoundGenerator implements WaveFunctions {

    private final int limit;

    public SquareWave(int limit, Duration duration) {
        super(duration);
        this.limit = limit;
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            double t = timeStep * i;
            double sample = 0;
            for(double n = 1; n <= limit; n++){
                sample += (1.0/(2 * n - 1)) * sin((2 * n - 1) * t);
            }
            channels.write(sample);
        }
    }
}
