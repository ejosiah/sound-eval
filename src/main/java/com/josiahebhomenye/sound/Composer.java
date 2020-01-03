package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;

public class Composer {

    public static void main(String[] args) throws Exception{
        Duration duration = Duration.ofSeconds(10);
     //   SoundGenerator sound = new ConstantSoundSignal(Short.MAX_VALUE, duration);
      //  SoundGenerator sound = new OverTones(440, 4, duration);
     //   SoundGenerator sound = new BusySignal(duration);
          SoundGenerator sound = new SineGenerator(440, duration);

        //  SoundFilter filter = new OnOffFilter(0.4, 0.2, 0.4, 2.0);
      //  SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "sine.wav");

       // SoundFilter filter = new LineFilter(Duration.ofSeconds(1), Duration.ofSeconds(9), duration);
       // SoundFilter filter = new DecayFilter(duration, 1);
       SoundFilter filter = new IdentityFilter();
        SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "sine.wav");
        System.out.println("done");
    }
}
