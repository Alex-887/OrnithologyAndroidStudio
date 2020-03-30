package com.example.ViewModel;
import android.app.Application;

//import com.example.ornithology_favre_berthouzoz.BaseApp;
import com.example.room.Bird;
import com.example.room.BirdRepository;
import com.example.room.Family;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class BirdViewModel extends AndroidViewModel {

    //private BirdRepository birdRepository;

    private LiveData<List<Bird>> allBirds;
    private Application application;

    private LiveData<List<Bird>> allBirdsFromFamily;



    private BirdRepository repository;

    @Nullable
    private LiveData<Family> currentFamily;



    public BirdViewModel(@NonNull Application application, String family ) {
        super(application);
        this.application = application;

        repository = new BirdRepository(application);

        allBirds = repository.getAllBirds();

        allBirdsFromFamily = repository.getAllBirdsFromFamily(family);

    }


    public void insertBird(Bird bird){
        repository.insertBird(bird);
    }

    public void updateBird(Bird bird){
        repository.updateBird(bird);
    }

    public void deleteAllBirds() {
        repository.deleteAllBird();
    }

    public void deleteBird(Bird bird){
        repository.deleteBird(bird);
    }

    public LiveData<List<Bird>> getAllBirds() { return repository.getAllBirds(); }

    public LiveData<List<Bird>> getAllBirdsFromFamily(String family) { return repository.getAllBirdsFromFamily(family); }



}
