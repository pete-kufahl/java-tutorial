package com.prk.domConsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsumingWithDomApi {
    // cases to use Dom api
    // 1. integration with unstable apis - DOM offers flexibility to support different formats
    // 2. api evolution - supporting different versions of an api
    //  e.g. different client versions pushing JSON requests

    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(BANK_LOAN_FILE);

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        System.out.println(writer.writeValueAsString(jsonNode));

        validateDates(jsonNode);
    }

    private static void validateDates(JsonNode jsonNode) {
        var fields = jsonNode.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            String fieldName = field.getKey();
            JsonNode childNode = field.getValue();
            if (childNode.isTextual() && fieldName.endsWith("Date")) {
                String fieldValue = childNode.asText();
                try {
                    LocalDate.parse(fieldValue, DateTimeFormatter.ISO_LOCAL_DATE);
                    System.out.println("validated date field: " + fieldName);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid value: " + fieldValue);
                }
            } else if (childNode.isContainerNode()) {
                validateDates(childNode); // Recurse into nested JSON objects/arrays
            }
        }
    }
}
