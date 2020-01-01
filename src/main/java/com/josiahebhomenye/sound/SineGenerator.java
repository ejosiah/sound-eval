package com.josiahebhomenye.sound;

import java.nio.ByteBuffer;
import java.time.Duration;

public class SineGenerator extends SoundGenerator implements WaveFunctions {
    private final double amplitude;
    private final double frequency;

    public SineGenerator(double frequency, Duration duration){
        this(1, frequency, duration);
    }

    public SineGenerator(double amplitude, double frequency, Duration duration) {
        super(duration);
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    @Override
    public void generate() {
       for(int i = 0; i < nSamples; i++){
           double t = i * timeStep;
           double sample = sin(frequency, t);
           channels.write(sample);
       }
    }
}
