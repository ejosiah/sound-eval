package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.Sound;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.sound.sampled.AudioFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Accessors(fluent = true)
@Getter
public abstract class AudioSource implements Publisher<Float> , Sound {

    private static final Random RNG = new Random();

    protected final AudioFormat format;
    protected final float timeStep;
    protected int currentChannel;
    protected float time;
    protected int curChannel;
    protected AudioSourceSubscription subscription;

    protected AudioSource(){
        this(DEFAULT_FORMAT);
    }

    protected AudioSource(AudioFormat format) {
        this.format = format;
        this.timeStep = 1/format.getFrameRate();
        this.currentChannel = 0;
    }

    @Override
    public void subscribe(Subscriber<? super Float> s) {
        subscription = new AudioSourceSubscription(s);
    }

    protected abstract float nextSample();

    public void stop(){
        subscription.cancel();
    }

    private class AudioSourceSubscription implements Subscription{

        private final Subscriber<? super Float> subscriber;
        private final AtomicBoolean stopped = new AtomicBoolean(false);

        public AudioSourceSubscription(Subscriber<? super Float> subscriber){
            this.subscriber = subscriber;
            subscriber.onSubscribe(this);
        }

        @Override
        public void request(long n) {
            if(!stopped.get()) {
                for (long i = 0; i < n; i++) {
                    subscriber.onNext(nextSample());
                    curChannel++;
                    curChannel %= format.getChannels();
                    if(curChannel == 0) time += timeStep;
                }
            }
        }

        @Override
        public void cancel() {
            stopped.set(true);
        }
    }

}


