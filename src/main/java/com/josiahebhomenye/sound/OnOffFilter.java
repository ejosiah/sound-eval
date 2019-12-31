package com.josiahebhomenye.sound;

import java.time.Duration;

public class OnOffFilter extends SoundFilter {


    private final int offSamples;
    private final int onSamples;
    private int aSwitch;
    private int count;

    protected OnOffFilter(float on, float off) {
        int frameSize = format.getFrameSize();
        int sampleSize = frameSize/format.getChannels();
        this.offSamples = (int)(format.getFrameRate() * sampleSize * off/sampleSize);
        this.onSamples = (int)(format.getFrameRate() * sampleSize * on/sampleSize);
        this.aSwitch = on <= 0 ? 0 : 1;
    }

    @Override
    public void filter(byte[] samples, int offset, int length) {
        int nChannels = format.getChannels();
        int n = length/(format.getSampleSizeInBits()/8);
        for(int i = offset; i < n; i += nChannels, count++){
            for(int j = 0; j < nChannels; j++){
                int index = i + j;
                int sample = aSwitch * get(samples, index);
                set(samples, sample, index);
            }
            toggleSwitch();
        }

    }

    private void toggleSwitch(){
        if(aSwitch == 1 && offSamples != 0 && onSamples != 0 && count%onSamples == 0){
            aSwitch = 0;
            count = 0;
        }else if(aSwitch == 0 && onSamples != 0 && offSamples != 0 && count%offSamples == 0){
            aSwitch = 1;
            count = 0;
        }
    }
}
