package com.prk.common;

import java.util.Date;

public class LoanDetails {
    private double amount;
    private Date startDate;
    private Date endDate;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "LoneDetails{" + "amount=" + amount + ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' + '}';
    }
}
