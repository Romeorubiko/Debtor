package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;

import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.person.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raul on 18/12/2016.
 */

public final class Storage {

    //all methods for storage

        public static final String ME = "ME";
        public static final String GROUPUSERS = "users";
        public static final String GROUPDEBTS = "debts";

        private Storage() {}


        //Writes in phone
        public static void writeLocalStorage(Context context, String key, Object object) throws IOException {
            FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }

        //Reads from phone
        public static Object readLocalStorage(Context context, String key) throws IOException,
                ClassNotFoundException {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        }

        //writes on firebise any object but you have to different the group it belongs
        public static void writeFirebase(String group, String id, Object value) {
            FirebaseDatabase.getInstance().getReference().child(group).child(id).setValue(value);
        }

        //read user account from firebase given an id
        public static User readUserFirebase(Context context, final String id){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Storage.GROUPUSERS);
            final List<User> userList= new ArrayList<User>();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot item: dataSnapshot.getChildren()) {
                        if (item.getKey().equals(id)) {
                            User user = dataSnapshot.getValue(User.class);
                            userList.add(user);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return userList.get(0);
        }

        //read debt from firebase given an id
        public static Debt readDebtFirebase(Context context, final String id){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Storage.GROUPDEBTS);
            final List<Debt> debtList= new ArrayList<Debt>();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot item: dataSnapshot.getChildren()) {
                            if (item.getKey().equals(id)) {
                                Debt debt = dataSnapshot.getValue(Debt.class);
                                debtList.add(debt);
                            }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return debtList.get(0);
        }
}
