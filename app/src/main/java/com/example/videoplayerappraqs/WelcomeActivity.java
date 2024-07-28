package com.example.videoplayerappraqs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnGoToMenu = findViewById(R.id.btnGoToMenu);
        btnGoToMenu.setOnClickListener(v -> {
            // Mostrar diálogo para capturar datos del usuario
            showUserDetailsDialog();
        });
    }

    private void showUserDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_details, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etAge = dialogView.findViewById(R.id.etAge);
        EditText etGender = dialogView.findViewById(R.id.etGender);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String name = etName.getText().toString();
            String ageStr = etAge.getText().toString();
            String gender = etGender.getText().toString();

            if (name.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
                Toast.makeText(WelcomeActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr);

            // Guardar los datos en la memoria interna
            saveUserData(name, age, gender);

            // Ir a la pantalla del menú
            Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveUserData(String name, int age, String gender) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putInt("age", age);
        editor.putString("gender", gender);
        editor.apply();
    }
}
