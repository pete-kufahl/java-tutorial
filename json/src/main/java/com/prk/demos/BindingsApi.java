package com.prk.demos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.prk.common.LoanApplication;

import java.io.IOException;

public class BindingsApi {
    public static void main(String[] args) throws IOException {
        LoanApplication app = ExampleLoan.LOAN_APPLICATION;
        System.out.println(app);
        System.out.println();
        toJsonString(app);
    }

    private static void toJsonString(LoanApplication app) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"));
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String jsonString = writer.writeValueAsString(app);
        System.out.println(jsonString);
    }
}
