package com.apkproject.debtor.test;

import android.content.Context;

import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Currency;
import com.apkproject.debtor.dataStructure.tools.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Test {
    public static void createUser(Context context) throws IOException {
        User me = new User("Anna Ejemplo","anna@falso.com", Currency.EUR);
        User mike = new User("Mike Tyson", "mike@falso.com", Currency.EUR);
        User evan = new User("Evan Garó", "evan@falso.com", Currency.EUR);
        User petra = new User("Petra Shepard", "petra@falso.com", Currency.EUR);
        me.addUser(mike);
        me.addUser(evan);
        me.addUser(petra);
        Debt d1 = new Debt(mike, me, 10.55, "Dinner at Moncloa", Currency.EUR);
        Debt d2 = new Debt(me, mike, 15, "Evan's present", Currency.EUR);
        Debt d3 = new Debt(mike, me, 15.60, "London apartment", Currency.EUR);
        Debt d4 = new Debt(me , evan, 15, "Party with Petra", Currency.EUR);
        Debt d5 = new Debt(me, petra, 5.25, "Party with Petra", Currency.EUR);

        me.addDebt(d1);
        me.addDebt(d2);
        me.addDebt(d3);
        me.addDebt(d4);
        me.addDebt(d5);

        Storage.writeLocalStorage(context, Storage.ME, me);
    }

    public static List<Object> createMyLists(){
        List<Object> result = new ArrayList<>();

        User me = new User("Anna Ejemplo","anna@falso.com", Currency.EUR);
        User mike = new User("Mike Tyson", "mike@falso.com", Currency.EUR);
        User evan = new User("Evan Garó", "evan@falso.com", Currency.EUR);
        User petra = new User("Petra Shepard", "petra@falso.com", Currency.EUR);

        Debt d1 = new Debt(mike, me, 10.55, "Dinner at Moncloa", Currency.EUR);
        Debt d2 = new Debt(me, mike, 15, "Evan's present", Currency.EUR);
        Debt d3 = new Debt(mike, me, 15.60, "London apartment", Currency.EUR);
        Debt d4 = new Debt(me , evan, 15, "Party with Petra", Currency.EUR);
        Debt d5 = new Debt(me, petra, 5.25, "Party with Petra", Currency.EUR);

        mike.addDebt(d1);
        mike.addDebt(d2);
        mike.addDebt(d3);
        evan.addDebt(d4);
        petra.addDebt(d5);

        result.add(Calendar.getInstance());
        result.add(evan);
        result.add(mike);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,Calendar.JULY);
        result.add(c);
        result.add(petra);

        return result;
    }
}
