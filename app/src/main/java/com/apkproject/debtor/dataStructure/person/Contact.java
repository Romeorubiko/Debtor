package com.apkproject.debtor.dataStructure.person;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Contact implements Serializable{
    private String name;

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isAUser(){
        return false;
    }
}
