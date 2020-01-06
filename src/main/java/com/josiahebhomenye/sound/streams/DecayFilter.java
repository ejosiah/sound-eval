package com.josiahebhomenye.sound.streams;

public class DecayFilter extends AudioFilter {
    private final float timeStep;
    private float time;
    private float channel;

    public DecayFilter(){
        timeStep = 1/format().getSampleRate();
    }

    @Override
    protected Float filter(Float sample) {
        float newSample = (float)Math.exp(-time) * sample;
        channel++;
        channel %= format().getChannels();
        if(channel == 0){
            time += timeStep;
        }
        return newSample;
    }
}
