package com.prk.demos;

import com.prk.common.Job;
import com.prk.common.LoanApplication;
import com.prk.common.LoanDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ExampleLoan {
    public static final LoanApplication LOAN_APPLICATION;

    static {
        final LoanDetails loanDetails = new LoanDetails();
        loanDetails.setAmount(10_000.00);


        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 15, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        loanDetails.setStartDate(startDate);
        calendar.set(2025, Calendar.JANUARY, 15, 0, 0, 0);
        Date endDate = calendar.getTime();
        loanDetails.setEndDate(endDate);

        final List<Job> jobs = new ArrayList<>();

        Job job0 = new Job();
        job0.setTitle("freelance developer");
        job0.setAnnualIncome(18_000);
        job0.setYearsActive(3);
        jobs.add(job0);

        Job job1 = new Job();
        job1.setTitle("part time developer");
        job1.setAnnualIncome(40_000);
        job1.setYearsActive(8);
        jobs.add(job1);

        LOAN_APPLICATION = new LoanApplication();
        LOAN_APPLICATION.setName("Pancho the Dog");
        LOAN_APPLICATION.setPurposeOfLoan("to build an extension to the house");
        LOAN_APPLICATION.setLoanDetails(loanDetails);
        LOAN_APPLICATION.setJobs(jobs);
    }
}
