package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.stream.IntStream;
import static java.nio.ByteOrder.*;

public interface Sound {

    AudioFormat.Encoding DEFAULT_ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    float DEFAULT_FRAME_RATE = 44100;
    int DEFAULT_SAMPLE_SIZE_IN_BITS = 16;
    int DEFAULT_CHANNELS = 2;
    int DEFAULT_FRAME_SIZE = (DEFAULT_SAMPLE_SIZE_IN_BITS/8) * DEFAULT_CHANNELS;
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


    class Channels extends ArrayList<Channels.Channel> {

        private final ByteBuffer samples;
        private final AudioFormat format;
        private final int nChannels;

        public Channels(byte[] samples, AudioFormat format){
            this(ByteBuffer.wrap(samples), format);
        }

        public Channels(ByteBuffer samples, AudioFormat format) {
            this.format = format;
            this.samples = samples.order(format.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN);
            IntStream.range(0, format.getChannels()).forEach(i -> add(new Channel(i)));
            this.nChannels = size();
        }

        public int nSamples(){
            return samples.array().length/format.getFrameSize();
        }

        class Channel{
            private final int offset;

            Channel(int offset) {
                this.offset = offset;
            }

            public void write(Integer sample, Integer pos){
                int index = index(pos);
                if(format.getSampleSizeInBits() == Short.SIZE){
                    samples.asShortBuffer().put(index, sample.shortValue());
                }else if(format.getSampleSizeInBits() == Integer.SIZE){
                    samples.asIntBuffer().put(index(pos), sample);
                }else {
                    samples.put(index(pos), sample.byteValue());
                }
            }

            public int get(Integer pos){
                if(format.getSampleSizeInBits() == Short.SIZE){
                    return samples.asShortBuffer().get(index(pos));
                }else if(format.getSampleSizeInBits() == Integer.SIZE){
                    return samples.asIntBuffer().get(index(pos));
                }else {
                    return samples.get(index(pos));
                }
            }

            public void write(Integer sample){
                if(format.getSampleSizeInBits() == Short.SIZE){
                    samples.asShortBuffer().put(sample.shortValue());
                }else if(format.getSampleSizeInBits() == Integer.SIZE){
                    samples.asIntBuffer().put(sample);
                }else {
                    samples.put(sample.byteValue());
                }
            }

            public int get(){
                if(format.getSampleSizeInBits() == Short.SIZE){
                    return samples.asShortBuffer().get();
                }else if(format.getSampleSizeInBits() == Integer.SIZE){
                    return samples.asIntBuffer().get();
                }else {
                    return samples.get();
                }
            }

            int index(int pos){
                return nChannels * pos + offset;
            }
        }
    }

}
