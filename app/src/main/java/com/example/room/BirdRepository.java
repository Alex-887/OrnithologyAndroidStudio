package com.example.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BirdRepository {
    private Dao dao;

    private LiveData<List<Bird>> allBirds;

    public BirdRepository(Application application){
        Database database = Database.getInstance(application);
        dao = database.dao();
        allBirds = dao.getBirds();
    }

    public void insertBird(Bird bird){
    new InsertBirdAsyncTask(dao).execute(bird);
    }


    public void updateBird(Bird bird){
        new UpdateBirdAsyncTask(dao).execute(bird);
    }

    public void deleteBird(Bird bird){
        new DeletetBirdAsyncTask(dao).execute(bird);
    }


    public LiveData<List<Bird>> getAllBirds(){
        return allBirds;
    }


    //room doesn't allow to do operations in main thread so we have to do async
    private static class InsertBirdAsyncTask extends AsyncTask<Bird, Void, Void>{//static so it doesn't reference the directory

         private Dao dao;

         private InsertBirdAsyncTask(Dao dao){
             this.dao = dao;
         }


        @Override
        protected Void doInBackground(Bird... birds) {
             dao.insertBird(birds[0]);
            return null;
        }
    }



    private static class DeletetBirdAsyncTask extends AsyncTask<Bird, Void, Void>{

        private Dao dao;

        private DeletetBirdAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Bird... birds) {
            dao.deleteBird(birds[0]);
            return null;
        }
    }




    private static class UpdateBirdAsyncTask extends AsyncTask<Bird, Void, Void>{

        private Dao dao;

        private UpdateBirdAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Bird... birds) {
            dao.updateBird(birds[0]);
            return null;
        }
    }





}
