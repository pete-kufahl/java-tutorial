package com.prk.twofiles;

public class DemoAutoCloseable {
    // why does this need to be here? ----------------v
    public static void main(String[] args) throws Exception {

        try (var r1 = new GenericResource("1")) {
            System.out.println("inside try-block");
        }

        try (var r2 = new GenericResource("2")) {
            System.out.println("inside try-block");
            throw new Exception("exception thrown in try-block");
        } catch (Exception e) {
            System.out.println("inside the catch-block");
            e.printStackTrace();
        }
    }
}
