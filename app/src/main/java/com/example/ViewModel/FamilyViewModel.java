package com.example.ViewModel;

import android.app.Application;

import com.example.room.Bird;
import com.example.room.BirdRepository;
import com.example.room.Family;
import com.example.room.FamilyRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class FamilyViewModel extends AndroidViewModel {

    private FamilyRepository familyRepository;
    private LiveData<List<Family>> allFamilies;


    public FamilyViewModel(@NonNull Application application)
    {
        super(application);
        familyRepository = new FamilyRepository(application);
        allFamilies = familyRepository.getAllFamilies();
    }



    public void insertFamily(Family family){
        familyRepository.insertFamily(family);
    }

    public void updateFamily(Family family){
        familyRepository.updateFamily(family);
    }

    public void deleteFamily(Family family){
        familyRepository.deleteFamily(family);
    }

    public LiveData<List<Family>> getAllFamilies()
    {
        return allFamilies;
    }

    public void deleteAllFamilies()
    {
        familyRepository.deleteAllFamilies();
    }


}
