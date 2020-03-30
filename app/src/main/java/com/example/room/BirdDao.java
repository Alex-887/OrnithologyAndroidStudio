package com.example.room;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface BirdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertBird(Bird bird);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateBird(Bird bird);


    @Query("UPDATE bird SET family=:family ")
    void updateFamilyInBird(String family );


    @Query("DELETE FROM bird")
    void deleteAllBirds();


    //order by is to have them in alphabetical order
    @Query("Select * from bird ORDER BY name")
    public LiveData<List<Bird>> getBirds(); //liveData is to update according to the database


    @Query("Select * from bird WHERE family=:family ORDER BY name")
    public LiveData<List<Bird>> getBirdsFromFamily(String family);


//    @Query("Select * from bird INNER JOIN family ON family.id = bird.familyId ORDER BY name")
//    public LiveData<List<Bird>> getBirds(); //liveData is to update according to the database


    @Delete
    void deleteBird(Bird bird);



}
