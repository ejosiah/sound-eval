package com.josiahebhomenye.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args)  throws Exception{

        File file = new File("sine.wav");
        AudioFileFormat format =  AudioSystem.getAudioFileFormat(file);
        AudioInputStream in = AudioSystem.getAudioInputStream(file);

        // nFrames = (SampleRate * duration * FrameSize)/(SampleSizeInBits/8) as int

        byte[] buf = new byte[format.getFrameLength()];

        for(int i = 0; i < 60; i++) {
            int read = in.read(buf);
        }

        new Visualizer(buf, in.getFormat()).setVisible(true);


        System.out.println(format);


        printFields(format, 0);
        printProps(format);

        playWithSource(AudioSystem.getAudioInputStream(file));
    }

    static void printFields(Object object, int depth){
        List<Method> getters =
                Arrays.stream(object.getClass().getMethods())
                    .filter(Main::isGetter)
                    .collect(Collectors.toList());

        if(getters.isEmpty()){
            System.out.printf(" %s\n", object);
        }else{
            System.out.println();
        }

        getters.forEach(method -> {
            String name = method.getName().replace("get", "").replace("is", "");
            Object value = invoke(object, method);
            if(value != null) {
                if (isNormalized(value)) {
                    for (int i = 0; i < depth; i++) {
                        System.out.print("\t");
                    }
                    System.out.printf("%s = %s\n", name, value);
                } else if(!(value instanceof Class)) {
                    for (int i = 0; i < depth; i++) {
                        System.out.print("\t");
                    }
                    System.out.printf("%s:", name);
                    printFields(value, depth + 1);
                }
            }
        });
    }

    static boolean isGetter(Method m){
        return (m.getName().startsWith("get") || m.getName().startsWith("is"))
                && (!m.getName().equals("getClass") && m.getParameterCount() == 0);
    }

    static void printProps(AudioFileFormat aff){
        aff.properties().forEach((key, value) -> {
            System.out.printf("%s : %s\n", key, value);
        });
    }

    public static Object invoke(Object target, Method method){
        try{
            return method.invoke(target);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    static void playWithSource(AudioInputStream in) throws Exception{
        AudioFormat format =  in.getFormat();
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(in.getFormat());
        byte[] buf = new byte[1024 * 1024];

        try {
            line.open();
            line.start();
            int read;
            while((read = in.read(buf)) != -1){
                line.write(buf, 0, read);
            }
        } finally {
            line.drain();
            line.stop();
            line.close();
            in.close();
        }
    }

    public static void playWithClip(AudioInputStream in) throws Exception {
        int length = in.getFormat().getFrameSize() * (int)in.getFrameLength();
        byte[] buf = new byte[length];

        in.read(buf);

        Clip clip = AudioSystem.getClip();
        clip.open(in.getFormat(), buf, 0, length);
      //  clip.start();
        clip.loop(100);
        TimeUnit.SECONDS.sleep(60);
       // clip.drain();
        clip.stop();
        clip.close();
    }

    static boolean isNormalized(Object obj){
        return obj.getClass().isPrimitive()
                || obj instanceof Number
                || obj instanceof Boolean
                || obj instanceof String
                || obj.getClass().isEnum();
    }
}
