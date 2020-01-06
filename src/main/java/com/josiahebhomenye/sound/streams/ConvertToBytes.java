package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.Bits;
import com.josiahebhomenye.sound.SamplesUtil;
import com.josiahebhomenye.sound.Sound;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Function;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;
import java.util.Arrays;
import static com.josiahebhomenye.sound.SamplesUtil.*;
public class ConvertToBytes implements Function<Float, byte[]>, Sound {

    @Getter
    @Accessors(fluent = true)
    private final AudioFormat format;
    private final int sampleSize;
    private byte[] buf;

    public ConvertToBytes(){
        this(DEFAULT_FORMAT);
    }

    public ConvertToBytes(AudioFormat format) {
        this.format = format;
        this.sampleSize = format.getSampleSizeInBits() / 8;
        buf = new byte[sampleSize];
    }

    @Override
    public byte[] apply(@NonNull Float sample){
        byte[] buf = new byte[sampleSize];
        if (sampleSize == Short.BYTES) {
            short sSample = convertToShort(sample);
            Bits.shortToBytes(sSample, buf, format.isBigEndian());
        } else if (sampleSize == Integer.BYTES) {
           Bits.intToBytes(convertToInt(sample), buf, format.isBigEndian());
        }
        return buf;
    }
}
