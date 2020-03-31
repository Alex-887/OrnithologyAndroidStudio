package com.example.ornithology_favre_berthouzoz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    protected static String Theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_main);
    }

    public void about(View aboutView) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);

    }

    public void searchName(View searchNameView) {
        Intent intent = new Intent(this, SearchNameActivity.class);
        startActivity(intent);

    }

    public void searchFamily(View searchFamilyView) {
        Intent intent = new Intent(this, SearchFamilyActivity.class);

        startActivity(intent);

    }

    public void settings(View settingsView) {
    Intent intent = new Intent(this, FragmentSettings.class);
     startActivity(intent);

    }


}
