package com.prk.streamingConsumer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class JsonMemoryComparison {
    private static final File JSON_FILE = new File("src/main/resources/citylots.json");

    public static void main(String[] args) throws IOException {
        countFeaturesInBlockTree("0022");
        // countFeaturesInBlockStreaming("0022");
    }

    private static void countFeaturesInBlockTree(String number) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(JSON_FILE);
        ArrayNode features = (ArrayNode) jsonNode.get("features");
        int featureCount = 0;
        for (var feature: features) {
            JsonNode properties = feature.get("properties");
            JsonNode thisBlockNum = properties.get("BLOCK_NUM");
            if (number.equals(thisBlockNum.asText())) {
                featureCount++;
            }
        }
        System.out.println("feature count = " + featureCount);
        printMemoryConsumption();
    }

    private static void countFeaturesInBlockStreaming(String blockNum) throws IOException {
        JsonFactory factory = new JsonFactory();
        int featureCount = 0;
        try(JsonParser parser = factory.createParser(JSON_FILE)) {
            JsonToken token;
            while ((token = parser.nextToken()) != null) {
                if (token == JsonToken.VALUE_STRING) {
                    String currentName = parser.currentName();
                    String text = parser.getText();
                    if (currentName.equals("BLOCK_NUM") && text.equals(blockNum)) {
                        featureCount++;
                    }
                }
            }
        }
        System.out.println("feature count = " + featureCount);
        printMemoryConsumption();
    }

    private static void printMemoryConsumption() {
        System.gc();
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = bean.getHeapMemoryUsage();
        System.out.printf("Used memory: %dK%n", heapUsage.getUsed() / 1024);
    }
}
