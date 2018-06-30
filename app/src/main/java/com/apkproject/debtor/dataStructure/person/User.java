package com.apkproject.debtor.dataStructure.person;

import com.apkproject.debtor.dataStructure.debts.Debt;

import java.io.Serializable;
import java.util.LinkedList;

public class User extends Contact implements Serializable {
    String password; //Do we need a password here?
    LinkedList<Debt> iOwe;
    LinkedList<Debt> theyOwe;
    String favCurrency;
    LinkedList<Contact> contactList;

    public User(String name, String surname1, String surname2, String email, String password, String favCurrency) {
        super(name, surname1, surname2, email);
        this.password = password;
        this.favCurrency = favCurrency;
        iOwe = new LinkedList<>();
        theyOwe = new LinkedList<>();
        contactList = new LinkedList<>();
    }

    public String getFavCurrency() {
        return favCurrency;
    }

    public void setFavCurrency(String favCurrency) {
        this.favCurrency = favCurrency;
    }
    //todo: removeall for iOwe, they Owe; or just remove and especific item
    //todo: add, modificate or remove contacts from contactList

}
