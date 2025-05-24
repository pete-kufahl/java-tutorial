package com.prk.bindingConsumer.advanced;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class AdvancedConsumingWithBindingApi {
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ImmutableLoanApplication application = mapper.readValue(BANK_LOAN_FILE, ImmutableLoanApplication.class);
        System.out.println(application + "\n\n");
    }
}
