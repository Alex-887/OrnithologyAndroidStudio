package com.example.firebaseRepository;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BirdRepositoryFirebase {


    private LiveData<List<Bird_Firebase>> allBirds;
    private LiveData<List<Bird_Firebase>> allBirdsFromFamily;


    private static BirdRepositoryFirebase instance;


    public static BirdRepositoryFirebase getInstance(){
        if(instance == null){
            synchronized (BirdRepositoryFirebase.class){
                if(instance == null){
                    instance = new BirdRepositoryFirebase();
                }
            }
        }
        return instance;
    }




    public BirdRepositoryFirebase() {

    }

    public LiveData<List<Bird_Firebase>> getAllBirds(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("birds");
        return new BirdListLiveData(reference);
    }


    public LiveData<Bird_Firebase> getBirdsFromFamilyId(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("birds").
                        child(id);
        return new BirdLiveData(reference);
    }


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insertBird(final Bird_Firebase bird, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("birds").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(id)
                .setValue(bird, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void updateBird(final Bird_Firebase bird, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(bird.getId())
                .updateChildren(bird.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }



    public void deleteBird(final Bird_Firebase bird, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("birds")
                .child(bird.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }







}
