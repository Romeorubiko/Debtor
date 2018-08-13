package com.apkproject.debtor.dataStructure.person;

import com.apkproject.debtor.dataStructure.alerts.Notification;
import com.apkproject.debtor.dataStructure.alerts.Request;
import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.tools.Currency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User extends Contact implements Serializable {

    private String email;
    private Currency currency;
    private List<Contact> contactList;
    private List<Debt> debtList;
    private List<Notification> notificationList;
    private List<Request> requestList;

    public User(String name, String email, Currency currency) {
        super(name);
        this.email = email;
        this.currency = currency;
        contactList = new ArrayList<>();
        debtList = new ArrayList<>();
        notificationList = new ArrayList<>();
        requestList = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public List<Debt> getDebtList() {
        return debtList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setPhoto(String photo){
        this.setPhoto(photo);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void addContact(Contact c){
        contactList.add(c);
    }

    public void addUser(User u){
        contactList.add(u);
    }

    public void addDebt(Debt d){
        debtList.add(d);
    }

    public boolean isInMyContactList(String name){
        for (Contact c : contactList) {
            if(c.getName().equals(name))return true;
        }
        return false;
    }

    @Override
    public boolean isAUser() {
        return true;
    }

    public void deleteContact(String name){
        for (Contact c : contactList) {
            if(c.getName().equals(name)){
                contactList.remove(c);
                return;
            }
        }
    }

    public void deleteAccount(){
        //todo delete account on firebase
    }

    public double getCurrentIOwe(){
        double result = 0;
        for (Debt d: getDebtList()) {
            if(d.getBalance()>0 && d.getFrom().getName().equals(getName())) result = result + d.getBalance();
        }
        return result;
    }

    public double getCurrentIAreOwed(){
        double result = 0;
        for (Debt d: getDebtList()) {
            if(d.getBalance()>0 && d.getTo().getName().equals(getName())) result = result + d.getBalance();
        }
        return result;
    }

    public double getBalance(){
        return getCurrentIAreOwed()-getCurrentIOwe();
    }

    /*todo
    public User findUserById(){

    }*/
}
