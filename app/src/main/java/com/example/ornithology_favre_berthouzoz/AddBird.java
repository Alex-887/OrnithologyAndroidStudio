package com.example.ornithology_favre_berthouzoz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddBird extends AppCompatActivity {
    String[]biotope;
    String[]taille;
    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbird);
        /*Resources res = getResources();
        biotope=res.getStringArray(R.array.biotope);
        taille=res.getStringArray(R.array.taille);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,biotope);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,taille);
        dataAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter2);*/

    }

}
