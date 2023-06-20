package com.example.tamago;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {// disable back button
    }

    @SuppressLint({"SetTextI18n", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.mm);

        Button MMStart = findViewById(R.id.MMStart);
        MMStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
            }
        });

        Button Reset = findViewById(R.id.MMReset);
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);
                pref.edit().clear().apply();
                Toast.makeText(getApplicationContext(), R.string.Reseted, Toast.LENGTH_LONG).show();
            }
        });


        Button MMAbout = findViewById(R.id.MMAbout);
        MMAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });
        TextView best = findViewById(R.id.bestr);
        // Установка лучшего результата
        best.setText(Integer.toString(getSharedPreferences("BestR", Context.MODE_PRIVATE).getInt("BestR", 0)));


    }
}
