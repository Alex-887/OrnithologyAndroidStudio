package com.example.room;


import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@androidx.room.Dao
public interface Dao {

    @Insert
    public void addBird(Bird bird);



    @Query("Select * from bird")
    public List<Bird> getBirds();

    @Delete
    void deleteBird(Bird bird);




//    @Query("SELECT * FROM bird")
//    List<Bird> getAll();
//
//    @Delete
//    void delete(Bird bird);




}
