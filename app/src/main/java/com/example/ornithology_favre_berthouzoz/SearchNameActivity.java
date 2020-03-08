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



public class SearchNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);

    }
    public void addBird(View addBirdView) {
        Intent intent = new Intent(this, AddBird.class);

        startActivity(intent);

    }

    public void infoBird(View addBirdView) {
        Intent intent = new Intent(this, InfoBird.class);

        startActivity(intent);

    }
}
