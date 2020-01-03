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

@Getter
@Accessors(fluent = true)
public abstract class AudioSource implements Publisher<Float> , Sound {

    private static final Random RNG = new Random();

    protected final AudioFormat format;
    protected final float timeStep;
    protected int currentChannel;
    protected float time;
    protected int curChannel;
    protected AudioSourceSubscription subscription;
    protected AtomicBoolean stopped;

    protected AudioSource(){
        this(DEFAULT_FORMAT);
    }

    protected AudioSource(AudioFormat format) {
        this.format = format;
        this.timeStep = 1/format.getFrameRate();
        this.currentChannel = 0;
        this.stopped = new AtomicBoolean(false);
    }

    @Override
    public void subscribe(Subscriber<? super Float> s) {
        subscription = new AudioSourceSubscription(s);
    }

    protected abstract float nextSample();

    public void stop(){
        stopped.set(true);
        if(subscription != null) {
            subscription.cancel();
        }
    }

    private class AudioSourceSubscription implements Subscription{

        private final Subscriber<? super Float> subscriber;
        private final AtomicBoolean stopped = new AtomicBoolean(false);

        public AudioSourceSubscription(Subscriber<? super Float> subscriber){
            this.subscriber = subscriber;
            withExceptionHandler(() -> {
                subscriber.onSubscribe(this);
            });
        }

        @Override
        public void request(long n) {
            withExceptionHandler(() -> {
                if(!stopped()) {
                    float sample = nextSample();
                    for (long i = 0; i < n; i++) {
                        subscriber.onNext(sample);
                        curChannel++;
                        curChannel %= format.getChannels();
                        if(curChannel == 0){
                            if(stopped()){
                                break;
                            }
                            time += timeStep;
                            sample = nextSample();
                        }

                    }
                }else {
                    subscriber.onComplete();
                }
            });

        }

        private void withExceptionHandler(Runnable runnable){
            try {
               runnable.run();
            }catch (DownStreamClosedException ex){
                cancel();
                subscriber.onComplete();
            }
            catch (Exception e) {
                cancel();
                subscriber.onError(e);
            }
        }

        @Override
        public void cancel() {
            stopped.set(true);
        }

        public boolean stopped(){
            return stopped.get() || AudioSource.this.stopped.get();
        }
    }

}


