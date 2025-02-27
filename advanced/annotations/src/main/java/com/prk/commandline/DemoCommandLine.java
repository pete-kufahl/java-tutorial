package com.prk.commandline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class DemoCommandLine {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // find names of all the classes in the package com.prk.commandline.commands
        Set<String> classNames = ClassUtils.findClassNames("com.prk.commandline.commands");

        // convert classes that have a command annotation to Class objects
        List<Class<?>> commandClasses = new ArrayList<>();
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            Command annotation = clazz.getAnnotation(Command.class);
            if (annotation != null) {
                System.out.printf("Found command: %s, order: %d%n", annotation.value(), annotation.order());
                commandClasses.add(clazz);
            }
        }

        // sort the classes by the value of the "order" element of their command annotations
        commandClasses.sort(Comparator.comparingInt(c -> c.getAnnotation(Command.class).order()));

        // interactive loop
        var sessionState = new SessionState();

    }
}
