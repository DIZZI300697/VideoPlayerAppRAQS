package com.example.videoplayerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videoplayerappraqs.R;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView tvMessage = findViewById(R.id.tvMessage);
        LinearLayout llCategories = findViewById(R.id.llCategories);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        int age = prefs.getInt("age", 0);
        String gender = prefs.getString("gender", "");

        tvMessage.setText("Hola " + name + ", de acuerdo a tu edad: " + age + " las categorías disponibles son:");

        if (age < 12) {
            addCategory(llCategories, "Caricatura", R.drawable.caricatura);
        } else if (age < 18) {
            addCategory(llCategories, "Acción", R.drawable.accion);
            addCategory(llCategories, "Caricatura", R.drawable.caricatura);
        } else {
            addCategory(llCategories, "Terror", R.drawable.terror);
            addCategory(llCategories, "Acción", R.drawable.accion);
            addCategory(llCategories, "Caricatura", R.drawable.caricatura);
        }
    }

    private void addCategory(LinearLayout llCategories, String category, int imageResId) {
        View categoryView = getLayoutInflater().inflate(R.layout.item_category, null);
        ImageView ivCategoryImage = categoryView.findViewById(R.id.ivCategoryImage);
        TextView tvCategoryLabel = categoryView.findViewById(R.id.tvCategoryLabel);

        ivCategoryImage.setImageResource(imageResId);
        tvCategoryLabel.setText(category);

        categoryView.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, com.example.videoplayerappraqs.PlayerActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });

        llCategories.addView(categoryView);
    }
}
