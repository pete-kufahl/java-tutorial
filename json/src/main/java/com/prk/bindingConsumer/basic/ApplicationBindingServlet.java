package com.prk.bindingConsumer.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prk.common.Job;
import com.prk.common.SimpleJettyService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApplicationBindingServlet extends HttpServlet {
    // approve the bank loan if the amount <= 3 * totalIncome, deny otherwise
    // SC_OK to approve
    // SC_FORBIDDEN to deny

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main (String[] args) {
        SimpleJettyService.run(ApplicationBindingServlet.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var loanApp = objectMapper.readValue(req.getInputStream(), BasicLoanApplication.class);
        var totalIncome = loanApp.getJobs().stream()
                .mapToDouble(Job::getAnnualIncome)
                .sum();
        var amount = loanApp.getLoanDetails().getAmount();

        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.println("Total Income = " + totalIncome);
        outputStream.println("Amount = " + amount);

        if (amount <= 3 * totalIncome) {
            resp.setStatus(HttpServletResponse.SC_OK);
            outputStream.println("Approved");
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            outputStream.println("Denied");
        }
    }
}
