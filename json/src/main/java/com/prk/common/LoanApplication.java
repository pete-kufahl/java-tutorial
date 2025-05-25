package com.prk.common;

import java.util.List;
import java.util.stream.Collectors;

public class LoanApplication {
    private String name;
    private String purposeOfLoan;
    private LoanDetails loanDetails;
    private List<Job> jobs;

    public LoanApplication() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public void setPurposeOfLoan(String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
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
