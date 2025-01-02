package com.example.codateapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class hobiler extends AppCompatActivity {

    private ArrayList<Integer> selectedHobbies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hobiler);
        Button kayitbtn= findViewById(R.id.kayditbutonu);
        selectedHobbies = new ArrayList<>();

        CheckBox hobby1 = findViewById(R.id.checkBox1);
        CheckBox hobby2 = findViewById(R.id.checkBox2);
        CheckBox hobby3 = findViewById(R.id.checkBox3);
        CheckBox hobby4 = findViewById(R.id.checkBox4);
        CheckBox hobby5 = findViewById(R.id.checkBox5);
        CheckBox hobby6 = findViewById(R.id.checkBox6);
        CheckBox hobby7 = findViewById(R.id.checkBox7);
        kayitbtn.setOnClickListener(v -> {
            selectedHobbies.clear();

            // Seçilen hobileri kontrol et
            if (hobby1.isChecked()) selectedHobbies.add(1);
            if (hobby2.isChecked()) selectedHobbies.add(2);
            if (hobby3.isChecked()) selectedHobbies.add(3);
            if (hobby4.isChecked()) selectedHobbies.add(4);
            if (hobby5.isChecked()) selectedHobbies.add(5);
            if (hobby6.isChecked()) selectedHobbies.add(6);
            if (hobby7.isChecked()) selectedHobbies.add(7);


            if (selectedHobbies.size() > 5) {
                Toast.makeText(hobiler.this, "En fazla 5 hobi seçebilirsiniz!", Toast.LENGTH_SHORT).show();
                return;
            } else if (selectedHobbies.size() == 0) {
                Toast.makeText(hobiler.this, "Lütfen en az bir tane seçin!", Toast.LENGTH_SHORT).show();
                return;
            }


            String[] hobbyNames = {"Kitap Okuma", "Yüzme", "Yemek Yapma", "Koşu", "Resim Yapma", "Seyahat", "Müzik Dinleme"};
            StringBuilder selectedHobbiesText = new StringBuilder("Seçilen Hobiler:\n");
            for (int id : selectedHobbies) {
                selectedHobbiesText.append("- ").append(hobbyNames[id - 1]).append("\n");
            }
            Toast.makeText(hobiler.this, selectedHobbiesText.toString(), Toast.LENGTH_SHORT).show();


            Intent resultIntent = new Intent();
            resultIntent.putIntegerArrayListExtra("selectedHobbies", selectedHobbies);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }
}