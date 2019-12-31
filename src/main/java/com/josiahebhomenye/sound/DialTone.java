package com.josiahebhomenye.sound;

import java.time.Duration;

public class DialTone extends SoundGenerator implements WaveFunctions {

    OnOffFilter filter;

    public DialTone(Duration duration) {
        super(duration);
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            float t = i * timeStep;
            float sample = (float)(sin(350, t) + 0.1 * sin(440, t));
            channels.write(sample);
        }
    }
}
