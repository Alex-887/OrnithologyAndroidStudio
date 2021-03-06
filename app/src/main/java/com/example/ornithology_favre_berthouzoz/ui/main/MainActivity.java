package com.example.ornithology_favre_berthouzoz.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;

import com.example.ornithology_favre_berthouzoz.R;
import com.example.ornithology_favre_berthouzoz.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;

    }


    //LOGOUT
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                break;
        }
        return true;

    }

    public void about(View aboutView) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


    public void searchFamily(View searchFamilyView) {
        Intent intent = new Intent(this, SearchFamilyActivity.class);
        startActivity(intent);
    }

    public void settings(View settingsView) {
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }


}
