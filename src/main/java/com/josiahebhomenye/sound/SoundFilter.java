package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;
import java.time.Duration;

public abstract class SoundFilter implements Sound {

    protected AudioFormat format;
    protected double duration;
    protected double timeStep;
    protected int nSamples;
    protected int index;

    public SoundFilter(){
        this(Duration.ZERO);
    }

    protected SoundFilter(Duration duration){
        this(DEFAULT_FORMAT, duration);
    }

    protected SoundFilter(AudioFormat format, Duration duration) {
        this.format = format;
        this.duration = duration.toMillis()/1000.0f;
        this.timeStep = 1/format.getSampleRate();
        this.nSamples = (int)(format.getFrameRate() * format.getFrameSize() * this.duration)/format.getFrameSize();
        this.index = 0;
    }



    public void reset(){

    }

    public int getRemainingBytes(){
        return 0;
    }

    public byte[] filter(byte[] samples){
        filter(samples, 0, samples.length);
        return samples;
    }

    public abstract void filter(byte[] samples, int offset, int length);

    public AudioFormat format(){
        return format;
    }

    public int get(byte[] samples, int index){
        return SamplesUtil.getSample(samples, format, index);
    }

    public void set(byte[] samples, int sample, int index){
        SamplesUtil.setSample(samples, sample, format, index);
    }
}
