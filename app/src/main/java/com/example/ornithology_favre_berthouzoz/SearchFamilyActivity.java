package com.example.ornithology_favre_berthouzoz;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



public class SearchFamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_family);

    }
    public void addFamily(View addFamilyView) {
        Intent intent = new Intent(this, AddFamily.class);
        startActivity(intent);

    }

    public void infoFamily(View addFamilyView) {
        Intent intent = new Intent(this, InfoFamily.class);
        startActivity(intent);

    }


}
