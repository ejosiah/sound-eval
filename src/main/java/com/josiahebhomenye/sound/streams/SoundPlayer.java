package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.Sound;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.Arrays;
import java.util.List;


public class SoundPlayer implements Consumer<byte[]>,  Sound {

    @Getter
    @Accessors(fluent = true)
    private final AudioFormat format;
    private SourceDataLine line;

    public SoundPlayer(){
        this(DEFAULT_FORMAT);
    }

    public SoundPlayer(AudioFormat format) {
        this.format = format;
    }

    @SneakyThrows
    public void start(){
        line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();
    }

    @Override
    public void accept(byte[] bytes) throws Throwable {
     //   System.out.println(Arrays.toString(bytes));
       line.write(bytes, 0, bytes.length);
    }

    public void stop(){
        if(line != null) {
            line.drain();
            line.stop();
            line.close();
        }
    }

//    @Override
//    public void accept(List<byte[]> byteStream) throws Throwable {
//        int size = byteStream.stream().map(b -> b.length).reduce(Integer::sum).get();
//        byte[] sum = new byte[size];
//        int next = 0;
//        for(byte[] bytes : byteStream){
//            for(int i = 0; i < bytes.length; i++, next++){
//                sum[next] = bytes[i];
//            }
//        }
//        line.write(sum, 0, sum.length);
//    }
}
