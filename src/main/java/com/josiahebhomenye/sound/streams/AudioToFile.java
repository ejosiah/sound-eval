package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.SamplesUtil;
import com.josiahebhomenye.sound.Sound;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.sound.sampled.AudioFormat;
import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
public class AudioToFile implements Consumer<byte[]>, Sound {

    @Getter
    @Accessors(fluent = true)
    private final AudioFormat format;
    private final int maxSamples;
    private final ByteArrayOutputStream out;
    private final String filename;
    private int samples;

    public AudioToFile(int maxSamples, String filename){
        this(DEFAULT_FORMAT, maxSamples, filename);
    }

    public AudioToFile(AudioFormat format, int maxSamples, String filename){
        this.format = format;
        this.maxSamples = maxSamples * format.getFrameSize();
        this.filename = filename;
        out = new ByteArrayOutputStream();
    }

    @Override
    public void accept(byte[] bytes) throws Throwable {
        out.write(bytes);
        samples += bytes.length;
        if(samples >= maxSamples){
            SamplesUtil.save(out.toByteArray(), format, filename);
            throw new DownStreamClosedException();
        }
    }
}
