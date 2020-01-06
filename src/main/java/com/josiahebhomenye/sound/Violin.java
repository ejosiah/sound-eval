package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Violin extends SoundGenerator implements WaveFunctions{

    private double frequency;
    private List<Harmonic> harmonics = Arrays.asList(
            new Harmonic(1, 0.995),
            new Harmonic(2, 0.940),
            new Harmonic(3, 0.425),
            new Harmonic(4, 0.480),
            new Harmonic(6, 0.365),
            new Harmonic(7, 0.040),
            new Harmonic(8, 0.085),
            new Harmonic(10, 0.090)
    );

    public Violin(double frequency, Duration duration) {
        super(duration);
        this.frequency = frequency;
    }

    @Override
    public void generate() {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double[] samples = new double[nSamples];
        for (int i = 0; i < nSamples; i++) {
            double t = timeStep * i;
            double sample = 0;
            for(Harmonic h : harmonics){
                double f = frequency * Math.pow(2, h.index() - 1);
                double a = h.weight();
                sample += a * sin(a, f, t);
            }
            min = Math.min(min, sample);
            max = Math.max(max, sample);
            samples[i] = sample;
        }

        for(int i = 0; i < nSamples; i++){
           channels.write(SamplesUtil.scaleToUnit(samples[i], min, max));
        }
    }
}
