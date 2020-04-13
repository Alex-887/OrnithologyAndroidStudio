package com.example;

import android.app.Application;

import com.example.firebaseRepository.BirdRepositoryFirebase;
import com.example.firebaseRepository.FamilyRepositoryFirebase;

public class BaseApp extends Application {

    public BirdRepositoryFirebase getBirdRepository() {
        return BirdRepositoryFirebase.getInstance();
    }


    public FamilyRepositoryFirebase getFamilyRepository() {
        return FamilyRepositoryFirebase.getInstance();
    }


}