package com.apkproject.debtor.dataStructure.person;

import android.graphics.Bitmap;

import com.apkproject.debtor.dataStructure.alerts.Notification;
import com.apkproject.debtor.dataStructure.alerts.Request;
import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.tools.Currency;
import com.apkproject.debtor.dataStructure.tools.SerialBitmap;

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
    private SerialBitmap photo;

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

    public void setPhoto(SerialBitmap photo){
        this.photo = photo;
    }
    public Bitmap getPhoto() {
        return photo.getBitmap();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //add contact sorted by name
    public void addContact(Contact contact){
        for (int i = 0; i < contactList.size(); i++){
            if(contactList.get(i).getName().toLowerCase().charAt(0) > contact.getName().toLowerCase().charAt(0))contactList.add(i+1, contact);
        }
        contactList.add(contact);
    }

    //add contact sorted by date
    public void addDebt(Debt d){
        debtList.add(0,d);
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


    public User findUserByMail(String email){
        for (Contact c: contactList) {
            if(c.isAUser()){
                if(((User)c).getEmail().equals(email))return (User) c;
            }
        }
        return null;
    }

    public Contact findUserByName(String name){
        for (Contact c: contactList) {
                if(c.getName().equals(name))return c;
        }
        return null;
    }

    public List<Contact> findAllWithName(String name){
        List<Contact> result = new ArrayList<>();
        for (Contact c: contactList) {
            if (c.getName().contains(name)) result.add(c);
        }
        return result;
    }
}
