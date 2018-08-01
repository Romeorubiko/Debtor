package com.apkproject.debtor.dataStructure.person;

import java.io.Serializable;

public class Contact implements Serializable{
    private String name;
    private String photo; //todo: idk the class for images

    public Contact(String name) {
        this.name = name;
        this.photo = "default";
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isAUser(){
        return false;
    }
}
