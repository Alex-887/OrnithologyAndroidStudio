package com.example.firebaseViewModel;

import android.app.Application;

import com.example.BaseApp;
import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseRepository.BirdRepositoryFirebase;
import com.example.firebaseRepository.FamilyRepositoryFirebase;
import com.example.pojo.FamiliesWithBirds;
import com.example.util.OnAsyncEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class BirdListViewModelFirebase extends AndroidViewModel {

    private static final String TAG = "BirdListViewModel";

    private final MediatorLiveData<List<Bird_Firebase>> mObservableBirds;
    private BirdRepositoryFirebase mRepository;
    private final MediatorLiveData<List<FamiliesWithBirds>> mObservableFamiliesWithBirds;



    public BirdListViewModelFirebase(@NonNull Application application, final String id, BirdRepositoryFirebase birdRepositoryFirebase, FamilyRepositoryFirebase familyRepositoryFirebase) {
        super(application);

        mRepository = birdRepositoryFirebase;

        mObservableBirds = new MediatorLiveData<>();
        mObservableFamiliesWithBirds = new MediatorLiveData<>();



        // set by default null, until we get data from the database.
        mObservableBirds.setValue(null);
        mObservableFamiliesWithBirds.setValue(null);

        LiveData<List<FamiliesWithBirds>> familiesWithBirds = birdRepositoryFirebase.getAllBirds();
        LiveData<List<Bird_Firebase>> birds = mRepository.getBirdsFromFamilyId(id);

        // observe the changes of the entities from the database and forward them
        mObservableBirds.addSource(birds, mObservableBirds::setValue);
        mObservableFamiliesWithBirds.addSource(familiesWithBirds, mObservableFamiliesWithBirds::setValue);

    }




    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mId;

        private final BirdRepositoryFirebase birdRepositoryFirebase;
        private final FamilyRepositoryFirebase familyRepositoryFirebase;

        public Factory(@NonNull Application application, String id) {
            mApplication = application;
            birdRepositoryFirebase = ((BaseApp) application).getBirdRepository();
            familyRepositoryFirebase = ((BaseApp)application).getFamilyRepository();
            mId = id;

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new BirdListViewModelFirebase(mApplication, mId, birdRepositoryFirebase, familyRepositoryFirebase);
        }
    }

    public LiveData<List<FamiliesWithBirds>> getAllBirds() {
        return mObservableFamiliesWithBirds;
    }


    public LiveData<List<Bird_Firebase>> getBirdsFromName() {
        return mObservableBirds;
    }

    public void createBird(Bird_Firebase bird, OnAsyncEventListener callback, final String familyId) {
        ((BaseApp) getApplication()).getBirdRepository()
                .insertBirdIntoFamily(bird, callback, familyId);
    }

    public void updateBird(Bird_Firebase bird, OnAsyncEventListener callback, final String familyId) {
        ((BaseApp) getApplication()).getBirdRepository()
                .updateBird(bird, callback, familyId);
    }


    public void deleteBird(Bird_Firebase bird, OnAsyncEventListener callback, final String familyId) {
        ((BaseApp) getApplication()).getBirdRepository()
                .deleteBird(bird, callback, familyId);
    }


    public void deleteAllBirds(OnAsyncEventListener callback, final String familyId) {
        ((BaseApp) getApplication()).getBirdRepository()
                .deleteAllBirds(callback, familyId);
    }


}
