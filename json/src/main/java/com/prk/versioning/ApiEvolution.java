package com.prk.versioning;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prk.bindingConsumer.basic.BasicLoanApplication;
import com.prk.common.Job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ApiEvolution {
    // show how selective extraction (i.e. using only what you need to make a decision)
    //  can keep the application code functional after changes are made to extraneous fields
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");
    private static final File BANK_LOAN_EVOLVED_FILE = new File("src/main/resources/bank_loan_evolved.json");

    public static void main (String[] args) throws Exception {
        System.out.println("original loan app ....");
        try (FileInputStream input = new FileInputStream(BANK_LOAN_FILE)) {
            // extractUsingDOMApi(input);
            // extractUsingBindingApi(input);
            extractUsingStreamingApi(input);
        }
        System.out.println("Changed loan app....");
        try (FileInputStream input = new FileInputStream(BANK_LOAN_EVOLVED_FILE)) {
            // extractUsingDOMApi(input);
            // extractUsingBindingApi(input);
            extractUsingStreamingApi(input);
        }
    }

    private static void extractUsingStreamingApi(FileInputStream input) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(input);
        double totalIncome = 0.0;
        double amount = 0.0;
        JsonToken token;
        while((token = parser.nextToken()) != null) {
            String currentName = parser.currentName();
            if (currentName != null && token.isNumeric()) {
                switch (currentName) {
                    case "annualIncome":
                        totalIncome += parser.getDoubleValue();
                        break;
                    case "amount":
                        amount = parser.getDoubleValue();
                        break;
                }
            }
        }
        System.out.println("Total Income = " + totalIncome);
        System.out.println("Amount = " + amount);
    }

    private static void extractUsingBindingApi(FileInputStream input) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BasicLoanApplication loanApp = objectMapper.readValue(input, BasicLoanApplication.class);
        var totalIncome = loanApp.getJobs().stream()
                .mapToDouble(Job::getAnnualIncome)
                .sum();
        var amount = loanApp.getLoanDetails().getAmount();
        System.out.println("Total Income = " + totalIncome);
        System.out.println("Amount = " + amount);
    }

    private static void extractUsingDOMApi(FileInputStream input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode loanApplication = objectMapper.readTree(input);
        double totalIncome = getTotalIncomeDOM(loanApplication);
        double amount = getAmountDOM(loanApplication);
        System.out.println("Total Income = " + totalIncome);
        System.out.println("Amount = " + amount);
    }

    private static double getAmountDOM(JsonNode loanApplication) {
        JsonNode loanDetails = loanApplication.get("loanDetails");
        if(loanDetails != null) {
            JsonNode amount = loanDetails.get("amount");
            return amount.asDouble();
        }
        return 0.0;
    }

    private static double getTotalIncomeDOM(JsonNode loanApplication) {
        double totalIncome = 0;
        JsonNode jobs = loanApplication.get("jobs");
        if (jobs != null && jobs.isArray()) {
            for (JsonNode job : jobs) {
                totalIncome += job.path("annualIncome").asDouble(0.0);
            }
        }
        return totalIncome;
    }
}
