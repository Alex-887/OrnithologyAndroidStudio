package com.example.firebase;

public class Family_Firebase {



    private String family;
    private long familyId;




    public Family_Firebase() {
    }


    public Family_Firebase(String family, long familyId) {
        this.family = family;
        this.familyId = familyId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(long familyId) {
        this.familyId = familyId;
    }









}
