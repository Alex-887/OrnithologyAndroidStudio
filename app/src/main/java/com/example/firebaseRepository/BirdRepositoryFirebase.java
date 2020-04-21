package com.example.firebaseRepository;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.pojo.FamiliesWithBirds;
import com.example.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BirdRepositoryFirebase {



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




    public LiveData<List<FamiliesWithBirds>> getAllBirds(){

        DatabaseReference birdsReference =
                FirebaseDatabase.getInstance()
                        .getReference("birds");


        return new FamiliesWithBirdsListLiveData(birdsReference);
    }


    public LiveData<List<Bird_Firebase>> getBirdsFromFamilyId(final String id){

        DatabaseReference birdsReference =
                FirebaseDatabase.getInstance()
                        .getReference("birds")
                        .child(id)
                        .child("birds");


        return new BirdsListLiveData(birdsReference, id);
    }


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insertBirdIntoFamily(final Bird_Firebase bird, final OnAsyncEventListener callback, String familyId) {
        String id = FirebaseDatabase.getInstance().getReference("birds").child(familyId).child("birds").push().getKey();


        FirebaseDatabase.getInstance().getReference("birds").child(familyId).child("birds").child(id)
                .setValue(bird, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void updateBird(final Bird_Firebase bird, final OnAsyncEventListener callback, final String familyId) {

        FirebaseDatabase.getInstance()
                .getReference("birds").child(familyId).child("birds")
                .child(bird.getId())
                .updateChildren(bird.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }



    public void deleteBird(final Bird_Firebase bird, OnAsyncEventListener callback, final String familyId) {
        FirebaseDatabase.getInstance()
                .getReference("birds").child(familyId).child("birds")
                .child(bird.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void deleteAllBirds(OnAsyncEventListener callback, final String familyId) {
        FirebaseDatabase.getInstance()
                .getReference("birds").child(familyId).child("birds")
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });




    }
}
