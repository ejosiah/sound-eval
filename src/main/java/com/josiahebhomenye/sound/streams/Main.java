package com.josiahebhomenye.sound.streams;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Supplier;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.start();
        AudioToFile audioToFile = new AudioToFile(44100 * 10, "sine.wav");
        AudioSource audioSource = new SineWaveSource(440);
       // AudioSource audioSource = new FileSource("sine.wav");

        Thread t = new Thread(() -> {
            delay(10000);
            audioSource.stop();
            soundPlayer.stop();
            System.out.println("stopped");
        });
        t.start();
       Disposable d =  Flowable.fromPublisher(audioSource).map(new ConvertToBytes()).buffer(100).map(Reducer.INSTANCE).subscribe(audioToFile);


        t.join();
        System.out.println("Main has now finished");
    }

    @SneakyThrows
    public static void delay(long t){
        Thread.sleep(t);
    }
}
