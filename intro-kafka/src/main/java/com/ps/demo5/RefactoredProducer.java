package com.ps.demo5;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class RefactoredProducer {
    private static final Logger log = LoggerFactory.getLogger(RefactoredProducer.class);
    private static final String TOPIC = "myorders";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092,http://localhost:9093,http://localhost:9094");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());

        KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

        // container for better random access, readability
        List<String> states = Arrays.asList(
                "AK", "AL", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
        );

        // close producer when process shut down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            producer.flush();
            producer.close();
            log.info("Producer closed successfully");
        }));

        // handle exception
        try {
            for (int i = 0; i < 25000; i++) {
                // replace Math.random()
                String key = states.get(ThreadLocalRandom.current().nextInt(states.size()));
                double value = ThreadLocalRandom.current().nextDouble(10, 10000);

                ProducerRecord<String, Double> record = new ProducerRecord<>(TOPIC, key, value);

                log.info("Sending message with key {} and value {}", key, value);

                // callback handles exception
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        log.error("Error sending message with key " + key, exception);
                    } else {
                        log.info("Message sent to topic {} partition {} with offset {}",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    }
                });

                Thread.sleep(5000); // Optional delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Producer was interrupted", e);
        }

        log.info("Successfully produced messages to topic {}", TOPIC);
    }
}
