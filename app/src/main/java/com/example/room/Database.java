package com.example.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@androidx.room.Database(entities = {Bird.class},version = 1)
public abstract class Database extends RoomDatabase {

    public static Database instance; //singleton

    public abstract Dao dao();

    public static synchronized Database getInstance(Context context) { //only on thread at a time
        if (instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "BirdDatabase").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).
                            build();
        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private Dao dao;

        private PopulateDbAsyncTask(Database db){
            dao = db.dao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insertBird(new Bird("Eagle", "SuperPredator"));
            dao.insertBird(new Bird("Coucou", "SuperCoucou"));
            dao.insertBird(new Bird("Duck", "Anatidae"));
            return null;
        }
    }

}
