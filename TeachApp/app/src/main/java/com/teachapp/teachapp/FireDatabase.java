package com.teachapp.teachapp;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireDatabase {

    public static DatabaseReference firedatabase;

    public final static DatabaseReference getInstance(){
        if (firedatabase== null){
            firedatabase = FirebaseDatabase.getInstance().getReference();
        }
        return firedatabase;
    }

    public final static DatabaseReference getNewInstance(){
        return FirebaseDatabase.getInstance().getReference();
    }

}
