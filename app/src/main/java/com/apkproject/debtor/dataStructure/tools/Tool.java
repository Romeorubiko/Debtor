package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;

import com.apkproject.debtor.dataStructure.person.User;

import java.io.IOException;

public final class Tool {

    public boolean isAnEmail(String email){
        if(email.contains("@")) return true;
        else return false;
    }

    public User getCurrentUser(Context context) throws IOException, ClassNotFoundException {
        return (User) Storage.readInLocalStorage(context, Storage.ME);
    }

}
