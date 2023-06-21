package com.example.tamago;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends Activity {
    GameStatistic gameStatistic;

    Boolean stopThread = false;


    @Override
    public void onBackPressed() {// отключение кнопки назад
    }

    //Тред, который выполняет все основные операции


    Thread mainThread = new Thread(new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            TextView money = findViewById(R.id.GMoney);
            ProgressBar GHunger = findViewById(R.id.GHunger);
            ProgressBar GThirst = findViewById(R.id.GThirst);
            ProgressBar GBoredom = findViewById(R.id.GBoredom);
            ProgressBar GHealth = findViewById(R.id.GHealth);
            TextView time = findViewById(R.id.GTime);
            TextView emoji = findViewById(R.id.GEnt);


            money.setText(Double.toString(gameStatistic.getMoney()));


            // элементы интерфейса с показателями персонажа


            while (true) {
                gameStatistic.modifyAllStatByStatModifier();
                if (gameStatistic.isDie()) {
                    gameStatistic.wipeStatistic();
                    gameStatistic.writeBestScore();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
                        }
                    });
                    startActivity(new Intent(Game.this, MainActivity.class));
                    break;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        GBoredom.setProgress(gameStatistic.getBoredom());
                        GHunger.setProgress(gameStatistic.getHunger());
                        GThirst.setProgress(gameStatistic.getThirst());
                        GHealth.setProgress(gameStatistic.getHealth());
                        time.setText((Integer.toString(gameStatistic.getPlayTime())));
                        emoji.setText(Mood.getMood(gameStatistic));
                    }
                });


                if (gameStatistic.getTick() >= 60 * gameStatistic.getStatModifier())
                    gameStatistic.increaseStatDecrease(1);

                gameStatistic.increaseTickByOne();


                do{
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (stopThread);


            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Установка вида

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        setContentView(R.layout.maingame);
        gameStatistic = GameStatistic.getInstance(getApplicationContext());


        mainThread.start();

        ImageButton GWork = findViewById(R.id.GWork);
        GWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameStatistic.decreaseMoney(-1.0);
                TextView money = findViewById(R.id.GMoney);
                money.setText(Double.toString(gameStatistic.getMoney()));
            }
        });


        ImageButton GSFood = findViewById(R.id.GSFood);
        GSFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, DFshop.class));
            }
        });


        ImageButton GSHealth = findViewById(R.id.GSHealth);
        GSHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, Hshop.class));
            }
        });


        ImageButton GPause = findViewById(R.id.GPause);
        GPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, Pause.class));
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        TextView money = findViewById(R.id.GMoney);
        money.setText(Double.toString(gameStatistic.getMoney()));
        stopThread = false;

        if (!gameStatistic.isDie())
            gameStatistic.save();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopThread = true;


        if (!gameStatistic.isDie())
            gameStatistic.save();


    }


}

