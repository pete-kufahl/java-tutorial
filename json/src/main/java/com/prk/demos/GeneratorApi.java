package com.prk.demos;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.prk.common.Job;
import com.prk.common.LoanApplication;
import com.prk.common.LoanDetails;

import java.io.IOException;
import java.util.List;

public class GeneratorApi {
    public static void main(String[] args) throws IOException {
        LoanApplication app = ExampleLoan.LOAN_APPLICATION;
        System.out.println(app);
        System.out.println();
        toJsonString(app);
    }

    private static void toJsonString(LoanApplication app) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator generator = jsonFactory.createGenerator(System.out);
        generator.setPrettyPrinter(new DefaultPrettyPrinter());

        generator.writeStartObject();
        generator.writeStringField("name", app.getName());
        generator.writeStringField("purposeOfLoan", app.getPurposeOfLoan());
        toJsonString(generator, app.getLoanDetails());
        toJsonString(generator, app.getJobs());
        generator.writeEndObject();

        generator.flush();
    }

    private static void toJsonString(JsonGenerator generator, List<Job> jobs) throws IOException {
        generator.writeFieldName("jobs");
        generator.writeStartArray();
        for (Job job: jobs) {
            generator.writeStartObject();
            generator.writeStringField("title", job.getTitle());
            generator.writeNumberField("annualIncome", job.getAnnualIncome());
            generator.writeNumberField("yearsActive", job.getYearsActive());
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }

    private static void toJsonString(JsonGenerator generator, LoanDetails loanDetails) throws IOException {
        generator.writeFieldName("loanDetails");
        generator.writeStartObject();
        generator.writeNumberField("amount", loanDetails.getAmount());
        generator.writeStringField("startDate", loanDetails.getStartDate().toString());
        generator.writeStringField("endDate", loanDetails.getEndDate().toString());
        generator.writeEndObject();
    }
}
