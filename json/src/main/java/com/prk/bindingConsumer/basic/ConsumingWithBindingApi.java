package com.prk.bindingConsumer.basic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConsumingWithBindingApi {
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BasicLoanApplication application = mapper.readValue(BANK_LOAN_FILE, BasicLoanApplication.class);
        System.out.println(application);
    }
}
