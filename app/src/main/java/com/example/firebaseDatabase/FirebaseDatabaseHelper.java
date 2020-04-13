package com.example.firebaseDatabase;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseEntities.Family_Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;

    private DatabaseReference mReferenceBirds;
    private List<Bird_Firebase> birds = new ArrayList<>();

    private DatabaseReference mReferenceFamilies;
    private List<Family_Firebase> families = new ArrayList<>();


    public FirebaseDatabaseHelper(){

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceBirds = mDatabase.getReference("birds");
        mReferenceFamilies = mDatabase.getReference("families");

    }



    public interface DataBirdsStatus {
        void DataIsLoaded(List<Bird_Firebase> birds, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }


    public interface DataFamiliesStatus{
        void DataIsLoaded(List<Family_Firebase> families, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }



    public void readBirds(final DataBirdsStatus dataStatus){
        mReferenceBirds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                birds.clear();

                List<String> birdsKeys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    birdsKeys.add(keyNode.getKey());
                    Bird_Firebase bird = keyNode.getValue(Bird_Firebase.class);

                    birds.add(bird);



                }


                dataStatus.DataIsLoaded(birds, birdsKeys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void readFamilies(final DataFamiliesStatus dataStatus){
        mReferenceFamilies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                families.clear();

                List<String> familiesKeys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    familiesKeys.add(keyNode.getKey());
                    Family_Firebase family = keyNode.getValue(Family_Firebase.class);
                    families.add(family);


                }
                dataStatus.DataIsLoaded(families, familiesKeys);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
