package com.josiahebhomenye.sound;

public class WaveFile {
    private final char[] chunkID = {'R', 'I', 'F', 'F'};
    private int chunkSize;
    private int format;
    private int subChunk1ID;
    private int subChunk1Size;
    private short audioFormat;
    private short numChannels;
    private int sampleRate;
    private int byteRate;
    private short blockAlign;
    private short bitsPerSample;
    private int subChunk2ID;
    private int subChunk2Size;
    private byte[] data;
}
