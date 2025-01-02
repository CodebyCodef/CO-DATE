package com.example.co_date;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.co_date.model.User;
import com.example.co_date.network.ApiService;




public class MainActivity extends AppCompatActivity {

    private Button btnFetchData;
    private TextView tvData;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFetchData = findViewById(R.id.btnFetchData);
        tvData = findViewById(R.id.tvData);

        // Retrofit'i yapılandır
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5025/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromApi();
            }
        });
    }

    private void fetchDataFromApi() {
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    displayData(users);
                } else {
                    tvData.setText("Veri alınamadı");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                tvData.setText("Hata: " + t.getMessage());
            }
        });
    }

    private void displayData(List<User> users) {
        StringBuilder data = new StringBuilder();
        for (User user : users) {
            data.append("ID: ").append(user.getId()).append("\n");
            data.append("İsim: ").append(user.getAd()).append("\n");
            data.append("Soyad: ").append(user.getSoyad()).append("\n");
            data.append("Yaş: ").append(user.getYas()).append("\n\n");
        }
        tvData.setText(data.toString());
    }
}
