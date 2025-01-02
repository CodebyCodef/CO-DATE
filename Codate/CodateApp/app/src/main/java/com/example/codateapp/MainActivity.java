package com.example.codateapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String kullaniciAdi="a";
    String sifre="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Lütfen giriş bilgilerinizi giriniz", Toast.LENGTH_SHORT).show();
            } else {
                //veri tabnından veri gelip kulanıcı adı ve şifreyi arayıp alıcak ve yazıcak

                if (username.equals(kullaniciAdi) && password.equals(sifre)) {
                    Toast.makeText(MainActivity.this, "Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Hatalı kullanıcı adı veya şifre!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KayitActivity.class);
            startActivity(intent);
        });
    }
}