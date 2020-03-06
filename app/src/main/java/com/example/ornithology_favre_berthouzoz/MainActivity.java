package com.example.ornithology_favre_berthouzoz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void searchIdentification(View searchIdentificationView) {
        Intent intent = new Intent(this, SearchIdentificationActivity.class);

        startActivity(intent);

    }

    public void settings(View settingsView) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

}
