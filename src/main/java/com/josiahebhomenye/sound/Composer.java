package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;

public class Composer {

    public static void main(String[] args) throws Exception{
        Duration duration = Duration.ofSeconds(1);
       // SoundGenerator sound = new ConstantSoundSignal(Short.MAX_VALUE, duration);
//        SoundGenerator sound = new OverTones(440, 4, duration);
        SoundGenerator sound = new SineGenerator(440, duration);

        //  SoundFilter filter = new OnOffFilter(0.4, 0.2, 0.4, 2.0);
      //  SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "sine.wav");

       // SoundFilter filter = new LineFilter(Duration.ofSeconds(5), Duration.ofSeconds(5), duration);
       // SoundFilter filter = new DecayFilter(duration);
        SamplesUtil.save( sound.samples(), sound.format(), "constant.wav");
        System.out.println("done");
    }
}
