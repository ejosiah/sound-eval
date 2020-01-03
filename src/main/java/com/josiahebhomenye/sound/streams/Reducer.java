package com.josiahebhomenye.sound.streams;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;

import java.util.List;
import java.util.Map;

public class Reducer implements Function<List<byte[]>, byte[]> {

    public static Reducer INSTANCE = new Reducer();

    private Reducer(){

    }

    @Override
    public byte[] apply(@NonNull List<byte[]> byteStream) throws Throwable {
        int size = byteStream.stream().map(b -> b.length).reduce(Integer::sum).get();
        byte[] sum = new byte[size];
        int pos = 0;
        for(byte[] bytes : byteStream){
            System.arraycopy(bytes, 0, sum, pos, bytes.length);
            pos += bytes.length;
        }

        return sum;
    }
}
