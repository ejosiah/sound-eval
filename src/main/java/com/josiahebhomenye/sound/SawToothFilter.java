package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.stream.IntStream;

public class SawToothFilter extends SoundFilter {


    @Override
    public void filter(byte[] samples, int offset, int length) {
        long time = (long)((length/format.getFrameSize())/format.getSampleRate());
        Duration duration = Duration.ofSeconds(time);
        float[] multiplier = new SquareWave(16, duration).fSamples();
        Channels channels = new Channels(samples, format);
        IntStream.range(0, channels.nSamples()).forEach(i -> {
            channels.forEach(channel -> {
                Float sample = channel.get(i) * multiplier[i];
                channel.write(sample.intValue(), i);
            });
        });
    }
}
