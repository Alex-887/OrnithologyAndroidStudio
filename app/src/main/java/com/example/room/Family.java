package com.example.room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "family")
public class Family {


    @PrimaryKey
    @NonNull
    private String family;


    public Family(@NonNull String family)
    {
        this.family = family;
    }


    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }


}
