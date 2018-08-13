package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.person.Contact;
import com.apkproject.debtor.dataStructure.person.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    public static Long getNewDebtID(){
        //todo
        return new Long(0);
    }

    public static Long getNewUserID(){
        //todo
        return new Long(0);
    }

    //convert an amount to a currency format: x0.00
    public static String toCurrencyFormat(double d){
        return new DecimalFormat("#0.00").format(d);
    }

    //convert an amount to a currency format: x0.00 and add the symbol for the currency description
    public static String toCurrencyAndSymbol(double d, Currency c){
        return (toCurrencyFormat(d) + c.getSymbol());
    }

    //return a list to display in my lists activity (recyclerview)
    public static List<Object> sortContactsAndDebts(User me){
        List<Object> result = new ArrayList<>();
        Log.d("Sort", "sorting contacts...");
//todo
        for (Debt d : me.getDebtList()) {

        }

        return result;
    }

}
