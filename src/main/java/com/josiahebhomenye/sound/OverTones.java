package com.josiahebhomenye.sound;

import static com.josiahebhomenye.sound.SamplesUtil.*;
import java.time.Duration;

public class OverTones extends SoundGenerator implements WaveFunctions {

    private final double frequency;
    private final int count;

    public OverTones(double frequency, int count, Duration duration) {
        super(duration);
        this.frequency = frequency;
        this.count = count;
    }

    @Override
    public void generate() {
//        for(int i = 0; i < nSamples; i++){
//            double a = 1;
//            double f = frequency;
//            double t = timeStep * i;
//            double sample = 0;
//            for(int j = 0; j < count; j++){
//                sample += a * sin(f, t);
//                a /= 2;
//                f *=2;
//            }
//
//            int scale = (count+1);
//            channels.write(scaleToUnit(sample, -scale, scale));
//        }
    }
}
