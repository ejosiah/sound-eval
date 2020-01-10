package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.WaveFunctions;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SineWaveSource extends AudioSource implements WaveFunctions {

    private final float frequency;

    static int x = 0;

    @Override
    protected float nextSample() {
        float y = (float)sin(frequency, time);
//        if(x < 100) {
//            System.out.printf("f: %s, t: %s, y : %s\n", frequency, time, y);
//            x++;
//        }
        return y;
    }
}
