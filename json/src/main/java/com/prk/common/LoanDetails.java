package com.prk.common;

import java.time.LocalDate;

public class LoanDetails {
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "LoneDetails{" + "amount=" + amount + ", startDate='" + startDate + '\'' +
                ", endDate='" + '\'' + '}';
    }
}
