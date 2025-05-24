package com.prk.domConsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prk.common.SimpleJettyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApplicationDomServlet extends HttpServlet {
    // approve the bank loan if the amount <= 3 * totalIncome, deny otherwise
    // SC_OK to approve
    // SC_FORBIDDEN to deny

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main (String[] args) {
        SimpleJettyService.run(ApplicationDomServlet.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode loanApplication = objectMapper.readTree(req.getInputStream());
        double totalIncome = getTotalIncome(loanApplication);
        double amount = getAmount(loanApplication);

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

    private double getAmount(JsonNode loanApplication) {
        JsonNode loanDetails = loanApplication.get("loanDetails");
        if(loanDetails != null) {
            JsonNode amount = loanDetails.get("amount");
            return amount.asDouble();
        }
        return 0.0;
    }

    private double getTotalIncome(JsonNode loanApplication) {
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
