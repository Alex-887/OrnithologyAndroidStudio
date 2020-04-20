package com.example.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BirdViewModelFactory implements ViewModelProvider.Factory {


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
