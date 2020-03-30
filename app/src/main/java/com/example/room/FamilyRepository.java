package com.example.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FamilyRepository {
    private FamilyDao familyDao;

    private LiveData<List<Family>> allFamilies;

    public FamilyRepository(Application application){
        Database database = Database.getInstance(application);
        familyDao = database.familyDao();
        allFamilies = familyDao.getFamilies();
    }


    //empty constructor
    public FamilyRepository() {

    }



    public void insertFamily(Family family){
    new InsertFamilyAsyncTask(familyDao).execute(family);
    }


    public void updateFamily(Family family){
        new UpdateFamilyAsyncTask(familyDao).execute(family);
    }

    public void deleteFamily(Family family){
        new DeleteFamilyAsyncTask(familyDao).execute(family);
    }

    public void deleteAllFamilies(){ new DeleteAllFamiliesAsyncTask(familyDao).execute();
    }


    public LiveData<List<Family>> getAllFamilies(){
        return allFamilies;
    }


    //room doesn't allow to do operations in main thread so we have to do async
    private static class InsertFamilyAsyncTask extends AsyncTask<Family, Void, Void>{//static so it doesn't reference the directory

         private FamilyDao familyDao;

         private InsertFamilyAsyncTask(FamilyDao familyDao){
             this.familyDao = familyDao;
         }
        @Override
        protected Void doInBackground(Family... families) {
            familyDao.insertFamily(families[0]);
            return null;
        }
    }


    private static class DeleteFamilyAsyncTask extends AsyncTask<Family, Void, Void>{

        private FamilyDao familyDao;

        private DeleteFamilyAsyncTask(FamilyDao familyDao){
            this.familyDao = familyDao;
        }

        @Override
        protected Void doInBackground(Family... families) {
            familyDao.deleteFamily(families[0]);
            return null;
        }
    }




    private static class UpdateFamilyAsyncTask extends AsyncTask<Family, Void, Void>{

        private FamilyDao familyDao;

        private UpdateFamilyAsyncTask(FamilyDao familyDao){
            this.familyDao = familyDao;
        }

        @Override
        protected Void doInBackground(Family... families) {
            familyDao.updateFamily(families[0]);
            return null;
        }
    }



    private static class DeleteAllFamiliesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private FamilyDao familyDao;

        private DeleteAllFamiliesAsyncTask(FamilyDao familyDao) {
            this.familyDao = familyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            familyDao.deleteAllFamily();
            return null;
        }
    }




}
