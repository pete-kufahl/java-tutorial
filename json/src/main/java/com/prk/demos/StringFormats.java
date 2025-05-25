package com.prk.demos;

import com.prk.common.Job;
import com.prk.common.LoanApplication;
import com.prk.common.LoanDetails;

import java.util.List;
import static java.util.stream.Collectors.joining;

public class StringFormats {
    public static void main(String[] args) {
        LoanApplication app = ExampleLoan.LOAN_APPLICATION;
        System.out.println(app);
        System.out.println();
        System.out.println(toJsonString(app));
    }

    private static CharSequence toJsonString(LoanApplication app) {
        return String.format(
                """
                {
                 "name": "%s",
                 "purposeOfLoan": "%s",
                 "loanDetails": %s,
                 "jobs": %s
                }""",
                app.getName(),
                app.getPurposeOfLoan(),
                toJsonString(app.getLoanDetails()),
                toJsonString(app.getJobs()));
    }

    private static Object toJsonString(List<Job> jobs) {
        return jobs.stream()
                .map(j -> String.format(
                        """
                        {
                          "title": "%s",
                          "annualIncome": "%g",
                          "yearsActive": "%d"
                        }""",
                        j.getTitle(),
                        j.getAnnualIncome(),
                        j.getYearsActive()))
                .collect(joining(",\n", "[\n", "\n ]"));
    }

    private static CharSequence toJsonString(LoanDetails loanDetails) {
        return String.format(
                """
                {
                   "amount": "%g",
                   "startDate": "%s",
                   "endDate": "%s"
                }""",
                loanDetails.getAmount(),
                loanDetails.getStartDate(),
                loanDetails.getEndDate());
    }
}
