package com.example.room;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bird")
public class Bird {


    @PrimaryKey(autoGenerate = true)
    private int idBird;

    private String name;

    private String family;


    public Bird(@NonNull String name, @NonNull String family){
        this.name = name;
        this.family = family;
    }


//
//    private static Bird addBird(final Database db, Bird bird) {
//        db.dao().insertBird(bird);
//        return bird;
//    }
//
//


    public int getIdBird() {
        return idBird;
    }
    public void setIdBird(int idBird) {
        this.idBird = idBird;
    }


    public String getName() {
        return name;
    }



    public String getFamily(){return family;}

}
