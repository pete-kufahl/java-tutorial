package com.prk.streamingConsumer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prk.bindingConsumer.basic.BasicLoanApplication;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(2)
@State(Scope.Thread)
public class JsonBenchmark {
    private static final String BANK_LOAN_FILEPATH = "src/main/resources/bank_loan.json";

    String bankLoanFile;
    ObjectMapper mapper;
    JsonFactory factory;

    @Setup
    public void prepare() throws IOException {
        bankLoanFile = new String(Files.readAllBytes(Paths.get(BANK_LOAN_FILEPATH)));
        mapper = new ObjectMapper();
        factory = mapper.getFactory();
    }

    @Benchmark
    public void streaming(Blackhole blackhole) throws IOException {
        try (JsonParser parser = factory.createParser(bankLoanFile)) {
            JsonToken token;
            while ((token = parser.nextToken()) != null) {
                if (token.isScalarValue()) {
                    String text = parser.getText();;
                    blackhole.consume(text);
                }
            }
        }
    }

    @Benchmark
    public BasicLoanApplication binding() throws IOException {
        return mapper.readValue(bankLoanFile, BasicLoanApplication.class);
    }
}
