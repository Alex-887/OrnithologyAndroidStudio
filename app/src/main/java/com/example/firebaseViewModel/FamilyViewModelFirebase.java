package com.example.firebaseViewModel;

import android.app.Application;

import com.example.firebaseEntities.Family_Firebase;
import com.example.firebaseRepository.FamilyRepositoryFirebase;
import com.example.BaseApp;
import com.example.util.OnAsyncEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FamilyViewModelFirebase extends AndroidViewModel {

    private FamilyRepositoryFirebase mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Family_Firebase> mObservableFamily;

    public FamilyViewModelFirebase(@NonNull Application application,
                                    FamilyRepositoryFirebase familyRepository) {
        super(application);

        mRepository = familyRepository;

        mObservableFamily = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableFamily.setValue(null);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;


        private final FamilyRepositoryFirebase mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((BaseApp) application).getFamilyRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FamilyViewModelFirebase(mApplication, mRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<Family_Firebase> getFamily() {
        return mObservableFamily;
    }

    public void createFamily(Family_Firebase family, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFamilyRepository()
                .insertFamily(family, callback);
    }

    public void updateFamily(Family_Firebase family, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFamilyRepository()
                .updateFamily(family, callback);
    }


    public void deleteFamily(Family_Firebase family, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFamilyRepository()
                .deleteFamily(family, callback);
    }





}