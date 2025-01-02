package com.example.codateapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import API.ApiService;
import API.Register;
import API.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private ArrayList<Integer> selectedHobbies = new ArrayList<>();
    private String base64ProfilePhoto;

    private final ActivityResultLauncher<Intent> hobbyLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedHobbies = result.getData().getIntegerArrayListExtra("selectedHobbies");
                    Toast.makeText(this, "Seçilen Hobiler: " + selectedHobbies.toString(), Toast.LENGTH_SHORT).show();
                }
            }
    );

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    profileImageView.setImageURI(imageUri);
                    base64ProfilePhoto = convertImageToBase64(((BitmapDrawable) profileImageView.getDrawable()).getBitmap());
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        EditText nameEditText = findViewById(R.id.name);
        EditText surnameEditText = findViewById(R.id.surname);
        EditText ageEditText = findViewById(R.id.age);
        EditText phoneEditText = findViewById(R.id.tel);
        EditText biographyText = findViewById(R.id.biography);
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        Button saveButton = findViewById(R.id.save_button);
        Button hobbyButton = findViewById(R.id.hobby_button);
        Button girisBtn = findViewById(R.id.geri);
        profileImageView = findViewById(R.id.profileImageView);

        profileImageView.setOnClickListener(v -> openImageChooser());

        hobbyButton.setOnClickListener(v -> {
            Intent intent = new Intent(KayitActivity.this, hobiler.class);
            intent.putIntegerArrayListExtra("selectedHobbies", selectedHobbies);
            hobbyLauncher.launch(intent);
        });

        girisBtn.setOnClickListener(v -> {
            Intent intent = new Intent(KayitActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        saveButton.setOnClickListener(v -> {
            if (!isFormValid(usernameEditText, passwordEditText, nameEditText, surnameEditText, ageEditText, phoneEditText, biographyText, genderRadioGroup)) {
                return;
            }

            Register user = new Register();
            user.setEmail(usernameEditText.getText().toString());
            user.setSifre(passwordEditText.getText().toString());
            user.setIsim(nameEditText.getText().toString());
            user.setSoyisim(surnameEditText.getText().toString());
            user.setYas(ageEditText.getText().toString());
            user.setTelNo(phoneEditText.getText().toString());
            user.setBiyografi(biographyText.getText().toString());

            RadioButton selectedGender = findViewById(genderRadioGroup.getCheckedRadioButtonId());
            user.setCinsiyet(selectedGender.getText().toString());



            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            apiService.registerUser(user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(KayitActivity.this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KayitActivity.this, AnasayfaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(KayitActivity.this, "Kayıt başarısız: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(KayitActivity.this, "Hata: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private String convertImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    private boolean isFormValid(EditText usernameEditText, EditText passwordEditText, EditText nameEditText, EditText surnameEditText, EditText ageEditText, EditText phoneEditText, EditText biographyText, RadioGroup genderRadioGroup) {
        if (usernameEditText.getText().toString().isEmpty() ||
                passwordEditText.getText().toString().isEmpty() ||
                nameEditText.getText().toString().isEmpty() ||
                surnameEditText.getText().toString().isEmpty() ||
                ageEditText.getText().toString().isEmpty() ||
                phoneEditText.getText().toString().isEmpty() ||
                genderRadioGroup.getCheckedRadioButtonId() == -1 ||
                biographyText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return false;
        }


        /*
        if (profileImageView.getDrawable() == null || base64ProfilePhoto == null) {
            Toast.makeText(this, "Lütfen bir profil fotoğrafı ekleyiniz!", Toast.LENGTH_SHORT).show();
            return false;
        }
        */

        if (selectedHobbies.isEmpty()) {
            Toast.makeText(this, "Lütfen en az bir hobi seçiniz!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
