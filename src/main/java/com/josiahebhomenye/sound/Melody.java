package com.josiahebhomenye.sound;

import com.josiahebhomenye.sound.music.Note;

import java.time.Duration;
import static com.josiahebhomenye.sound.music.Note.*;

public class Melody extends SoundGenerator {

    Note[] notes = {C_4, D_4, E_4, F_4, G_4, A_4, B_4, C_5};
    private static final Duration duration = Duration.ofSeconds(32);

    public Melody() {
        super(duration);

    }

    @Override
    public void generate() {
        Duration duration = Duration.ofSeconds(4);
        DecayFilter filter = new DecayFilter(duration, 1);

        for(Note note : notes){
            byte[] samples = new OverTones(note.getValue(), 4, duration).samples();
            samples = filter.filter(samples);
            buffer.put(samples);
        }
    }
}
