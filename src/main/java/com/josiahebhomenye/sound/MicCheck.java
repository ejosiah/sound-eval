package com.josiahebhomenye.sound;

import com.josiahebhomenye.sound.Sound;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.*;
import java.time.Duration;
import java.util.function.Supplier;

public class MicCheck {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TargetDataLine line = AudioSystem.getTargetDataLine(Sound.DEFAULT_FORMAT);
        line.open();
        line.start();

        Thread t = new Thread(() -> readFromLine(line, out));
        t.start();
        t.join();

      //  line.drain();
        line.stop();
        line.close();
        byte[] buf = out.toByteArray();

       AudioInputStream in = new AudioInputStream(new ByteArrayInputStream(buf), Sound.DEFAULT_FORMAT, buf.length );
       AudioSystem.write(in, AudioFileFormat.Type.WAVE, new File("explosion0.wav"));
    }

    @SneakyThrows
    public static void sleep(long time){
        Thread.sleep(time);
    }

    @SneakyThrows
    public static void readFromLine(TargetDataLine line, OutputStream out){
        long _20Seconds = Duration.ofSeconds(5).toMillis();
        long startTime = System.currentTimeMillis();

        Supplier<Long> elapsedTime = () -> System.currentTimeMillis() - startTime;

        byte[] buf = new byte[1024];
        while(elapsedTime.get() < _20Seconds){
            line.read(buf, 0, buf.length);
            out.write(buf);
       //     System.out.println("read data");
        }
    }
}
