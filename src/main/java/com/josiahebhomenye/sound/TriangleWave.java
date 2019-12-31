package com.josiahebhomenye.sound;

import java.time.Duration;
import static java.lang.Math.*;

public class TriangleWave extends SoundGenerator implements WaveFunctions {

    private final int limit;

    public TriangleWave(int limit, Duration duration) {
        super(duration);
        this.limit = limit;
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            float t = timeStep * i;
         //   float sample = 0;
            float sample = sin(t) - sin(2 * t)/2 + sin(3 * t)/3 - sin(4 * t)/4;
//            for(float n = 1; n <= limit; n++){
//                sample += (1.0/((2 * n - 1) * (2 * n - 1))) * cos((2 * n - 1) * t);
//            }
            channels.write(sample);
        }
    }
}
