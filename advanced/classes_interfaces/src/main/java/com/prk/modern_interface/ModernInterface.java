package com.prk.modern_interface;

public interface ModernInterface {

    // Default method - provides a concrete implementation
    default void defaultMethod() {
        System.out.println("This is a default method.");
        privateHelperMethod(); // Calling private method inside default method
    }

    // Static method - belongs to the interface and cannot be overridden
    static void staticMethod() {
        System.out.println("This is a static method.");
        privateStaticHelperMethod(); // Calling private static method
    }

    // Private method - only accessible within this interface
    private void privateHelperMethod() {
        System.out.println("This is a private method.");
    }

    // Private static method - only accessible within this interface
    private static void privateStaticHelperMethod() {
        System.out.println("This is a private static method.");
    }
}
