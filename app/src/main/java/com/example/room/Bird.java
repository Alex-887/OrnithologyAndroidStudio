package com.example.room;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName ="bird",
        foreignKeys =
        @ForeignKey(entity = Family.class,
        parentColumns = "family",
        childColumns = "family",
        onDelete = CASCADE),
        indices = {@Index("family")})


public class Bird {

    @PrimaryKey(autoGenerate = true)
    private int id;
   // private long familyId;
    private String name;



    private String family;
    private String description;
    private String biology;



    public Bird(@NonNull String name, @NonNull String family, String description, String biology){
        this.name = name;
       // this.familyId = family;
        this.family=family;
        this.description= description;
        this.biology = biology;

    }






//    public long getFamilyId() {
//        return familyId;
//    }
//
//
//    public void setFamilyId(long familyId) {
//        this.familyId = familyId;
//    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public String getBiology() {
        return biology;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }






}
