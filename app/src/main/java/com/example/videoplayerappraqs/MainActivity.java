package com.example.videoplayerappraqs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Iniciar el SplashActivity
        Intent intent = new Intent(this, com.example.videoplayerapp.SplashActivity.class);
        startActivity(intent);
        finish();
    }
}

