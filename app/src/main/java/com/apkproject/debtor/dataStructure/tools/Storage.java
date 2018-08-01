package com.apkproject.debtor.dataStructure.tools;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Raul on 18/12/2016.
 */

public final class Storage {

        public static final String ME = "ME";
        private Storage() {}

        public static void writeInLocalStorage(Context context, String key, Object object) throws IOException {
            FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }

        public static Object readInLocalStorage(Context context, String key) throws IOException,
                ClassNotFoundException {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        }
}
