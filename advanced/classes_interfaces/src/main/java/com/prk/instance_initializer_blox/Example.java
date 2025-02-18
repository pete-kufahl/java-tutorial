package com.prk.instance_initializer_blox;

import java.util.Random;

public class Example {
    private final byte[] randomBytes;

    {
        randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
    }

    public static void main(String[] args) {
        var obj = new Example();
        for (byte b : obj.randomBytes) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }
}
