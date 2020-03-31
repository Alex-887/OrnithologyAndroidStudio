package com.example.ornithology_favre_berthouzoz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.ornithology_favre_berthouzoz.ui.main.DescriptionFragment;

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
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);



        //get the values of the bird clicked
        Intent intent = getIntent();
        TextView nameDisplay = findViewById(R.id.birdName);

        TextView descriptionDisplay = findViewById(R.id.edit_txt_description);
        TextView biologyDisplay = findViewById(R.id.edit_txt_biology);

        String birdName = intent.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
        String familyName = intent.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);

        String biology = intent.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
        String description = intent.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);

        //set bird as title
        nameDisplay.setText(birdName);


        //send the datas to the SectionsPagerAdapter with bundle
        //Bundle args = new Bundle();
        //args.putString( "Description", description);
        //args.putString( "Biology", biology);



        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(description, biology);
       // descriptionFragment.setArguments(args);

       getSupportFragmentManager().beginTransaction().replace(R.id.containerFrag, descriptionFragment).commit();


//        // set Fragmentclass Arguments
//        DescriptionFragment fragDescription = new DescriptionFragment();
//        fragDescription.setArguments(args);



        //biologyDisplay.setText(biology);
        //descriptionDisplay.setText(familyName);





    }
}