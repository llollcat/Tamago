package com.example.tamago;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        context = getApplicationContext();


        Button MMStart = findViewById(R.id.start_button);
        MMStart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        });

        Button Reset = findViewById(R.id.score_reset_button);
        Reset.setOnClickListener(view -> {
            GameStatistic gameStatistic = GameStatistic.getInstance();
            gameStatistic.wipeAllStatistic();

            Toast.makeText(getApplicationContext(), R.string.Reseted, Toast.LENGTH_LONG).show();
        });

        Button MMAbout = findViewById(R.id.about_button);
        MMAbout.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Установка лучшего результата
        TextView best = findViewById(R.id.best_result_textView);
        GameStatistic gameStatistic = GameStatistic.getInstance();
        best.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getWrittenScore()));

    }
}
