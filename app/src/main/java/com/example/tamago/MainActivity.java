package com.example.tamago;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static Context context = null;
    @SuppressLint({"SetTextI18n", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm);

        context = getApplicationContext();

        Button MMStart = findViewById(R.id.MMStart);
        MMStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button Reset = findViewById(R.id.MMReset);
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameStatistic gameStatistic = GameStatistic.getInstance();
                gameStatistic.wipeStatisticWithOutPlayTime();

                Toast.makeText(getApplicationContext(), R.string.Reseted, Toast.LENGTH_LONG).show();
            }
        });


        Button MMAbout = findViewById(R.id.MMAbout);
        MMAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        TextView best = findViewById(R.id.bestr);
        // Установка лучшего результата
        GameStatistic gameStatistic = GameStatistic.getInstance();
        best.setText(Integer.toString(gameStatistic.getWrittenScore()));

    }
    public static Context getAppContext() {
        return context;
    }

}
