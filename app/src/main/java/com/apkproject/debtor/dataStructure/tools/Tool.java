package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.person.Contact;
import com.apkproject.debtor.dataStructure.person.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    // Decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

}
