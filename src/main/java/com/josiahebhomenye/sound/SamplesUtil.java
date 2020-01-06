package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.nio.ByteOrder.*;

public class SamplesUtil {

    public static void save(double[] samples, AudioFormat format, String filename) throws IOException {
        byte[] bSamples = new byte[samples.length * format.getFrameSize()];
        ShortBuffer sBuf = ByteBuffer.wrap(bSamples).asShortBuffer();


        for(int i = 0; i < samples.length; i++){
            sBuf.put(i, convertToShort(samples[i]));
        }

        save(bSamples, format, filename);
    }

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

    public static Duration getDuration(AudioFileFormat format){
        long durationInSeconds = (long)(format.getFrameLength()/(format.getFormat().getFrameRate() * format.getFormat().getFrameSize()));
        return Duration.ofSeconds(durationInSeconds);
    }

    public static long length(byte[] samples, AudioFormat format){
        return  samples.length/format.getFrameSize();
    }

    public static short convertToShort(double x){
        if(x < -1 || x > 1) throw new IllegalArgumentException(String.format("%s out of range x should be in the rage -1:1", x));
        double max = 1;
        double min = -1;
        double a = Short.MIN_VALUE;
        double b = Short.MAX_VALUE;

        double num = (b-a) * (x-min);
        double denum = max - min;

        return (short)(num/denum + a);
    }

    public static int convertToInt(double x){
        if(x < -1 || x > 1) throw new IllegalArgumentException(String.format("%s out of range x should be in the rage -1:1", x));
        double max = 1;
        double min = -1;
        double a = Integer.MIN_VALUE;
        double b = Integer.MAX_VALUE;

        double num = (b-a) * (x-min);
        double denum = max - min;

        return (int)(num/denum + a);
    }

    public static Double scaleToUnit(double x, double min, double max){
        return (2 * (x - min))/(max - min) - 1;
    }

    public static float[] convertToFloats(byte[] samples, AudioFormat format){
        ByteBuffer buf = ByteBuffer.wrap(samples).order(format.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN);
        if(format.getSampleSizeInBits() == Short.SIZE){
            return convert(buf.asShortBuffer());
        }else if(format.getSampleSizeInBits() == Integer.SIZE){
            return convert(buf.asIntBuffer());
        }else {
            float[] fSamples = new float[samples.length];
            for(int i = 0; i < samples.length; i++){
                fSamples[i] = scaleToUnit(samples[i], Byte.MIN_VALUE, Byte.MAX_VALUE).floatValue();
            }
            return fSamples;
        }
    }

    private static float[] convert(ShortBuffer buffer){
        float[] fSamples = new float[buffer.limit()];
        for(int i = 0; i < fSamples.length; i++){
            fSamples[i] = scaleToUnit(buffer.get(), Short.MIN_VALUE, Short.MAX_VALUE).floatValue();
        }
        return fSamples;
    }

    private static float[] convert(IntBuffer buffer){
        float[] fSamples = new float[buffer.limit()];
        for(int i = 0; i < fSamples.length; i++){
            fSamples[i] = scaleToUnit(buffer.get(), Integer.MIN_VALUE, Integer.MAX_VALUE).floatValue();
        }
        return fSamples;
    }

    public static byte[] convertToBites(float[] samples, AudioFormat format){
        int sampleSize = format.getSampleSizeInBits();
        byte[] bSample = new byte[samples.length * (sampleSize/Byte.SIZE)];
        ByteBuffer buf = ByteBuffer.wrap(bSample).order(format.isBigEndian()? BIG_ENDIAN : LITTLE_ENDIAN);
        for(int i = 0; i < samples.length; i++){
            if(sampleSize == Short.SIZE){
                short sample = convertToShort(samples[i]);
                buf.asShortBuffer().put(i, sample);
            }else if(sampleSize == Integer.SIZE){
                int sample = convertToInt(samples[i]);
                buf.asIntBuffer().put(i, sample);
            }
        }
        return buf.array();
    }


}
