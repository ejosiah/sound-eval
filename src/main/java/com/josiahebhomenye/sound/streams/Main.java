package com.josiahebhomenye.sound.streams;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.SneakyThrows;

public class Main {
    public static void main(String[] args) throws Exception {
        AudioSource audioSource = new SineWaveSource(440);
       Disposable d =  Flowable.fromPublisher(audioSource).subscribe(System.out::println);
        Thread t = new Thread(() -> {
           delay(100);
           audioSource.stop();
           System.out.println("stopped");
       });
        t.start();
        t.join();
    }

    @SneakyThrows
    public static void delay(long t){
        Thread.sleep(t);
    }
}
