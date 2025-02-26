package com.prk.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DemoFileWriter {
    public static void main(String[] args) {
        var names = List.of("Adam", "Eve", "Abraham", "Esau", "Ezekiel", "Joseph", "John");

        // for-loop handles checked exception ...
        try (FileWriter out = new FileWriter("names.txt", StandardCharsets.UTF_8)) {
            for (String name: names) {
                out.write(names + "\n");
            }

            // .. but the functionally equivalent lambda does not
            // thats because it implements Consumer<>, which has no throws clause in its abstract function
            // no really good way to handle this situation
            // names.forEach(name -> out.write(name + "\n"));

        } catch (IOException ex) {
            System.err.println("Some error occurred: " + ex.getMessage());
        }
    }
}
