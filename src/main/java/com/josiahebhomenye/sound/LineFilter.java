package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.stream.IntStream;

public class LineFilter extends SoundFilter {

    private Double attachSamples;
    private Double decaySamples;


    public LineFilter(Duration attack, Duration decay, Duration duration){
        super(duration);
        this.attachSamples = (attack.toMillis()/1000.0) * format.getFrameRate();
        this.decaySamples = (decay.toMillis()/1000.0) * format.getFrameRate();

    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        Channels channels = new Channels(samples, format);
        double slope = 1/attachSamples;
        for(int i = 0; i < attachSamples; i++){
            double volume = i * slope;
            int pos = i;
            channels.forEach(channel -> {
                Double sample = volume * channel.get(pos);
                channel.write(sample.intValue(), pos);
            });

        }
        slope = 1/decaySamples;
        for(int i = 0; i < (nSamples - attachSamples); i++){
            double volume = Math.max(1 - i * slope, 0);
            int pos = i + attachSamples.intValue();
            channels.forEach(channel -> {
                Double sample = volume * channel.get(pos);
                channel.write(sample.intValue(), pos);
            });
        }
    }
}
