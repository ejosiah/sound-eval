package com.josiahebhomenye.sound;

import java.time.Duration;

public class Composer {

    public static void main(String[] args) throws Exception{
        Duration duration = Duration.ofSeconds(20);
        SoundGenerator tone = new SineGenerator(440, duration);
        SoundFilter filter = new OnOffFilter(2.0f, 4.0f);
        SamplesUtil.save( filter.filter(tone.samples()), tone.format(), "sine.wav");
      //  SamplesUtil.save( tone.samples(), tone.format(), "sine.wav");
        System.out.println("done");
    }
}
