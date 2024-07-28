package com.example.videoplayerappraqs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etName, etAge, etGender;
    private Button btnSave;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        btnSave = findViewById(R.id.btnSave);

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String ageStr = etAge.getText().toString().trim();
                String gender = etGender.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(gender)) {
                    Toast.makeText(RegistrationActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(RegistrationActivity.this, "La edad debe ser un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", name);
                editor.putInt("age", age);
                editor.putString("gender", gender);
                editor.putBoolean("isRegistered", true);
                editor.apply();

                Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
