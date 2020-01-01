package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;
import static com.josiahebhomenye.sound.SamplesUtil.*;

public class TriangleWave extends SoundGenerator implements WaveFunctions {

    private final int limit;

    public TriangleWave(int limit, Duration duration) {
        super(duration);
        this.limit = limit;
    }

    @Override
    public void generate() {
        double min = triangle(limit, Math.PI);
        double max = triangle(limit, 0);

        for(int i = 0; i < nSamples; i++){
            double t = timeStep * i;
            double sample = triangle(limit, t);
            channels.write(scaleToUnit(sample, max, min));
        }
    }
}
