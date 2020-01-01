package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class SoundGenerator implements Sound {


    protected final AudioFormat format;
    protected final ByteBuffer buffer;
    protected final Channels channels;
    protected final int sampleSizeInBits;
    protected final int nSamples;
    protected final double duration;
    protected final double timeStep;
    protected final double frameSize;

    private boolean generated;

    public SoundGenerator(Duration duration){
        this(DEFAULT_FORMAT, duration);
    }

    public SoundGenerator(AudioFormat format, Duration duration){
        this.format = format;
        this.duration = duration.toMillis()/1000.0f;
        this.timeStep = 1/format.getSampleRate();
        this.frameSize = format.getFrameSize();
        int size = (int)(this.duration * format.getFrameRate() * format.getFrameSize());
        this.nSamples = (int)(size/frameSize);
        byte[] data = new byte[size];
        this.buffer = ByteBuffer.wrap(data).order(format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.channels = new Channels(format.getChannels());
        this.sampleSizeInBits = format.getSampleSizeInBits();
    }

    public abstract void generate();

    public byte[] samples(){
        ensureGenerated();
        return Arrays.copyOf(buffer.array(), buffer.limit());
    }

    public AudioFormat format(){
        return format;
    }

    protected void ensureGenerated(){
        if(!generated){
            generate();
            generated = true;
        }
    }

    protected final class Channels extends ArrayList<Channel>{
        Channels(int nChannels){
            IntStream.range(0, nChannels).forEach(i -> add(new Channel()));
        }

        void write(double sample){
            forEach(c -> c.write(sample));
        }

        void write(short sample){
            forEach(c -> c.writeShort(sample));
        }

        void writeInt(int sample){
            forEach(c -> c.writeInt(sample));
        }
    }

   private final class Channel{

        void write(double sample){
            if(sampleSizeInBits== Short.SIZE){
                buffer.putShort((short)(sample * Short.MAX_VALUE));
            }else if(sampleSizeInBits == Integer.SIZE){
                buffer.putInt((short)(sample * Short.MAX_VALUE));
            }
        }

        void writeShort(short sample){
            if(sampleSizeInBits == Integer.SIZE){
                buffer.putInt(sample);
            }else {
                buffer.putShort(sample);
            }
        }

        void writeInt(int sample){
            if(sampleSizeInBits == Short.SIZE){
                buffer.putShort((short)sample);
            }else {
                buffer.putInt(sample);
            }
        }
   }
}
