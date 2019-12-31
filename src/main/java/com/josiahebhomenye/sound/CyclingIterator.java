package com.josiahebhomenye.sound;

import java.util.Iterator;

public class CyclingIterator<T> implements Iterator<T> {

    private final T[] array;
    private int nextIndex;

    public CyclingIterator(T[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        return array[(nextIndex++)%array.length];
    }

    public static void main(String[] args) {
        Integer[] array = {1 , 2, 3, 4, 5};
        CyclingIterator<Integer> itr = new CyclingIterator<>(array);
        for(int i = 0; i < 20; i++){
            System.out.print(itr.next() + " ");
            if((i+1)%5 == 0) System.out.println();
        }
    }
}
