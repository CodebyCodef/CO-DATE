package com.example.codateapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AyarlarActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText biographyEditText;
    private Button  deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        //veri tabnında veri çekip ona göre bir kulanıcın bilgileri gosterilebilir
        profileImageView = findViewById(R.id.profileImageView);
        biographyEditText = findViewById(R.id.biography);
        deleteAccountButton = findViewById(R.id.delete_account_button);
        ImageButton backButton = findViewById(R.id.back_button);
        Button saveBiographyButton = findViewById(R.id.save_biography);


        saveBiographyButton.setOnClickListener(v -> {
            String biography = biographyEditText.getText().toString().trim();
            if (!biography.isEmpty()) {
                // Biyografiyi kaydetme işlemi burada yapılabilir
                Toast.makeText(AyarlarActivity.this, "Biyografi kaydedildi: " + biography, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AyarlarActivity.this, "Biyografi boş olamaz!", Toast.LENGTH_SHORT).show();
            }
        });
        deleteAccountButton.setOnClickListener(v -> showDeleteAccountDialog());

        profileImageView.setOnClickListener(v -> openImageChooser());

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AyarlarActivity.this, AnasayfaActivity.class);
            startActivity(intent);
        });

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            //veri tabında hesaptna çıkış yapılığını tutlması
            Toast.makeText(this, "Hesaptan çıkış yapıldı!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AyarlarActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hesabı Sil")
                .setMessage("Hesabınızı silmek istediğinizden emin misiniz? Bu işlem geri alınamaz!")
                .setPositiveButton("Evet", (dialog, which) -> {
                    Toast.makeText(this, "Hesabınız silindi!", Toast.LENGTH_SHORT).show();
                    // veri tabanı hesap silme
                })
                .setNegativeButton("Hayır", null)
                .show();
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    profileImageView.setImageURI(imageUri); // Seçilen resmi ImageView'e yerleştiriyoruz
                    //veri tananına eklemek
                }
            });
}
