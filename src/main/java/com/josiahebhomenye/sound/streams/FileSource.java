package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.SamplesUtil;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileSource extends AudioSource {

    ByteBuffer buffer;

    @SneakyThrows
    public FileSource(String filename){
        AudioInputStream in = AudioSystem.getAudioInputStream(new File(filename));
        byte[] buf = new byte[1024 * 1024];
        buffer = ByteBuffer.allocate(1024 * 1024 * 1024).order(ByteOrder.LITTLE_ENDIAN);
        int read;
        while((read = in.read(buf)) != -1){
            buffer.put(buf);
        }
        buffer.flip();
    }

    @Override
    protected float nextSample() {
        if(buffer.hasRemaining()){
            return SamplesUtil.scaleToUnit(buffer.getShort(), Short.MIN_VALUE, Short.MAX_VALUE).floatValue();
        }else{
            stop();
            return 0;
        }
    }
}
