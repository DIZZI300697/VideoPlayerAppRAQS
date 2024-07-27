package com.example.videoplayerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videoplayerappraqs.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Iniciar animación del globo
        ImageView balloon = findViewById(R.id.balloon);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.balloon_anim);
        balloon.startAnimation(anim);

        // Redirigir a la pantalla de bienvenida después de la animación
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, com.example.videoplayerapp.WelcomeActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // Duración de la animación (3 segundos)
    }
}
