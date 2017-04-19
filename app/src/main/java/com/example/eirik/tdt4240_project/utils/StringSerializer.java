package com.example.eirik.tdt4240_project.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Sindre on 4/19/2017.
 */

public class StringSerializer {

    // ref http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again
    public static String serializeToString(Object o) {
        String serializedObject = "";

        // serialize the object
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(o);
            so.flush();
            serializedObject = bo.toString();
        } catch (Exception e) {
            Log.d("serializer", e.getMessage());
        }

        return serializedObject;
    }

    @Nullable
    public static <T extends Object> T constructFromString(String s) {
        // deserialize the object
        try {
            byte b[] = s.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            T obj = (T) si.readObject();
            return obj;
        } catch (Exception e) {
            Log.d("serializer", e.getMessage());
        }
        return null;
    }
}
