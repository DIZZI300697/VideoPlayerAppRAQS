package com.example.videoplayerappraqs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;

public class PlayerActivity extends AppCompatActivity {
    private ImageView ivProfilePhoto;
    private String category;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        category = getIntent().getStringExtra("category");

        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);

        showProfilePhotoDialog();
    }

    private void showProfilePhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para poder ver el video es necesario que pongas una foto de perfil.");
        builder.setPositiveButton("Aceptar", (dialog, which) -> takeProfilePhoto());
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Intent intent = new Intent(PlayerActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takeProfilePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivProfilePhoto.setImageBitmap(photo);
            // Guardar foto en almacenamiento interno
            try {
                FileOutputStream fos = openFileOutput("profile_photo.png", MODE_PRIVATE);
                photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar la foto, intente de nuevo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PlayerActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }

            // Mostrar datos del usuario
            displayUserData();
        } else {
            Intent intent = new Intent(PlayerActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void displayUserData() {
        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserAgeGender = findViewById(R.id.tvUserAgeGender);

        String name = prefs.getString("name", "");
        int age = prefs.getInt("age", 0);
        String gender = prefs.getString("gender", "");

        tvUserName.setText(name);
        tvUserAgeGender.setText("Edad: " + age + ", Género: " + gender);

        // Configurar el reproductor de video
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + getVideoResource(category);
        videoView.setVideoURI(Uri.parse(videoPath));

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);

        btnPlay.setOnClickListener(v -> videoView.start());
        btnPause.setOnClickListener(v -> videoView.pause());
        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(PlayerActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private int getVideoResource(String category) {
        switch (category) {
            case "Caricatura":
                return R.raw.caricatura;
            case "Acción":
                return R.raw.accion;
            case "Terror":
                return R.raw.terror;
            default:
                return R.raw.caricatura;
        }
    }
}
