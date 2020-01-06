package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;

public class Composer {

    public static void main(String[] args) throws Exception{
        Duration duration = Duration.ofSeconds(3);
     //   SoundGenerator sound = new ConstantSoundSignal(Short.MAX_VALUE, duration);
     //   SoundGenerator sound = new OverTones(1760, 4, duration);
     //   SoundGenerator sound = new BusySignal(duration);
          SoundGenerator sound = new SineGenerator(440, duration);
     //   SoundGenerator sound = new Melody();
     //   SoundGenerator sound = new HumptyDumpty();
    //    SoundGenerator sound = new SawTootheWave(16, Duration.ofSeconds(20));

        //  SoundFilter filter = new OnOffFilter(0.4, 0.2, 0.4, 2.0);
      //  SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "sine.wav");

       // SoundFilter filter = new LineFilter(Duration.ofSeconds(1), Duration.ofSeconds(9), duration);
      //  SoundFilter filter = new DecayFilter(duration, 3);
      //  SoundFilter filter = new IdentityFilter();
        SoundFilter filter = new SawToothFilter();
        SamplesUtil.save( filter.filter(sound.samples()), sound.format(), "saw_tooth.wav");
        System.out.println("done");
    }
}
