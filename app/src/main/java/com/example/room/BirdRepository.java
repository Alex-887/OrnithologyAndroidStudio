package com.example.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BirdRepository {
    private BirdDao birdDao;
    private FamilyDao familyDao;

    private LiveData<List<Bird>> allBirds;
    private LiveData<List<Bird>> allBirdsFromFamily;


    private static BirdRepository instance;


    private String family;

    public BirdRepository(Application application){
        Database database = Database.getInstance(application);
        birdDao = database.dao();

        allBirds = birdDao.getBirds();

        allBirdsFromFamily = birdDao.getBirdsFromFamily(family);
    }




    public BirdRepository() {

    }

    public LiveData<List<Bird>> getAllBirds(){
        return allBirds;
    }


    public void insertBird(Bird bird){
        new InsertBirdAsyncTask(birdDao).execute(bird);
    }

    public void updateBird(Bird bird){
        new UpdateBirdAsyncTask(birdDao).execute(bird);
    }

    public void deleteBird(Bird bird){
        new DeleteBirdAsyncTask(birdDao).execute(bird);
    }

    public void deleteAllBird(){ new DeleteAllBirdsAsyncTask(birdDao).execute(); }

    public void updateFamilyInBird(Bird bird){

        new UpdateFamilyInBird(birdDao).execute(bird);

    }


    public LiveData<List<Bird>> getAllBirdsFromFamily(String family){
        return birdDao.getBirdsFromFamily(family);
    }




    private static class UpdateFamilyInBird extends AsyncTask<Bird, String, Void>{//static so it doesn't reference the directory

        private BirdDao birdDao;
        private UpdateFamilyInBird(BirdDao birdDao){
            this.birdDao = birdDao;
        }

        @Override
        protected Void doInBackground(Bird... birds) {
            Bird bird = birds[0];

            birdDao.updateFamilyInBird(bird.getFamily());
            return null;
        }
    }




    //room doesn't allow to do operations in main thread so we have to do async
    private static class InsertBirdAsyncTask extends AsyncTask<Bird, Void, Void>{//static so it doesn't reference the directory

         private BirdDao birdDao;

         private InsertBirdAsyncTask(BirdDao birdDao){
             this.birdDao = birdDao;
         }


        @Override
        protected Void doInBackground(Bird... birds) {
             birdDao.insertBird(birds[0]);
            return null;
        }
    }



    private static class DeleteBirdAsyncTask extends AsyncTask<Bird, Void, Void>{

        private BirdDao birdDao;

        private DeleteBirdAsyncTask(BirdDao birdDao){
            this.birdDao = birdDao;
        }

        @Override
        protected Void doInBackground(Bird... birds) {
            birdDao.deleteBird(birds[0]);
            return null;
        }
    }






    private static class UpdateBirdAsyncTask extends AsyncTask<Bird, Void, Void>{

        private BirdDao birdDao;

        private UpdateBirdAsyncTask(BirdDao birdDao){
            this.birdDao = birdDao;
        }

        @Override
        protected Void doInBackground(Bird... birds) {
            birdDao.updateBird(birds[0]);
            return null;
        }
    }




    private static class DeleteAllBirdsAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private BirdDao birdDao;

        private DeleteAllBirdsAsyncTask(BirdDao birdDao) {
            this.birdDao = birdDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            birdDao.deleteAllBirds();
            return null;
        }
    }





}
