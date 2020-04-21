package com.example.ornithology_favre_berthouzoz.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import gr.net.maroulis.library.EasySplashScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.ornithology_favre_berthouzoz.R;
import com.example.ornithology_favre_berthouzoz.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.WHITE)
                .withLogo(R.drawable.ornitop);

        View splashScreen = config.create();
        setContentView(splashScreen);


    }
}
