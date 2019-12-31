package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Samples {
    public final AudioFormat format;
    public final ByteBuffer samples;

    public Samples(AudioFormat format, byte[] samples) {
        this.format = format;
        this.samples = ByteBuffer.wrap(samples).order(order(format));
    }

    public static ByteOrder order(AudioFormat format){
        return format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
    }

    public int get(int i){
        if(format.getSampleSizeInBits() == Short.SIZE){
            return samples.getShort(i);
        }else if(format.getSampleSizeInBits() == Integer.SIZE){
            return samples.getInt(i);
        }else{
            return samples.get(i);
        }
    }

    public void set(Integer sample, int i){
        if(format.getSampleSizeInBits() == Short.SIZE){
            samples.putShort(i, sample.shortValue());
        }else if(format.getSampleSizeInBits() == Integer.SIZE){
            samples.putInt(i, sample);
        }else{
            samples.put(i, sample.byteValue());
        }
    }
}
