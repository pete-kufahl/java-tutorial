package com.prk.twofiles;

public class GenericResource implements AutoCloseable {

    private final String name;

    public GenericResource(String name) {
        this.name = name;
        System.out.println("initializing resource " + name);
    }

    @Override
    public void close() throws Exception {
        System.out.println("resource is closed " + name);
    }
}
