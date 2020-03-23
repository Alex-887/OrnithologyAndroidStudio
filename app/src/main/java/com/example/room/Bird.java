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



    private String description;
    private String biology;


    public Bird(@NonNull String name, @NonNull String family, String description, String biology){
        this.name = name;
        this.family = family;
        this.description= description;
        this.biology = biology;
    }




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

    public String getDescription() {
        return description;
    }

    public String getBiology() {
        return biology;
    }




}
