package com.prk.bindingConsumer.basic;

public class BasicLoanDetails {
    private double amount;
    private String startDate;
    private String endDate;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "LoneDetails{" + "amount=" + amount + ", startDate='" + startDate + '\'' +
                ", endDate='" + '\'' + '}';
    }
}
