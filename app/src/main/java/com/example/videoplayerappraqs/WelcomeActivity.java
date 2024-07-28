package com.example.videoplayerappraqs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private Button btnRegister;
    private Button btnGoToMenu;
    private Button btnChangeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToMenu = findViewById(R.id.btnGoToMenu);
        btnChangeUser = findViewById(R.id.btnChangeUser);

        boolean isRegistered = prefs.getBoolean("isRegistered", false);
        if (isRegistered) {
            btnRegister.setVisibility(View.GONE);
            btnGoToMenu.setVisibility(View.VISIBLE);
            btnChangeUser.setVisibility(View.VISIBLE);
        } else {
            btnRegister.setVisibility(View.VISIBLE);
            btnGoToMenu.setVisibility(View.GONE);
            btnChangeUser.setVisibility(View.GONE);
        }

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        btnGoToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        btnChangeUser.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            recreate();
        });
    }
}
