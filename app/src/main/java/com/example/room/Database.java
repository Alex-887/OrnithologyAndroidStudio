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


@androidx.room.Database(entities = {Bird.class, Family.class},version = 14, exportSchema = true)
public abstract class Database extends RoomDatabase {


    private static Context activity;

    public static Database instance; //singleton

    public abstract BirdDao dao();

    public abstract FamilyDao familyDao();

    public static synchronized Database getInstance(Context context) { //only on thread at a time

        //get the json file
        activity = context.getApplicationContext();

        if (instance ==null)
        {
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

        private BirdDao birdDao;
        private FamilyDao familyDao;

        private PopulateDbAsyncTask(Database db){
            familyDao = db.familyDao();
            birdDao = db.dao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

           fillWithSartingData(activity);
            return null;
        }
    }


    private static void fillWithSartingData(Context context){
        BirdDao birdDao = getInstance(context).dao();
        FamilyDao familyDao = getInstance(context).familyDao();

        JSONArray birds = loadJSONArray(context);
        JSONArray families = loadJSONArray(context);


        try{

            for(int i = 0; i< families.length(); i++){
                JSONObject familyObj = families.getJSONObject(i);

                String family = familyObj.getString("FamilieF");

                familyDao.insertFamily(new Family(family));

                //String family = familyDao.insertFamily(new Family(family));
            }


            for(int j = 0; j< birds.length(); j++){
                JSONObject bird = birds.getJSONObject(j);

                String engName = bird.getString("NameEng");
                String family = bird.getString("FamilieF");
                String description = bird.getString("Description");
                String biology = bird.getString("Biologie");

                birdDao.insertBird(new Bird(engName, family, description, biology));

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
