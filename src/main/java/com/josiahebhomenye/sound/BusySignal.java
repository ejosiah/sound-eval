package com.josiahebhomenye.sound;

import java.time.Duration;

public class BusySignal extends SoundGenerator implements WaveFunctions {

    private OnOffFilter filter;

    public BusySignal(Duration duration) {
        super(duration);
        this.filter = new OnOffFilter(0.5, 0.5);
    }

    @Override
    public void generate() {
        double low = 480;
        double high = 620;

        for(int i = 0; i < nSamples; i++){
            double t = i * timeStep;
            double sample = (sin(low, t) + sin(high, t))/2;
            channels.write(sample);
        }
        filter.filter(buffer.array());
    }
}
