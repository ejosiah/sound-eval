package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;

import java.io.IOException;
import java.io.InputStream;

public abstract class AudioSource extends InputStream implements Sound {

    protected final AudioFormat format;
    protected final float[] frameBuf;

    protected AudioSource(){
        this(DEFAULT_FORMAT);
    }

    protected AudioSource(AudioFormat format) {
        this.format = format;
        this.frameBuf = new float[format.getChannels()];
    }


    @Override
    public int read() throws IOException {
        return 0;
    }

    protected abstract float[] nextFrame();
}
