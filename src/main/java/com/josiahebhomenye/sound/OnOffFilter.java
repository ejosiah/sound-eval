package com.josiahebhomenye.sound;

import java.time.Duration;
import java.util.Arrays;

public class OnOffFilter extends SoundFilter {



    private int count;
    private Switch curSwitch;
    private CyclingIterator<Switch> itr;

    protected OnOffFilter(double... timeCycles) {
        int frameSize = format.getFrameSize();
        int sampleSize = frameSize/format.getChannels();

        Switch.State state = Switch.State.ON;

        Switch[] switches = new Switch[timeCycles.length];
        for(int i = 0; i < timeCycles.length; i++){
            double time = timeCycles[i];
            switches[i] = new Switch((int)(format.getFrameRate() * sampleSize * time/sampleSize), state);
            state = state.invert();
        }
        itr = new CyclingIterator<>(switches);
        curSwitch = itr.next();
    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        int nChannels = format.getChannels();
        int n = length/(format.getSampleSizeInBits()/8);
        for(int i = offset; i < n; i += nChannels, count++){
            toggleSwitch();
            for(int j = 0; j < nChannels; j++){
                int index = i + j;
                int sample = curSwitch.state.value * get(samples, index);
                set(samples, sample, index);
            }
        }

    }

    private void toggleSwitch(){
        if(count >= curSwitch.duration){
            curSwitch = itr.next();
            count = 0;
        }
    }
}
