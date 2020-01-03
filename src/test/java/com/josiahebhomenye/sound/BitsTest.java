package com.josiahebhomenye.sound;

import org.junit.Test;
import static java.nio.ByteOrder.*;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

public class BitsTest {

    @Test
    public void convert_big_endian_shorts_to_bytes(){
        IntStream.rangeClosed(Short.MIN_VALUE, Short.MAX_VALUE).forEach(i -> {
            short expected = (short)i;
            short actual = ByteBuffer.wrap(Bits.shortToBytes(expected, true)).getShort();
            assertEquals(expected, actual);
        });
    }

    @Test
    public void convert_little_endian_shorts_to_bytes(){
        IntStream.rangeClosed(Short.MIN_VALUE, Short.MAX_VALUE).forEach(i -> {
            short expected = (short)i;
            short actual = ByteBuffer.wrap(Bits.shortToBytes(expected,false)).order(LITTLE_ENDIAN).getShort();
            assertEquals(expected, actual);
        });
    }

    @Test
    public void convert_big_endian_ints_to_bytes(){
        LongStream.rangeClosed(Integer.MIN_VALUE, Integer.MAX_VALUE).forEach(i -> {
            int expected = (int)i;
            int actual = ByteBuffer.wrap(Bits.intToBytes(expected, true)).getInt();
            assertEquals(expected, actual);
        });
    }

    @Test
    public void convert_little_endian_ints_to_bytes(){
        LongStream.rangeClosed(Integer.MIN_VALUE, Integer.MAX_VALUE).forEach(i -> {
            int expected = (int)i;
            int actual = ByteBuffer.wrap(Bits.intToBytes(expected, false)).order(LITTLE_ENDIAN).getInt();
            assertEquals(expected, actual);
        });
    }



}