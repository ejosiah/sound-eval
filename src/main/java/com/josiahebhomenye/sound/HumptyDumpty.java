package com.josiahebhomenye.sound;

import com.josiahebhomenye.sound.music.Symbol;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.josiahebhomenye.sound.music.Symbol.*;
import static com.josiahebhomenye.sound.music.Note.*;

public class HumptyDumpty extends SoundGenerator {

    Symbol[] notes = {
            new Mimim(C_4),new Crotchet(C_4),new Mimim(E_4), new Crotchet(E_4)
            , new OverlappingSymboles(Arrays.asList(new Crotchet(D_4), new Crotchet(E_4), new Crotchet(D_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(F_3))))
            , new OverlappingSymboles(Arrays.asList(Mimim.dotted(C_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(E_3))))
            , new Mimim(E_4), new Crotchet(E_4), new Mimim(G_4), new Crotchet(G_4)
            , new OverlappingSymboles(Arrays.asList(new Crotchet(F_4), new Crotchet(G_4), new Crotchet(F_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(F_3))))
            , new OverlappingSymboles(Arrays.asList(Mimim.dotted(E_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(E_3))))
            , new OverlappingSymboles(Arrays.asList(new Crotchet(A_4), new Crotchet(A_4), new Crotchet(A_4)), Arrays.asList(Mimim.dotted(E_4)))
            , new OverlappingSymboles(Arrays.asList(new Crotchet(G_4), new Crotchet(G_4), new Crotchet(G_4)), Arrays.asList(Mimim.dotted(D_4)))
            , new OverlappingSymboles(Arrays.asList(new Crotchet(F_4), new Crotchet(F_4), new Crotchet(F_4)), Arrays.asList(Mimim.dotted(C_4)))
            , new OverlappingSymboles(Arrays.asList(Mimim.dotted(E_4)), Arrays.asList(Mimim.dotted(C_3)))
            , new Crotchet(D_4), new Crotchet(E_4), new Crotchet(F_4)
            , new Crotchet(G_4), new Crotchet(E_4), new Crotchet(C_4)
            , new OverlappingSymboles(Arrays.asList(new Crotchet(D_4), new Crotchet(E_4), new Crotchet(D_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(F_3))))
            , new OverlappingSymboles(Arrays.asList(Mimim.dotted(C_4)), Arrays.asList(new CombinedSymbols(Mimim.dotted(G_3), Mimim.dotted(E_3))))
    };

    public HumptyDumpty() {
        super(Duration.ofSeconds(48));
    }

    @Override
    public void generate() {
        for(Symbol note : notes){
            buffer.put(samples(note));
        }
    }

    private byte[] samples(Symbol symbol){
        if(symbol instanceof CombinedSymbols){
            return samples((CombinedSymbols)symbol);
        }else if(symbol instanceof OverlappingSymboles){
            return samples((OverlappingSymboles)symbol);
        }else {
            return getSamples(symbol);
        }
    }

    private byte[] samples(CombinedSymbols combined){
        int factor = combined.notes().length;
        float[] fSamples = new float[(int)((combined.duration().toMillis()/1000.0) * format.getSampleRate() * format.getChannels())];

        for(Symbol note1 : combined){
            float[] cSamples = SamplesUtil.convertToFloats(getSamples(note1), format);
            for(int i = 0; i < cSamples.length; i++){
                fSamples[i] += cSamples[i];
            }
        }
        float[] samples = new float[fSamples.length];
        for(int i = 0; i < samples.length; i++){
            samples[i] = fSamples[i]/factor;
        }
        return SamplesUtil.convertToBites(samples, format);
    }

    private byte[] getSamples(Symbol note){
        DecayFilter filter = new DecayFilter(note.duration(), 2);
       // byte[] samples = new OverTones(note.note().getValue(), 4, note.duration()).samples();
        byte[] samples = new Violin(note.note().getValue(),  note.duration()).samples();
        return filter.filter(samples);
    }

    private byte[] samples(OverlappingSymboles symbol){
        int factor = symbol.overlaps().length;
        float[] fSamples = new float[(int)((symbol.duration().toMillis()/1000.0) * format.getSampleRate() * format.getChannels())];

        int next = 0;
        for(List<Symbol> overlaps : symbol.overlaps()){
            for(int j = 0; j < overlaps.size(); j++){
                Symbol note = overlaps.get(j);
                float[] samples = SamplesUtil.convertToFloats(samples(note), format);
                for(int i = 0; i < samples.length; i++, next++){
                    fSamples[next] += samples[i];
                }
            }
            next = 0;
        }
        float[] samples = new float[fSamples.length];
        for(int i = 0; i < samples.length; i++){
            samples[i] = fSamples[i]/factor;
        }
        return SamplesUtil.convertToBites(samples, format);
    }
}
