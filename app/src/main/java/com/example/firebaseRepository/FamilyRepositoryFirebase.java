package com.example.firebaseRepository;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseEntities.Family_Firebase;
import com.example.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FamilyRepositoryFirebase {


    private LiveData<List<Bird_Firebase>> allBirds;
    private LiveData<List<Bird_Firebase>> allBirdsFromFamily;

    private String familyId;

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






    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insertFamily(final Family_Firebase family, final OnAsyncEventListener callback) {

        String id = FirebaseDatabase.getInstance()
                .getReference("families").push().getKey();

        FirebaseDatabase.getInstance()
                .getReference("families")
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
    }


    public LiveData<Family_Firebase> getFamily() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("families");

        return new FamilyLiveData(reference);


    }


}
