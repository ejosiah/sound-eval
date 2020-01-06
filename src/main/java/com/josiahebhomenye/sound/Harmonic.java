package com.josiahebhomenye.sound;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Harmonic {
    private int index;
    private double weight;
}
