package com.prk.modern_interface;

public class DemoModernInterface {
    public static void main(String[] args) {
        ModernImplementation obj = new ModernImplementation();

        // Calling default method
        obj.defaultMethod();

        // Calling static method (through interface name)
        ModernInterface.staticMethod();
    }
}
