package com.prk.staticnested;

import java.time.LocalDate;

public class Enclosing {

    private static int num = 10;
    private String name = "Pancho";
    private static LocalDate date = LocalDate.of(2025, 2, 17);

    // can only access static members from the static context
    private static void printNumber() {
        System.out.println(num);
    }

    public void printName() {
        System.out.println(name);
    }

    private static void printDate() {
        System.out.println(date);
    }

    // can only access static members from the static context
    static class Nested {
        private LocalDate date = LocalDate.of(2025, 2, 18);

        private void printDate() {
            System.out.println(date);
        }

        void run() {
            // accessing private member of enclosing class
            System.out.println(num);

            // cannot call non-static member
            // printName();

            // local variable shadows the enclosing variable of the same name
            System.out.println(date);   // --> local date
            printDate();                // --> local printDate

            // can access enclosing members explicitly
            System.out.println(Enclosing.date);
            Enclosing.printDate();
        }

    }
}
