package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.stream.IntStream;

public class ConstantSoundSignal extends SoundGenerator {

    private final int value;

    public ConstantSoundSignal(int value, Duration duration) {
        super(duration);
        this.value = value;
    }

    @Override
    public void generate() {
      //  IntStream.range(0, nSamples).forEach(i -> channels.writeInt(value));
    }
}
