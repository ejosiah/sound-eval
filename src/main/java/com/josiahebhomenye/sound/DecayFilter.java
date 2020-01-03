package com.josiahebhomenye.sound;

import java.time.Duration;

public class DecayFilter extends SoundFilter {


    private final double rate;

    protected DecayFilter(Duration duration) {
        super(duration);
        this.rate = 1.0;
    }

    protected DecayFilter(Duration duration, double rate) {
        super(duration);
        this.rate = rate;
    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        Channels channels = new Channels(samples, format);

        for(int i = offset; i < channels.nSamples(); i++, index++){
            double t = index * timeStep;
            for(Channels.Channel channel : channels){
                Double sample = channel.get(i) * Math.exp(-t * rate);
                channel.write(sample.intValue(), i);
            }
        }
        index %= nSamples;
    }
}
