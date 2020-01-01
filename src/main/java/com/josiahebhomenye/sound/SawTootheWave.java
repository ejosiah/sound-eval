package com.josiahebhomenye.sound;

import java.time.Duration;

import static com.josiahebhomenye.sound.SamplesUtil.scaleToUnit;

public class SawTootheWave extends SoundGenerator implements WaveFunctions {

    private final int limit;

    public SawTootheWave(int limit, Duration duration) {
        super(duration);
        this.limit = limit;
    }

    @Override
    public void generate() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double[] samples = new double[nSamples];
        for(int i = 0; i < nSamples; i++){
            double t = timeStep * i;
            double sample = sawtooth(limit, t);
            min = Math.min(min, sample);
            max = Math.max(max, sample);
            samples[i] = sample;
        }
        for(int i = 0; i < nSamples; i++){
            channels.write(scaleToUnit(samples[i], min, max));
        }
    }
}
