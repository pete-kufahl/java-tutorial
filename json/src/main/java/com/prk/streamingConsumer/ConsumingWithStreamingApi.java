package com.prk.streamingConsumer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;

public class ConsumingWithStreamingApi {
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    public static void main(String[] args) throws IOException {
        var factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(BANK_LOAN_FILE)) {
            JsonToken token;
            while ((token = parser.nextToken()) != null) {
                if (token.isScalarValue()) {
                    String currentName = parser.currentName();
                    if (currentName != null) {
                        String text = parser.getText();
                        System.out.printf("%s: %s%n", currentName, text);
                    }
                }
            }
        }
    }
}
