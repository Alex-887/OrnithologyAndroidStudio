package com.example.ViewModel;
import android.app.Application;
import com.example.room.Bird;
import com.example.room.BirdRepository;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class BirdViewModel extends AndroidViewModel {

    private BirdRepository birdRepository;
    private LiveData<List<Bird>> allBirds;


    public BirdViewModel(@NonNull Application application) {
        super(application);
        birdRepository = new BirdRepository(application);
        allBirds = birdRepository.getAllBirds();
    }


    public void insertBird(Bird bird){
        birdRepository.insertBird(bird);
    }

    public void updateBird(Bird bird){
        birdRepository.updateBird(bird);
    }


    public void deleteBird(Bird bird){
        birdRepository.deleteBird(bird);
    }

    public LiveData<List<Bird>> getAllBirds()
    {
        return allBirds;
    }


}
