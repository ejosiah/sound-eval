package com.josiahebhomenye.sound;

import java.time.Duration;

public class Composer {

    public static void main(String[] args) throws Exception{
        Duration duration = Duration.ofSeconds(20);
        SoundGenerator sound = new SineGenerator(400, duration);
        SoundFilter filter = new OnOffFilter(0.4, 0.2, 0.4, 2.0);
        SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "sine.wav");
      //  SamplesUtil.save( sound.samples(), sound.format(), "sine.wav");
        System.out.println("done");
    }
}
