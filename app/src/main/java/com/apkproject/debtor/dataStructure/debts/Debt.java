package com.apkproject.debtor.dataStructure.debts;

import com.apkproject.debtor.dataStructure.person.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//What defines a debt
public class Debt implements Serializable{

	private long id;
	private Contact from;
	private Contact to;
	private double amount;
	private Calendar lastUpdate;
	private String description;
	private List<Payment> payments;
	private com.apkproject.debtor.dataStructure.tools.Currency currency;

	public Debt(Contact from, Contact to, double amount, String description, com.apkproject.debtor.dataStructure.tools.Currency currency) {
		//TODO: generate debt id
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

    public long getId() {
        return id;
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
	    //TODO: when getting debt balance, check if the balance is from or to user, and show a negative or positive balance accordingly
        double total_payements = 0;

        for (Payment payment: this.payments) total_payements += payment.getAmount();

        return this.amount-total_payements;
    }

    public void pay (Payment payment) {
	    payments.add(payment);
    }

    public boolean checkPayment (Payment payment) {
	    //TODO: implement the remaining necessary checks for payement of debt
	    if (payment.getAmount() <= 0) return false;
	    return true;
    }

    public boolean isSettled () {
	    if (this.amount==0) return true;
	    return false;
    }
}
