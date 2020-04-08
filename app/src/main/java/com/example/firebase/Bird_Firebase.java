package com.example.firebase;

public class Bird_Firebase {


    private long id;
    private String name;
    private long familyId;
    private String description;
    private String biology;




    public Bird_Firebase() {

    }


    public Bird_Firebase(long id, String name, long family, String description, String biology) {
        this.id = id;
        this.name = name;
        this.familyId = family;
        this.description = description;
        this.biology = biology;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(long familyId) {
        this.familyId = familyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBiology() {
        return biology;
    }

    public void setBiology(String biology) {
        this.biology = biology;
    }





}
