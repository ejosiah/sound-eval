package com.josiahebhomenye.sound.streams;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SineWaveSource extends AudioSource {

    private final float frequency;

    @Override
    protected float nextSample() {
        return (float)Math.sin(2 * Math.PI * frequency * time);
    }
}
