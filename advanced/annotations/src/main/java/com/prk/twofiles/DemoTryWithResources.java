package com.prk.twofiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DemoTryWithResources {

    public static void main(String[] args) {
        try {
            removeEmptyLines("src/main/resources/input.txt", "src/main/resources/output.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeEmptyLines(String inputName, String outName) throws IOException {
        Path inputPath = Paths.get(inputName);
        Path outputPath = Paths.get(outName);

        // Ensure input file exists
        if (!Files.exists(inputPath)) {
            throw new FileNotFoundException("Input file not found: " + inputName);
        }
        // try-with-resources with 2 resources
        try (BufferedReader in = new BufferedReader(new FileReader(inputName, StandardCharsets.UTF_8));
                BufferedWriter out = new BufferedWriter(new FileWriter(outName, StandardCharsets.UTF_8))) {
            // copy all non-empty lines from input to output
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Read line: [" + line + "]");
                if (!line.trim().isEmpty()) {
                    out.write(line);
                    out.newLine();
                    System.out.println("Wrote line: [" + line + "]");
                }
            }
        }
    }
}
