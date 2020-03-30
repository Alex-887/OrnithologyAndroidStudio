package com.example.ornithology_favre_berthouzoz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoBird extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_bird);

        Intent intent = getIntent();
        TextView nameDisplay = findViewById(R.id.birdName);

        String birdName = intent.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
        String familyName = intent.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);
        String idBird = intent.getStringExtra(AddEditBirdActivity.EXTRA_IDBIRD);
        String biology = intent.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
        String description = intent.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);

        nameDisplay.setText(birdName);




    }



}
