package com.josiahebhomenye.sound;

import java.time.Duration;

public class DialTone extends SoundGenerator implements WaveFunctions {

    OnOffFilter filter;

    public DialTone(Duration duration) {
        super(duration);
    }

    @Override
    public void generate() {
//        for(int i = 0; i < nSamples; i++){
//            double t = i * timeStep;
//            double sample = sin(350, t) + 0.1 * sin(440, t);
//            channels.write(sample);
//        }
//        filter.filter(buffer.array());
    }
}
