package com.apkproject.debtor.dataStructure.debts;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Payment implements Serializable {
    private long debtId;
    private double amount;
    private Calendar date;

    public Payment(long debtId, double amount, Calendar date) {
        this.debtId = debtId;
        this.amount = amount;
        this.date = date;
    }

    public long getDebtId() {
        return debtId;
    }

    public double getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }
}
