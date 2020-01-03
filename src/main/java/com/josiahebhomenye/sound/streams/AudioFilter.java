package com.josiahebhomenye.sound.streams;

import com.josiahebhomenye.sound.Sound;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Function;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.sound.sampled.AudioFormat;


@Getter
@Accessors(fluent = true)
public abstract class AudioFilter implements Sound, Function<Float, Float> {
    private final AudioFormat format;

    protected AudioFilter(){
        this(DEFAULT_FORMAT);
    }

    protected AudioFilter(AudioFormat format) {
        this.format = format;
    }

    @Override
    public Float apply(@NonNull Float sample) throws Throwable {
        return filter(sample);
    }

    protected abstract Float filter(Float sample);
}
