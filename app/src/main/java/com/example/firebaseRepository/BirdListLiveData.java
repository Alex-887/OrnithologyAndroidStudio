package com.example.firebaseRepository;

import android.util.Log;

import com.example.firebaseEntities.Bird_Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class BirdListLiveData extends LiveData<List<Bird_Firebase>> {

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public BirdListLiveData(DatabaseReference ref) {
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
            setValue(toBirdList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Bird_Firebase> toBirdList(DataSnapshot snapshot) {
        List<Bird_Firebase> birds = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Bird_Firebase entity = childSnapshot.getValue(Bird_Firebase.class);
            entity.setId(childSnapshot.getKey());
            birds.add(entity);
        }
        return birds;
    }
}