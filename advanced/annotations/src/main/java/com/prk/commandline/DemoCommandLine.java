package com.prk.commandline;

import com.prk.commandline.commands.CommandExecutor;

import java.io.IOException;
import java.util.*;

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
        var scanner = new Scanner(System.in);
        while (!sessionState.isFinished()) {
            // display the menu
            System.out.println();
            for (Class<?> clazz : commandClasses) {
                Command annotation = clazz.getAnnotation(Command.class);
                System.out.printf("%-12s - %s%n", annotation.value(), annotation.description());
            }

            // display prompt, get user input
            var userInput = UserInput.readUserInput(scanner);

            // find, execute the command
            boolean found = false;
            for (Class<?> clazz : commandClasses) {
                Command annotation = clazz.getAnnotation(Command.class);
                if (annotation.value().equals(userInput.command())) {
                    executeCommand(clazz, sessionState, userInput);
                    found = true;
                    break;
                }
            }
            // print error if command wasn't found
            if (!found) {
                System.out.println("Command not recognized: " + userInput.command());
            }
        }
    }

    private static void executeCommand(Class<?> commandClass, SessionState sessionState, UserInput userInput) {
        var instance = (CommandExecutor) ClassUtils.newInstance(commandClass);
        instance.execute(sessionState, userInput);
    }
}
