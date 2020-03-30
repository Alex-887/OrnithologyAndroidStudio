package com.example.ornithology_favre_berthouzoz;

import com.example.room.Bird;
import com.example.room.Database;
import com.example.room.Family;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class BirdDaoTest {


Database database;

    // DATA SET FOR TEST
    private static  String family_family;
    private static Bird Bird_demo1 = new Bird("Coucou", "Coucou", "https://www.google.fr, ", "coucou");
    private static Bird Bird_demo2 = new Bird("Coucou1", "Coucou2", "https://www.google.fr, ", "coucou");
    private static Bird Bird_demo3= new Bird("Coucou2", "Coucou", "https://www.google.fr, ", "coucou");


    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        database.dao().insertBird(Bird_demo1);
        database.dao().insertBird(Bird_demo2);
        database.dao().insertBird(Bird_demo3);

        // TEST
        List<Bird> birds = LiveDataTestUtil.getValue(this.database.dao().getBirdsFromFamily("Coucou"));
        List<Bird> Allbirds = LiveDataTestUtil.getValue(this.database.dao().getBirds());

        assertTrue(birds.get(0).getName().equals(Bird_demo1.getName()) && birds.get(0).getFamily() == family_family);





    }
}