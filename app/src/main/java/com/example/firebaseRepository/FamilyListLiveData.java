package com.example.firebaseRepository;

import android.util.Log;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseEntities.Family_Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class FamilyListLiveData extends LiveData<List<Family_Firebase>> {

    private static final String TAG = "FamilyAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public FamilyListLiveData(DatabaseReference ref) {
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
            setValue(toFamilyList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Family_Firebase> toFamilyList(DataSnapshot snapshot) {
        List<Family_Firebase> families = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Family_Firebase entity = childSnapshot.getValue(Family_Firebase.class);
            entity.setFamilyId(childSnapshot.getKey());


            families.add(entity);

        }
        return families;
    }
}