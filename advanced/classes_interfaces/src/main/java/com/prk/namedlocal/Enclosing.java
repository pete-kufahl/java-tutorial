package com.prk.namedlocal;

public class Enclosing {

    private String name = "Peter";
    private static int classVar = 22;

    public void enclosingMethod(int param1, int param2) {

        int outerVar1 = 11;

        class Local {
            // local class's method has access to the variables of the enclosing method
            void innerMethod() {
                System.out.println("data member is: " + name);
                System.out.println("parameters are: " + param1 + " and " + param2);
                System.out.println("outer variable is: " + outerVar1);
                System.out.println("outer static data member is: " + classVar);
            }

            static void innerStaticMethod() {
                /* inner method declared static --> cannot access instance data members, parameters or local variables
                 *  of the enclosing class/method
                System.out.println("data member is: " + name);
                System.out.println("parameters are: " + param1 + " and " + param2);
                System.out.println("outer variable is: " + outerVar1);
                */
                System.out.println("outer static data member is: " + classVar);
            }
        }

        var obj = new Local();
        obj.innerMethod();

        // local variables referenced from an inner class must be final or effectively final
        // cannot do: outerVar1 += 1;

        // however, changing data members accessed by the local class is fine
        this.name = "George";
        System.out.println("data member is now: " + name);
    }
}
