package com.example.room;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bird")
public class Bird {


    private static Bird addBird(final Database db, Bird bird) {
        db.dao().addBird(bird);
        return bird;
    }


    private static void populateWithTestDad(Database db){
        Bird bird = new Bird();
        bird.setName("Coucou");
        bird.setFamily("Coucou Vert");
        addBird(db,bird);
    }



    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "family")
    private String family;




    public Bird(){
        this.name = name;
        this.family = family;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily(){return family;}


    public void setFamily(String family) {
        this.family = family;
    }
}
