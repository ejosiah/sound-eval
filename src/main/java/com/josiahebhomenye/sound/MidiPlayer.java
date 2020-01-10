package com.josiahebhomenye.sound;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import javax.sound.midi.*;

import java.time.Duration;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

public class MidiPlayer {

    public static void main(String[] args) throws Exception {
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();

        MidiDevice.Info[] info =  stream(MidiSystem.getMidiDeviceInfo()).filter(i -> i.getName().toLowerCase().contains("launch")).toArray(MidiDevice.Info[]::new);
        MidiDevice device = MidiSystem.getMidiDevice(info[0]);
        System.out.println(device.getClass().getName());
        System.out.println(device.isOpen());
        device.open();

        System.out.println(device.isOpen());
        Transmitter transmitter = device.getTransmitter();
        System.out.println(transmitter.getClass().getName());
        Receiver receiver = new MyReceiver(synthesizer.getReceiver());
        System.out.println(receiver.getClass().getName());
        transmitter.setReceiver(receiver);
        //Thread.sleep(Duration.ofMinutes(10).toMillis());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            device.close();
            synthesizer.close();
        }));
    }

    @RequiredArgsConstructor
    static class MyReceiver implements Receiver{

        long previousTimeStamp;
        int[] pitches = new int[127];
        long[] timeStamps = new long[127];

        interface ExcludeSend{

            void send(MidiMessage message, long timeStamp);
        }

        @Delegate(excludes = ExcludeSend.class)
        private final Receiver delegate;

        @Override
        public void send(MidiMessage message, long timeStamp){
            if(message instanceof ShortMessage) {
                ShortMessage msg = (ShortMessage)message;
                if(msg.getCommand() == ShortMessage.NOTE_ON) {
                    timeStamps[msg.getData1()] = timeStamp;
                    System.out.printf("Note on: note %s, velocity: %s, channel: %s\n", msg.getData1(), msg.getData2(), msg.getChannel());
                    previousTimeStamp = timeStamp;
                }else if(msg.getCommand() == ShortMessage.NOTE_OFF){
                    long diff = timeStamp - timeStamps[msg.getData1()];
                    timeStamps[msg.getData1()] = 0;
                    System.out.printf("Note off: note %s, velocity: %s, held for %s microSeconds\n", msg.getData1(), msg.getData2(), diff);
                }else if(msg.getCommand() == 240){
                    // IGNORE
                }
                else{
                    System.out.println(msg.getCommand());
                }
            }
            delegate.send(message, timeStamp);
        }

    }
}
