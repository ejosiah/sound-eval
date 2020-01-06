package com.josiahebhomenye.sound;

public class Bits {

    public static byte getByte(int sample, int index, boolean bigEndian){
        if(index < 0 || index > 3) throw new IndexOutOfBoundsException(String.format("%s is out of range 0:3", index));
        return (byte)(sample << (index * 8));
    }

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
