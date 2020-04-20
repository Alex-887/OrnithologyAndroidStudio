package com.example.firebaseRepository;

import android.util.Log;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseEntities.Family_Firebase;
import com.example.pojo.FamiliesWithBirds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class FamiliesWithBirdsListLiveData extends LiveData<List<FamiliesWithBirds>> {

    private static final String TAG = "BirdsLiveData";

    private final DatabaseReference reference;

    private final FamiliesWithBirdsListLiveData.MyValueEventListener listener =
            new FamiliesWithBirdsListLiveData.MyValueEventListener();



    public FamiliesWithBirdsListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }



    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toFamilyWithBirdsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }



    private List<FamiliesWithBirds> toFamilyWithBirdsList(DataSnapshot dataSnapshot){
        List<FamiliesWithBirds> familiesWithBirdsList = new ArrayList<>();


        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
            FamiliesWithBirds familiesWithBirds = new FamiliesWithBirds();
            familiesWithBirds.family = childSnapshot.getValue(Family_Firebase.class);
            familiesWithBirds.family.setFamilyId(childSnapshot.getKey());

            familiesWithBirds.birds = toBirds(childSnapshot.child("birds"),
                    childSnapshot.getKey());

            familiesWithBirdsList.add(familiesWithBirds);
        }


     return familiesWithBirdsList;
    }



    private List<Bird_Firebase> toBirds(DataSnapshot snapshot, String familyId) {
        List<Bird_Firebase> birds = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Bird_Firebase entity = childSnapshot.getValue(Bird_Firebase.class);
            entity.setId(childSnapshot.getKey());
            entity.setFamilyId(familyId);
            birds.add(entity);
        }
        return birds;
    }
}