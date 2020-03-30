package com.example.ViewModel;

import android.app.Application;

import com.example.room.BirdRepository;
import com.example.room.FamilyRepository;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BirdViewModelFactory implements ViewModelProvider.Factory {

//
//    private final BirdRepository birdRepository;
//    private final FamilyRepository familyRepository;
//    private final Executor executor;

    private Application mApplication;
    private String mfamily;





    public BirdViewModelFactory(@NonNull Application application, String family) {
        mApplication = application;
        mfamily = family;

    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BirdViewModel(mApplication, mfamily);
    }



}
