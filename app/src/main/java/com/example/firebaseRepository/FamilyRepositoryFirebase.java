package com.example.firebaseRepository;
import com.example.firebaseEntities.Family_Firebase;
import com.example.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FamilyRepositoryFirebase {


    private static FamilyRepositoryFirebase instance;


    public static FamilyRepositoryFirebase getInstance(){
        if(instance == null){
            synchronized (FamilyRepositoryFirebase.class){
                if(instance == null){
                    instance = new FamilyRepositoryFirebase();
                }
            }
        }
        return instance;
    }




    public LiveData<List<Family_Firebase>> getAllFamilies(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("families");

        return new FamilyListLiveData(reference);
    }


    public LiveData<Family_Firebase> getFamily(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("families")
                .child(id)
                .child("familyName");

        return new FamilyLiveData(reference);

    }


    //Because we want to retrieve the name of the family inside birds, we will do all these "queries" inside families and inside birds.



    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insertFamily(final Family_Firebase family, final OnAsyncEventListener callback) {

        String id = FirebaseDatabase.getInstance()
                .getReference("families").push().getKey();

        FirebaseDatabase.getInstance().getReference("families").child(id)
                .setValue(family, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });


        //cf database
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(id)
                .setValue(family, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });


    }



    public void updateFamily(final Family_Firebase family, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("families")
                .child(family.getFamilyId())
                .updateChildren(family.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

        //cf database
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(family.getFamilyId())
                .child("familyName")
                .updateChildren(family.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });



    }



    public void deleteFamily(final Family_Firebase family, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("families")
                .child(family.getFamilyId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

        //cf database
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(family.getFamilyId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }



    public void deleteAllFamilies(OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("families")
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

        //cf database
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }





}
