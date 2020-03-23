package com.example.room;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    @Insert
    public void insertBird(Bird bird);

    @Update
    void updateBird(Bird bird);


    //order by is to have them in alphabetical order
    @Query("Select * from bird ORDER BY name")
    public LiveData<List<Bird>> getBirds(); //liveData is to update according to the database

    @Delete
    void deleteBird(Bird bird);


//
//    @Delete
//    void delete(Bird bird);




}
