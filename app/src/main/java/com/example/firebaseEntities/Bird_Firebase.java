package com.example.firebaseEntities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Bird_Firebase {


    private String id;
    private String name;
    private String familyId;
    private String description;
    private String biology;



    public Bird_Firebase() {

    }


    public Bird_Firebase( String name, String description, String biology) {
        this.name = name;
        this.description = description;
        this.biology = biology;
    }




    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getBiology() {
        return biology;
    }




    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Bird_Firebase)) return false;
        Bird_Firebase o = (Bird_Firebase) obj;
        return o.getName().equals(this.getName());
    }


        @Exclude
            public Map<String, Object> toMap(){
            HashMap<String, Object> result = new HashMap<>();
            result.put("biology", biology);
            result.put("description", description);
            result.put("name", name);
            return result;
        }





}
