package com.example.ornithology_favre_berthouzoz.ui.main;

import android.content.Intent;
import android.os.Bundle;
import com.example.ornithology_favre_berthouzoz.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.widget.TextView;

public class InfoBirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tabbed_info_bird);


        //get the values of the bird clicked
        Intent intent = getIntent();
        TextView nameDisplay = findViewById(R.id.birdName);


        String birdName = intent.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
        String biology = intent.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
        String description = intent.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);

        //set bird as title
        nameDisplay.setText(birdName);

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(description, biology);

       getSupportFragmentManager().beginTransaction().replace(R.id.containerFrag, descriptionFragment).commit();




    }
}