package com.prk.commandline;

import java.util.Arrays;
import java.util.Scanner;

public record UserInput(String command, String[] arguments) {

    private static final String PROMPT = "> ";

    public static UserInput readUserInput(Scanner scanner) {
        String line = "";
        while (line.isEmpty()) {
            System.out.print(PROMPT);
            line = scanner.nextLine().trim();
        }
        String[] parts = line.split("\\s");
        return new UserInput(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }
}
