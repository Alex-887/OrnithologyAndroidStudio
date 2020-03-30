package com.example.room;

import com.google.firebase.database.core.Repo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface FamilyDao {

    //on conflict => if there is a family with a name already present, it will display a mistake but still add it, with IGNORE we can say the error is not important
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFamily(Family family);

    @Update
    void updateFamily(Family family);

    @Delete
    void deleteFamily(Family family);

    @Query("DELETE  FROM family")
    void deleteAllFamily();

    @Query("Select * from family ORDER BY family")
    public LiveData<List<Family>> getFamilies(); //liveData is to update according to the database

    @Query("Select family from family WHERE family=:family")
    public LiveData<Family> getFamily(String family); //liveData is to update according to the database


}
