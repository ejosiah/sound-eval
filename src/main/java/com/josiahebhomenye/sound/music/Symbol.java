package com.josiahebhomenye.sound.music;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Symbol {
    private final Duration duration;
    private final Note note;
    private final boolean dotted;

    public static class Semibreve extends Symbol{

        public Semibreve(Note note) {
            super(Duration.ofMillis(3000), note, false);
        }

        private Semibreve(Duration duration, Note note, boolean dotted){
            super(duration, note, dotted);
        }

        public static Semibreve dotted(Note note){
            return new Semibreve(Duration.ofMillis(4500), note, true);
        }
    }

    public static class Mimim extends Symbol{

        public Mimim(Note note) {
            super(Duration.ofMillis(1500), note, false);
        }

        private Mimim(Duration duration, Note note, boolean dotted){
            super(duration, note, dotted);
        }

        public static Mimim dotted(Note note){
            return new Mimim(Duration.ofMillis(2250), note, true);
        }
    }

    public static class Crotchet extends Symbol{

        public Crotchet(Note note) {
            super(Duration.ofMillis(750), note, false);
        }

        private Crotchet(Duration duration, Note note, boolean dotted){
            super(duration, note, dotted);
        }

        public static Crotchet dotted(Note note){
            return new Crotchet(Duration.ofMillis(1125), note, true);
        }

    }

    public static class Quaver extends Symbol{

        public Quaver(Note note) {
            super(Duration.ofMillis(375), note, false);
        }

        private Quaver(Duration duration, Note note, boolean dotted){
            super(duration, note, dotted);
        }

        public static Quaver dotted(Note note){
            return new Quaver(Duration.ofMillis(562), note, true);
        }
    }

    public static class SemiQuaver extends Symbol{

        public SemiQuaver(Note note) {
            super(Duration.ofMillis(187), note, false);
        }

        private SemiQuaver(Duration duration, Note note, boolean dotted){
            super(duration, note, dotted);
        }

        public static SemiQuaver dotted(Note note){
            return new SemiQuaver(Duration.ofMillis(281), note, true);
        }
    }

    @Getter
    public static class CombinedSymbols extends Symbol implements Iterable<Symbol> {
        Symbol[] notes;
        public CombinedSymbols(Symbol... notes) {
            super(null, null, false);
            this.notes = notes;
        }

        @Override
        public Duration duration() {
            Duration max = Duration.ZERO;
            for(Symbol symbol : notes){
                if(symbol.duration.compareTo(max) > 0){
                    max = symbol.duration;
                }
            }
            return max;
        }

        @Override
        public Note note() {
            throw new UnsupportedOperationException("cant call note on Combined symbols");
        }

        @Override
        public Iterator<Symbol> iterator() {
            return Arrays.stream(notes).iterator();
        }
    }

    @Getter
    public static class OverlappingSymboles extends Symbol{

        private final List<Symbol>[] overlaps;

        public OverlappingSymboles(List<Symbol>... overlaps) {
            super(null, null, false);
            this.overlaps = overlaps;
        }

        @Override
        public Duration duration() {
            return overlaps[0].stream().map(Symbol::duration).reduce(Duration::plus).get();
        }

        @Override
        public Note note() {
            throw new UnsupportedOperationException("cant call note on Combined symbols");
        }
    }

}
