package com.example.room;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ornithology_favre_berthouzoz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@androidx.room.Database(entities = {Bird.class},version = 1)
public abstract class Database extends RoomDatabase {




    private static Context activity;

    public static Database instance; //singleton

    public abstract Dao dao();

    public static synchronized Database getInstance(Context context) { //only on thread at a time

        //get the json file
        activity = context.getApplicationContext();

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


           fillWithSartingData(activity);



            return null;
        }
    }


    private static void fillWithSartingData(Context context){
        Dao dao = getInstance(context).dao();

        JSONArray birds = loadJSONArray(context);

        try{

            for(int i = 0; i< birds.length(); i++){
                JSONObject bird = birds.getJSONObject(i);

                String engName = bird.getString("NameEng");
                String family = bird.getString("FamilieF");
                String description = bird.getString("Description");
                String biology = bird.getString("Biologie");

                dao.insertBird(new Bird(engName, family,description, biology));

            }

        } catch (JSONException e){

        }
    }


    private static JSONArray loadJSONArray(Context context){
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.birdsjson);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;

        try{

            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());

            return json.getJSONArray("birds");

        } catch(IOException | JSONException exception){
            exception.printStackTrace();
        }
        return null;
    }

}
