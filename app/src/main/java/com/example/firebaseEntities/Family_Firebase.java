package com.example.firebaseEntities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Family_Firebase {



    private String familyName;
    private String familyId;




    public Family_Firebase() {
    }


    public Family_Firebase(String familyName, String familyId) {
        this.familyName = familyName;
        this.familyId = familyId;
    }



    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Exclude
    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }



    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Family_Firebase)) return false;
        Family_Firebase o = (Family_Firebase) obj;
        return o.getFamilyId().equals(this.getFamilyId());
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("familyName", familyName);

        return result;
    }


    @Override
    public String toString() {
        return familyName;
    }



}
