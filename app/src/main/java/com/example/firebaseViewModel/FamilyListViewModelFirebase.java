package com.example.firebaseViewModel;

import android.app.Application;

import com.example.firebaseEntities.Family_Firebase;
import com.example.firebaseRepository.FamilyRepositoryFirebase;
import com.example.BaseApp;
import com.example.util.OnAsyncEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class FamilyListViewModelFirebase extends AndroidViewModel {

    private static final String TAG = "AccountListViewModel";

    private final MediatorLiveData<List<Family_Firebase>> mObservableFamilies;
    private FamilyRepositoryFirebase mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    public FamilyListViewModelFirebase(@NonNull Application application, FamilyRepositoryFirebase familyRepository) {
        super(application);

        mRepository = familyRepository;

        mObservableFamilies = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableFamilies.setValue(null);


        LiveData<List<Family_Firebase>> families = mRepository.getAllFamilies();

        // observe the changes of the entities from the database and forward them
        mObservableFamilies.addSource(families, mObservableFamilies::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final FamilyRepositoryFirebase familyRepositoryFirebase;


        public Factory(@NonNull Application application) {
            mApplication = application;
            familyRepositoryFirebase = ((BaseApp) application).getFamilyRepository();


        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FamilyListViewModelFirebase(mApplication, familyRepositoryFirebase);
        }
    }



    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<Family_Firebase>> getFamilies() {
        return mObservableFamilies;
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
