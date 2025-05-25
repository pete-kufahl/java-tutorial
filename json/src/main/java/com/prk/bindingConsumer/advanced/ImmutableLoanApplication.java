package com.prk.bindingConsumer.advanced;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prk.common.Job;
import com.prk.common.LoanDetails;


import java.util.List;
import java.util.stream.Collectors;

public class ImmutableLoanApplication {
    private final String name;
    private final String purposeOfLoan;
    private final LoanDetails loanDetails;
    private final List<Job> jobs;

    @JsonCreator
    public ImmutableLoanApplication(
            @JsonProperty("name") final String name,
            @JsonProperty("purposeOfLoan") final String purposeOfLoan,
            @JsonProperty("loanDetails") final LoanDetails loanDetails,
            @JsonProperty("jobs") final List<Job> jobs
    ) {
        this.name = name;
        this.purposeOfLoan = purposeOfLoan;
        this.loanDetails = loanDetails;
        this.jobs = List.copyOf(jobs);
    }

    public String getName() {
        return name;
    }

    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return "LoanApplication{" + "name='" + '\'' + ", purposeOfLoan='" + purposeOfLoan + '\'' +
                ", loanDetails=\n\t" + loanDetails + ", jobs=\n\t" +
                jobs.stream()
                        .map(Job::toString)
                        .collect(Collectors.joining("\n\t\t", "[\n\t\t", "\n\t]")) +
                '}';
    }

}
