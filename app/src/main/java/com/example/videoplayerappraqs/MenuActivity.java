package com.example.videoplayerappraqs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private LinearLayout categoryLayout;
    private TextView tvGreeting;
    private Button btnBackToWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        tvGreeting = findViewById(R.id.tvGreeting);
        categoryLayout = findViewById(R.id.categoryLayout);
        btnBackToWelcome = findViewById(R.id.btnBackToWelcome);

        String name = prefs.getString("name", "Usuario");
        int age = prefs.getInt("age", 0);
        String greeting = "Hola " + name + ", de acuerdo a tu edad: " + age + " las categorías disponibles son:";
        tvGreeting.setText(greeting);

        // Mostrar categorías según la edad
        if (age < 12) {
            addCategory("Caricatura", R.drawable.caricatura_banner);
        } else if (age <= 18) {
            addCategory("Acción", R.drawable.accion_banner);
            addCategory("Caricatura", R.drawable.caricatura_banner);
        } else {
            addCategory("Terror", R.drawable.terror_banner);
            addCategory("Acción", R.drawable.accion_banner);
            addCategory("Caricatura", R.drawable.caricatura_banner);
        }

        btnBackToWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addCategory(String categoryName, int bannerResId) {
        View categoryView = getLayoutInflater().inflate(R.layout.item_category, categoryLayout, false);
        TextView tvCategory = categoryView.findViewById(R.id.tvCategory);
        tvCategory.setText(categoryName);
        categoryView.findViewById(R.id.ivBanner).setBackgroundResource(bannerResId);

        categoryView.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, PlayerActivity.class);
            intent.putExtra("category", categoryName);
            startActivity(intent);
        });

        categoryLayout.addView(categoryView);
    }
}
