package com.example.codateapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

public class AnasayfaActivity extends AppCompatActivity {

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private ArrayList<CardItem> items;
    private Handler handler;
    private int queryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);


        items = new ArrayList<>();


        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {}

            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Right) {
                    Toast.makeText(AnasayfaActivity.this, "Sağa kaydırıldı!", Toast.LENGTH_SHORT).show();
                    queryCount = 1;
                } else if (direction == Direction.Left) {
                    Toast.makeText(AnasayfaActivity.this, "Sola kaydırıldı!", Toast.LENGTH_SHORT).show();
                }

                loadNextCard();
            }

            @Override
            public void onCardAppeared(View view, int position) {}
            @Override
            public void onCardDisappeared(View view, int position) {}
            @Override
            public void onCardRewound() {}
            @Override
            public void onCardCanceled() {}
        });

        cardStackView.setLayoutManager(manager);
        adapter = new CardStackAdapter(items);
        cardStackView.setAdapter(adapter);

        ImageView imageView = findViewById(R.id.image);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(AnasayfaActivity.this, AyarlarActivity.class);
            startActivity(intent);
        });

        handler = new Handler();
        startTimer();

     //ilk kart ekleme
        loadNextCard();
    }

    private void loadNextCard() {
        //veri tabındaan kişini hobilerine uygun veri çekmek ve onu nextIteme atama
        CardItem nextItem = new CardItem("Rastgele Kişi", "Yaş: 25", R.drawable.image, "Biyografi: Bu kişi yazılım mühendisidir.");
        items.add(nextItem);
        adapter.notifyItemInserted(items.size() - 1);
    }

    private void startTimer() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                performQuery();
                handler.postDelayed(this, 10000); // Ne kadar sıklıkla sorgulanacak
            }
        };
        handler.post(runnable);
    }

    private void performQuery() {
        if (queryCount == 1) {
            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Eşleşme Bulundu!");
                builder.setMessage("Yeni bir eşleşme bulundu. Daha fazla bilgi almak için profiline göz atabilirsiniz.");
                builder.setPositiveButton("Tamam", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            });
            queryCount = 0;
        }
    }
}
