package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;

public interface Sound {

    AudioFormat.Encoding DEFAULT_ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    float DEFAULT_FRAME_RATE = 44100;
    int DEFAULT_SAMPLE_SIZE_IN_BITS = 16;
    int DEFAULT_CHANNELS = 2;
    int DEFAULT_FRAME_SIZE = 4;
    boolean IS_BIG_ENDIAN = false;

    AudioFormat DEFAULT_FORMAT = new AudioFormat(
            DEFAULT_ENCODING,
            DEFAULT_FRAME_RATE,
            DEFAULT_SAMPLE_SIZE_IN_BITS,
            DEFAULT_CHANNELS,
            DEFAULT_FRAME_SIZE,
            DEFAULT_FRAME_RATE,
            IS_BIG_ENDIAN
    );

    AudioFormat format();


}
