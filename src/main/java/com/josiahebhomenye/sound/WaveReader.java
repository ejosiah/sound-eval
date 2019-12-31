package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WaveReader {

    public String read(InputStream in) throws Exception{
        byte[] buf = new byte[4];
        int read = in.read(buf);

        return new String(buf);
    }
}
