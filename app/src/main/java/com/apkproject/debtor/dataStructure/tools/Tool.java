package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;
import android.widget.Toast;

import com.apkproject.debtor.dataStructure.person.User;

import java.io.IOException;

public final class Tool {

    public static boolean isAnEmail(String email){
        if(email.contains("@")) return true;
        else return false;
    }

    public static User getCurrentUser(Context context) throws IOException, ClassNotFoundException {
        return (User) Storage.readLocalStorage(context, Storage.ME);
    }

    public static void toastMessage(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

    public static Long getNewID(){
        //todo
        return new Long(0);
    }
}
