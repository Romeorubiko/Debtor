package com.apkproject.debtor.dataStructure.person;

import java.io.Serializable;

public class Contact implements Serializable{
    String name;
    String surname1;
    String surname2;
    String email;
    String id;

    //todo: id system

    public Contact(String name, String surname1, String surname2, String email) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.email = email;
        //asognar id
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
