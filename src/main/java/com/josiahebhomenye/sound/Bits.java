package com.josiahebhomenye.sound;

public class Bits {

    public static byte[] shortToBytes(short sample, boolean bigEndian){
        byte[] buf =  new byte[2];
        shortToBytes(sample, buf, bigEndian);
        return buf;
    }

    public static void shortToBytes(short sample, byte[] buf, boolean bigEndian){
        if(bigEndian){
            buf[0] = (byte)((sample >> 8) & 0xFF);
            buf[1] = (byte)(sample & 0xFF);
        }else{
            buf[0] = (byte)(sample & 0xFF);
            buf[1] = (byte)((sample >> 8) & 0xFF);
        }
    }

    public static  byte[] intToBytes(int sample, boolean bigEndian){
        byte[] buf =  new byte[4];
        intToBytes(sample, buf, bigEndian);
        return buf;
    }

    public static void intToBytes(int sample, byte[] buf, boolean bigEndian){
        if(bigEndian){
            buf[0] = (byte)((sample >> 24) & 0xFF);
            buf[1] = (byte)((sample >> 16) & 0xFF);
            buf[2] = (byte)((sample >> 8) & 0xFF);
            buf[3] = (byte)(sample & 0xFF);
        }else{
            buf[0] = (byte)(sample & 0xFF);
            buf[1] = (byte)((sample >> 8) & 0xFF);
            buf[2] = (byte)((sample >> 16) & 0xFF);
            buf[3] = (byte)((sample >> 24) & 0xFF);
        }
    }
}
