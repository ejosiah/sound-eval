package com.josiahebhomenye.sound;

import lombok.RequiredArgsConstructor;

public class ByteString {

    @RequiredArgsConstructor
    private static class Node{
        private final int length;
        private final Node left;
        private final Node right;
    }

    private static class Leave extends Node{
        private final byte[] data;

        private Leave(byte[] data) {
            super(data.length, null, null);
            this.data = data;
        }
    }
}
