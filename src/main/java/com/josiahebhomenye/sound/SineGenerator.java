package com.josiahebhomenye.sound;

import java.nio.ByteBuffer;
import java.time.Duration;

public class SineGenerator extends SoundGenerator implements WaveFunctions {
    private final float amplitude;
    private final float frequency;

    public SineGenerator(float frequency, Duration duration){
        this(1, frequency, duration);
    }

    public SineGenerator(float amplitude, float frequency, Duration duration) {
        super(duration);
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    @Override
    public void generate() {
       for(int i = 0; i < nSamples; i++){
           float t = i * timeStep;
           float sample = sin(frequency, t);
           channels.write(sample);
       }
    }
}
