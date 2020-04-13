package com.example.firebaseViewModel;

import android.app.Application;

import com.example.firebaseRepository.BirdRepositoryFirebase;
import com.example.firebaseEntities.Bird_Firebase;
import com.example.BaseApp;
import com.example.util.OnAsyncEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

//import com.example.ornithology_favre_berthouzoz.BaseApp;


public class BirdViewModelFirebase extends AndroidViewModel {

    private BirdRepositoryFirebase mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Bird_Firebase> mObservableBird;

    public BirdViewModelFirebase(@NonNull Application application,
                            final String familyId, BirdRepositoryFirebase birdRepositoryFirebase) {
        super(application);

      //  mRepository = birdRepository;

        mObservableBird = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableBird.setValue(null);

        if (familyId != null) {
            LiveData<Bird_Firebase> bird = mRepository.getBirdsFromFamilyId(familyId);

            // observe the changes of the account entity from the database and forward them
            mObservableBird.addSource(bird, mObservableBird::setValue);
        }
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mFamilyId;

        private final BirdRepositoryFirebase mRepository;

        public Factory(@NonNull Application application, String familyId) {
            mApplication = application;
            mFamilyId = familyId;
            mRepository = ((BaseApp) application).getBirdRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BirdViewModelFirebase(mApplication, mFamilyId, mRepository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<Bird_Firebase> getBirds() {
        return mObservableBird;
    }





    public void createBird(Bird_Firebase bird, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getBirdRepository()
                .insertBird(bird, callback);
    }

    public void updateBird(Bird_Firebase bird, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getBirdRepository()
                .updateBird(bird, callback);
    }



}
