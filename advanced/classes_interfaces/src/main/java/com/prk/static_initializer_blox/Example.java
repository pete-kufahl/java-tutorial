package com.prk.static_initializer_blox;

import java.io.IOException;
import java.util.Properties;

public class Example {
    private static final Properties CONFIGURATION = new Properties();

    // static initializer blocks can handle logic beyond simple assignment.
    //. e.g. handling checked exceptions
    static {
        try {
            CONFIGURATION.load(Example.class.getResourceAsStream("/example.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
