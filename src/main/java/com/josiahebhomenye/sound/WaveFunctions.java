package com.josiahebhomenye.sound;

public interface WaveFunctions {

    default  double sin(double x){
        return (double)Math.sin(x);
    }

    default double sin(double a, double f, double t){
        return a * (double)Math.sin(2 * Math.PI * f * t);
    }

    default double cos(double a, double f, double t){
        return a * (double)Math.cos(2 * Math.PI * f * t);
    }

    default double cos(double x){
        return (double)Math.cos(x);
    }

    default double sin(double f, double t){
        return (double)Math.sin(2 * Math.PI * f * t);
    }

    default double cos(double f, double t){
        return(double) Math.cos(2 * Math.PI * f * t);
    }

    default double triangle(double limit, double t){
        double sum = 0;
        for(double n = 1; n <= limit; n++){
            double _2n_1 = (2 * n - 1);
            sum += (1.0/(_2n_1 * _2n_1)) * cos(_2n_1 * t);
        }
        return sum;
    }

    default double square(double limit, double t){
        double sum = 0;
        for(double n = 1; n <= limit; n++){
            double _2n_1 = (2 * n - 1);
            sum += (1.0/_2n_1) * sin(_2n_1 * t);
        }
        return sum;
    }

    default double sawtooth(double limit, double t){
        double sum = 0;
        for(double n = 1; n <= limit; n++){
            sum +=  (Math.pow(-1, n+1)/n) * sin(n * t);
        }
        return sum;
    }
}
