package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.nio.ByteOrder.*;

public class SamplesUtil {

    public static void save(byte[] samples, AudioFormat format, String filename) throws IOException {
        File file = new File(filename);
        AudioInputStream ain = new AudioInputStream(new ByteArrayInputStream(samples), format, samples.length);
        AudioSystem.write(ain, AudioFileFormat.Type.WAVE, file);
    }

    public static int getSample(byte[] samples, AudioFormat format, int index){
        ByteBuffer buf = ByteBuffer.wrap(samples).order(format.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN );    // TODO optimize later
        if(format.getSampleSizeInBits() == Short.SIZE){
            return buf.asShortBuffer().get(index);
        }else if(format.getSampleSizeInBits() == Integer.SIZE){
            return buf.asIntBuffer().get(index);
        }
        return samples[index];
    }

    public static void setSample(byte[] samples, Integer sample, AudioFormat format, int index){
        ByteBuffer buf = ByteBuffer.wrap(samples).order(format.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN );    // TODO optimize later
        if(format.getSampleSizeInBits() == Short.SIZE){
            buf.asShortBuffer().put(index, sample.shortValue());
        }else if(format.getSampleSizeInBits() == Integer.SIZE){
            buf.asIntBuffer().put(index, sample);
        }

    }
}
