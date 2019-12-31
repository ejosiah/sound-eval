package com.josiahebhomenye.sound;

public interface WaveFunctions {

    default float sin(float a, float f, float t){
        return a * (float)Math.sin(2 * Math.PI * f * t);
    }

    default float cos(float a, float f, float t){
        return a * (float)Math.cos(2 * Math.PI * f * t);
    }

    default float sin(float f, float t){
        return (float)Math.sin(2 * Math.PI * f * t);
    }

    default float cos(float f, float t){
        return(float) Math.cos(2 * Math.PI * f * t);
    }
}
