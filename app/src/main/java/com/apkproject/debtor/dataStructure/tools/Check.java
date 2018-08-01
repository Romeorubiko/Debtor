package com.apkproject.debtor.dataStructure.tools;

public final class Check {

    public boolean isAnEmail(String email){
        if(email.contains("@")) return true;
        else return false;
    }

}
