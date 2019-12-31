package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Random;

public class NoiseGenerator extends SoundGenerator {
    private static Random rng = new Random();

    public NoiseGenerator(Duration duration) {
        super(duration);
    }

    @Override
    public void generate() {
        for(int i = 0; i < nSamples; i++){
            channels.writeInt(rng.nextInt());
        }
    }
}
