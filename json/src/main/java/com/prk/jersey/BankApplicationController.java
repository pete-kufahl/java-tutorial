package com.prk.jersey;

import com.prk.bindingConsumer.basic.BasicLoanApplication;
import com.prk.common.Job;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Path("/")
public class BankApplicationController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response applyForLoan(BasicLoanApplication loanApp) {
        double totalIncome = loanApp.getJobs().stream()
                .mapToDouble(Job::getAnnualIncome)
                .sum();
        double amount = loanApp.getLoanDetails().getAmount();
        if (amount <= 3 * totalIncome) {
            return Response.ok("approved").build();
        } else {
            return Response.status(SC_FORBIDDEN).entity("denied").build();
        }
    }
}
