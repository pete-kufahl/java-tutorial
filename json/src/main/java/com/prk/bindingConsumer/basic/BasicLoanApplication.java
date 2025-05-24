package com.prk.bindingConsumer.basic;

import com.prk.common.Job;

import java.util.List;
import java.util.stream.Collectors;

public class BasicLoanApplication {
    // when using the binding api, field names in the POJO must match the field names in the JSON
    private String name;
    private String purposeOfLoan;
    private String comments;
    private BasicLoanDetails loanInfo;
    private BasicLoanDetails loanDetails;
    private List<Job> jobs;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BasicLoanDetails getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(BasicLoanDetails loanInfo) {
        this.loanInfo = loanInfo;
    }

    public BasicLoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(BasicLoanDetails loanDetails) {
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
