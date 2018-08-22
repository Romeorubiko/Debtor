package com.apkproject.debtor.dataStructure.debts;

import com.apkproject.debtor.dataStructure.person.Contact;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

//What defines a debt
public class Debt implements Serializable{

	private final String ID;
	private Contact from;
	private Contact to;
	private double amount;
	private Calendar lastUpdate;
	private String description;
	private List<Payment> payments;
	private com.apkproject.debtor.dataStructure.tools.Currency currency;


	public Debt(Contact from, Contact to, double amount, String description, com.apkproject.debtor.dataStructure.tools.Currency currency) {

        ID = UUID.randomUUID().toString();
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.lastUpdate = Calendar.getInstance();
		this.description = description;
		this.payments = new ArrayList<>();
		this.currency = currency;
	}

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return ID;
    }

    public Contact getFrom() {
        return from;
    }

    public Contact getTo() {
        return to;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public com.apkproject.debtor.dataStructure.tools.Currency getCurrency() {
        return currency;
    }

    public double getBalance () {
        double total_payements = 0;

        for (Payment payment: this.payments) total_payements += payment.getAmount();

        return this.amount-total_payements;
    }

    public void pay (Payment payment) {
	    if (checkPayment(payment)) {
	        this.payments.add(payment);
        }

    }

    public boolean checkPayment (Payment payment) {
	    //TODO: implement the remaining necessary checks for payement of debt
	    if (payment.getAmount() <= 0) return false;
	    return true;
    }

    public boolean isSettled () {
	    if (this.getBalance()==0) return true;
	    return false;
    }
}
