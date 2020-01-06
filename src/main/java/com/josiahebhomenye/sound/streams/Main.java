package com.josiahebhomenye.sound.streams;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.SneakyThrows;

public class Main {
    public static void main(String[] args) throws Exception {
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.start();
        AudioToFile audioToFile = new AudioToFile(44100 * 10, "sine.wav");
        AudioSource audioSource = new SineWaveSource(440);
       // AudioSource audioSource = new FileSource("sine.wav");

        Thread t = new Thread(() -> {
            delay(20000);
            audioSource.stop();
            soundPlayer.stop();
            System.out.println("stopped");
        });
        t.start();
       Disposable d =
               Flowable
                   .fromPublisher(audioSource)
                   .map(new DecayFilter())
                   .map(new ConvertToBytes()).buffer(1024)
                   .map(Reducer.INSTANCE)
                   .subscribe(soundPlayer);


        t.join();
        d.dispose();
        System.out.println("Main has now finished");
    }

    @SneakyThrows
    public static void delay(long t){
        Thread.sleep(t);
    }
}
