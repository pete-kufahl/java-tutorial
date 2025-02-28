package com.prk.tempdirectory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class DemoTempDirectory {
    public static void main(String[] args) {
        try (var tempDir = new TempDirectory("test-");
             var out = Files.newBufferedWriter(tempDir.getDirectory()
                     .resolve("test.txt"), StandardCharsets.UTF_8)) {
            System.out.println("Temporary directory: " + tempDir.getDirectory());

            out.write("hello there!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
